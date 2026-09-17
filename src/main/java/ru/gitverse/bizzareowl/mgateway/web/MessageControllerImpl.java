package ru.gitverse.bizzareowl.mgateway.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSavedResponseDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;

@RestController
@RequestMapping("/emergency-alerts")
public class MessageControllerImpl implements MessageController {

    @PostMapping
    @Override
    public MessageSavedResponseDto sendMessage(RawAlertMessageDto rawAlertMessageDto) {
        return null;
    }

}
