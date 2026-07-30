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
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessagesToHandlerRouter;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;
import tools.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;
import java.util.UUID;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AlertMessagesToHandlerRouter alertMessagesToHandlerRouter;

    @Test
    @DisplayName("Send message with valid body test")
    public void sendMessage_withValidMessage_shouldSaveMessage() {

        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto(100, new byte[]{1, 1, 1});
        final UUID uuid = UUID.randomUUID();
        final AlertMessageHandler handler = Mockito.mock(AlertMessageHandler.class);

        Mockito.when(handler.handle(Mockito.any())).thenReturn(
                new ProcessedAlertMessage(uuid, 100, "message", ZonedDateTime.now(), null)
        );
        Mockito.when(alertMessagesToHandlerRouter.route(Mockito.anyInt())).thenReturn(handler);

        mockMvcTester.post().uri("/emergency-alerts" )
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .assertThat().hasStatus2xxSuccessful()
                .actual()
                .assertThat()
                .bodyJson().extractingPath("$.messageId").isEqualTo(uuid.toString());

    }

    @Test
    @DisplayName("Send message with invalid body test")
    public void sendMessage_withInvalidMessage_shouldDiscardMessage() {

        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto(-123123, null);

        final MvcTestResult result = mockMvcTester.post().uri("/emergency-alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .exchange();

        Assertions.assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(result).bodyJson().extractingPath("$.occurredAt").isNotEmpty();
        Assertions.assertThat(result).bodyJson().extractingPath("$.description").isNotEmpty();

    }

}