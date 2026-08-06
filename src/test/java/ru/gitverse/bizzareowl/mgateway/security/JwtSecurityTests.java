package ru.gitverse.bizzareowl.mgateway.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import ru.gitverse.bizzareowl.mgateway.configuration.SecurityConfig;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(
        properties = {
                "application.jwt.signature.secret-key=1bUGQhHpJ8ezuKxyLurY7wBcJ9OxCpoaHwXN4XKneXV"
        }, controllers = TestController.class
)
@Import({
        SecurityConfig.class, JwtSecurityTests.TestConfig.class
})
public class JwtSecurityTests {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public JwtUtils jwtUtils(@Value("${application.jwt.signature.secret-key}") String base64Key) {
            return new JwtUtils(base64Key);
        }

    }

    private static final String SAMPLE_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.xGsnJRq4BKSbxJmsOY0Q4rc4oZJP-OSQrADxlaGztgs";
    private static final String FAKE_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJZdWIIOiIxMjM0NTY3ODkwIn0.xGsnJRq4BKSbxJmsOY0Q4rc4oZJP-OSQrADxlaGztgs";


    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    @DisplayName("Perform simple request with JWT bearer token should accept request")
    public void simpleRequest_withCorrectJwtToken_shouldAcceptRequest() {
        final MvcTestResult mvcTestResult = mockMvcTester.post().uri("/test")
                .header("Authorization", "Bearer " + SAMPLE_JWT_TOKEN)
                .exchange();

        assertThat(mvcTestResult).hasStatus(HttpStatus.OK);
    }

    @Test
    @DisplayName("Perform simple request with fake JWT bearer token should deny request")
    public void simpleRequest_withFakeJwtToken_shouldAcceptRequest() {
        final MvcTestResult mvcTestResult = mockMvcTester.post().uri("/test")
                .header("Authorization", "Bearer " + FAKE_JWT_TOKEN)
                .exchange();

        assertThat(mvcTestResult).hasStatus4xxClientError();
    }

    @Test
    @DisplayName("Perform simple request without JWT bearer token should deny request")
    public void simpleRequest_withoutJwtToken_shouldDenyRequest() {
        final MvcTestResult mvcTestResult = mockMvcTester.post().uri("/test").exchange();
        assertThat(mvcTestResult).hasStatus4xxClientError();
    }

}
