package it.davidecascella.config;

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

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurator {





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/public/**").permitAll()// Permetti tutte le richieste OPTIONS per il CORS
                        .requestMatchers("/actuator/health").permitAll() // Lascia aperto l'health check per l'AWS Load Balancer/Target Group
                        .anyRequest().authenticated() // Tutto il resto richiede un JWT valido
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(_ ->new JwtAuthenticationConverter()) );// Attiva la validazione del JWT

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter  cognitoJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new CognitoGroupsConverter());

        return converter;
    }



}