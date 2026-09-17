package ru.gitverse.bizzareowl.mgateway.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import ru.gitverse.bizzareowl.mgateway.security.JwtSecurityFilter;
import ru.gitverse.bizzareowl.mgateway.security.JwtUtils;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestMatcherRegistry -> {
                    requestMatcherRegistry.anyRequest().authenticated();
                }).addFilterBefore(new JwtSecurityFilter(jwtUtils), AnonymousAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
