package it.davidecascella.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class CognitoGroupsConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<String> groups = source.getClaimAsStringList("cognito:groups");
        if (groups == null || groups.isEmpty()) {
            return List.of();
        }
        return groups.stream()
                .map(group -> (GrantedAuthority) () -> "ROLE_" + group.toUpperCase()) // Aggiungi "ROLE_" per conformarti a Spring Security
                .toList();

    }
}
