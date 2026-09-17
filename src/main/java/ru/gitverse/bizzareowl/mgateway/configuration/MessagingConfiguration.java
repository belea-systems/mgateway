package ru.gitverse.bizzareowl.mgateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

@Configuration
public class MessagingConfiguration {

    @Bean
    public AlertMessageSender alertMessageSender() {
        return null;
    }

}
