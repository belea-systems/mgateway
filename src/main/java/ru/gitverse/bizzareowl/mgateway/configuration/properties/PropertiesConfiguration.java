package ru.gitverse.bizzareowl.mgateway.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class PropertiesConfiguration {

    @Bean
    public AlertMessagesToHandlersProperties alertMessagesToHandlersProperties() {
        return new AlertMessagesToHandlersProperties();
    }

}
