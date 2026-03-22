package it.davidecascella.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

/**
 * Converts AWS Cognito group claims from a JWT into Spring Security {@link GrantedAuthority}.
 * <p>
 * Extracts the {@code cognito:groups} claim from the token and maps each group name to
 * an authority prefixed with {@code ROLE_}, conforming to Spring Security conventions.
 */
public class CognitoGroupsConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts Cognito groups from the JWT into a collection of granted authorities.
     *
     * @param source the JWT containing Cognito claims (must not be null)
     * @return collection of authorities, one per group (ROLE_GROUP_NAME); empty if no groups
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
