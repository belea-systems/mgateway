package ru.gitverse.bizzareowl.mgateway.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessageHandler;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSource;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSourceData;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.persistence.DeduplicationRepository;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecordPrimaryKey;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSourceDataDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AlertMessageHandler alertMessageHandler;

    @MockitoBean
    private DeduplicationRepository deduplicationRepository;

    @Test
    @DisplayName("Send message with valid body test")
    public void sendMessage_withValidMessage_shouldSaveMessage() {

        final MessageSourceDataDto messageSourceDataDto = new MessageSourceDataDto("id", "name", "TELEGRAM");
        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto("Message", "id", Instant.now(), messageSourceDataDto);
        final UUID uuid = UUID.randomUUID();

        Mockito.when(alertMessageHandler.handle(Mockito.any())).thenReturn(
                new ProcessedAlertMessage(
                        uuid, "message", "id", new MessageSourceData("id", "name", MessageSource.TELEGRAM), Instant.now()
                )
        );

        MvcTestResult testResult = mockMvcTester.post().uri("/emergency-alerts" )
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(testResult).hasStatus(HttpStatus.CREATED);
        assertThat(testResult).bodyJson().extractingPath("$.messageId").isEqualTo(uuid.toString());
    }

    @Test
    @DisplayName("Send message second time with valid body test")
    public void sendMessage_secondTimeWithValidMessage_shouldReturnNoContent() {

        final MessageSourceDataDto messageSourceDataDto = new MessageSourceDataDto("id", "name", "TELEGRAM");
        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto("Message", "id", Instant.now(), messageSourceDataDto);

        Mockito.when(deduplicationRepository.existsById(
                new MessageDeduplicationRecordPrimaryKey("id", "id", MessageSource.TELEGRAM))
        ).thenReturn(true);

        MvcTestResult testResult = mockMvcTester.post().uri("/emergency-alerts" )
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(testResult).hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Send message with invalid body test")
    public void sendMessage_withInvalidMessage_shouldDiscardMessage() {

        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto("Message", null, null, null);

        final MvcTestResult result = mockMvcTester.post().uri("/emergency-alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(result).bodyJson().extractingPath("$.title").isNotEmpty();
        assertThat(result).bodyJson().extractingPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result).bodyJson().extractingPath("$.detail").isNotEmpty();


    }

}