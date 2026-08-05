package ru.gitverse.bizzareowl.mgateway.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessageHandler;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.gateway.RawAlertMessage;
import ru.gitverse.bizzareowl.mgateway.persistence.DeduplicationRepository;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecordPrimaryKey;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSavedResponseDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.mappers.RawAlertMassageDtoMapper;

@RestController
@RequestMapping("/emergency-alerts")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final AlertMessageHandler alertMessageHandler;

    private final DeduplicationRepository deduplicationRepository;

    private final RawAlertMassageDtoMapper rawAlertMassageDtoMapper = Mappers.getMapper(RawAlertMassageDtoMapper.class);

    @PostMapping
    @Override
    public ResponseEntity<MessageSavedResponseDto> sendMessage(@Valid @RequestBody RawAlertMessageDto rawAlertMessageDto) {
        final RawAlertMessage rawAlertMessage = rawAlertMassageDtoMapper.toDomain(rawAlertMessageDto);
        if (deduplicationRepository.existsById(new MessageDeduplicationRecordPrimaryKey(
                rawAlertMessage.messageId(), rawAlertMessage.messageData().id(), rawAlertMessage.messageData().source()))
        ) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        final ProcessedAlertMessage processedAlertMessage = alertMessageHandler.handle(rawAlertMassageDtoMapper.toDomain(rawAlertMessageDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageSavedResponseDto(processedAlertMessage.id()));
    }

}
