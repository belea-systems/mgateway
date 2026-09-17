package ru.gitverse.bizzareowl.mgateway.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessagesToHandlerRouter;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSavedResponseDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.mappers.RawAlertMassageDtoMapper;

@RestController
@RequestMapping("/emergency-alerts")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final AlertMessagesToHandlerRouter alertMessagesToHandlerRouter;

    private final RawAlertMassageDtoMapper rawAlertMassageDtoMapper = Mappers.getMapper(RawAlertMassageDtoMapper.class);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public MessageSavedResponseDto sendMessage(@Valid @RequestBody RawAlertMessageDto rawAlertMessageDto) {
        final ProcessedAlertMessage processedAlertMessage = alertMessagesToHandlerRouter.route(rawAlertMessageDto.sourceId())
                .handle(rawAlertMassageDtoMapper.toDomain(rawAlertMessageDto));

        return new MessageSavedResponseDto(processedAlertMessage.id());
    }

}
