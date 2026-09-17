package ru.gitverse.bizzareowl.mgateway.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("mgateway")
@NoArgsConstructor
@AllArgsConstructor
public class AlertMessagesToHandlersProperties {

    @Getter
    private List<Integer> telegram;

}
