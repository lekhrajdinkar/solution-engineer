package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.oauth2.jwt.JwtValidationException;

@Slf4j
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>
{
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final String requiredScope;

    public CustomJwtAuthenticationConverter(String requiredScope) {
        this.requiredScope = requiredScope;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Extract and validate scope
        Collection<String> tokenScopes = extractScopes(jwt);
        log.info("JWT {} , tokenScopes {}", jwt, tokenScopes);
        if (!tokenScopes.contains(requiredScope)) {
            throw new JwtValidationException("Missing required scope: " + requiredScope, List.of());
        }

        // Convert JWT to authorities
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        if (authorities == null) {
            authorities = Collections.emptyList();
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<String> extractScopes(Jwt jwt) {
        // Check both "scp" and "scope" claims (different providers use different claims)
        Object scopes = jwt.getClaim("scp");
        if (scopes == null) {
            scopes = jwt.getClaim("scope");
        }

        if (scopes instanceof String) {
            return List.of(((String) scopes).split(" "));
        } else if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }
        return Collections.emptyList();
    }
}
