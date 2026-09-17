package ru.gitverse.bizzareowl.mgateway.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("mgateway")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlertMessagesToHandlersProperties {

    private List<Integer> telegram;

}
