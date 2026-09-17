package ru.gitverse.bizzareowl.mgateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessageHandler;
import ru.gitverse.bizzareowl.mgateway.gateway.impl.TelegramAlertMessageHandler;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

@Configuration
public class HandlersConfiguration {

    @Bean
    public AlertMessageHandler telegramAlertMessageHandler(final AlertMessageSender alertMessageSender) {
        return new TelegramAlertMessageHandler(alertMessageSender);
    }

}
