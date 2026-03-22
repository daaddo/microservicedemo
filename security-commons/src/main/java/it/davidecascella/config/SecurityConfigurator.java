package it.davidecascella.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * Spring Security configuration for OAuth2 Resource Server with JWT validation.
 * <p>
 * Configures HTTP security (CSRF, CORS, authorization), enables method-level security,
 * and wires JWT authentication with Cognito group-to-authority conversion.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfigurator {

    /**
     * Defines the main security filter chain for HTTP requests.
     *
     * @param http the HTTP security builder
     * @return the configured filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                           auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                            auth.requestMatchers("/actuator/**").permitAll();
                            auth.requestMatchers("/**").permitAll();
                        }
                    )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(_ ->new JwtAuthenticationConverter()) );// Attiva la validazione del JWT

        return http.build();
    }

    /**
     * Provides a JWT converter that maps Cognito groups to Spring Security authorities.
     *
     * @return configured JwtAuthenticationConverter using {@link CognitoGroupsConverter}
     */
    @Bean
    public JwtAuthenticationConverter cognitoJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new CognitoGroupsConverter());

        return converter;
    }



}