package it.davidecascella.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS configuration for non-production and production profiles.
 * <p>
 * In non-production, configures allowed origins (from {@code cors.allowed.origins}),
 * methods, and headers. In production, returns a no-op configuration.
 */
@Configuration
@Slf4j
public class CorsConfig {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    /**
     * CORS configuration source for non-production environments.
     * <p>
     * Uses {@code cors.allowed.origins} for allowed origins (comma-separated).
     * If set to {@code *}, credentials are disabled; otherwise credentials are allowed.
     *
     * @return CORS configuration source for all paths
     */
    @Bean
    @Profile("!prod")
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Allowed origins: {}", allowedOrigins);
        List<String> split = List.of(allowedOrigins.split(","));
        CorsConfiguration configuration = new CorsConfiguration();
        if (allowedOrigins.equals("*")) {
            log.info("Setting allowCredentials to false");
            configuration.setAllowedOriginPatterns(List.of("*")); // Usa allowedOriginPatterns
            configuration.setAllowCredentials(false);

        } else {
            configuration.setAllowCredentials(true);
            configuration.setAllowedOrigins(split);
        }
        // Aggiungi il tuo frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "X-XSRF-TOKEN",
                "Accept",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Credentials",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin"));

        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * CORS configuration source for production (no CORS applied).
     *
     * @return configuration source returning null (no CORS headers)
     */
    @Bean
    @Profile("prod")
    public CorsConfigurationSource prodCorsConfigurationSource() {
        return _ -> null;
    }
}
