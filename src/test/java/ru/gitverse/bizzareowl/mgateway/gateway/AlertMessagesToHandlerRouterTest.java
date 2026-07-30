package ru.gitverse.bizzareowl.mgateway.gateway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.gitverse.bizzareowl.mgateway.configuration.properties.AlertMessagesToHandlersProperties;

@SpringBootTest(
        properties = {
                "mgateway.sources.telegram[0]=100",
                "mgateway.sources.telegram[1]=101"
        })
public class AlertMessagesToHandlerRouterTest {

    @Autowired
    private AlertMessagesToHandlersProperties alertMessagesToHandlersProperties;

    @MockitoBean
    private AlertMessageHandler alertMessageHandler;

    @Test
    @DisplayName("Initialize router with configuration properties")
    public void initializeRouter_withConfigurationProperties_shouldInitializeProperly() {
        AlertMessagesToHandlerRouter alertMessagesToHandlerRouter = new AlertMessagesToHandlerRouter(
                alertMessageHandler, alertMessagesToHandlersProperties
        );

        Assertions.assertThat(alertMessagesToHandlerRouter.getAlertMessageHandlerMap().get(100)).isEqualTo(alertMessageHandler);
        Assertions.assertThat(alertMessagesToHandlerRouter.getAlertMessageHandlerMap().get(101)).isEqualTo(alertMessageHandler);
    }

}