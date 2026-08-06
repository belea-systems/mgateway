package ru.gitverse.bizzareowl.mgateway.security;

import io.jsonwebtoken.lang.Collections;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {

    public JwtAuthentication() {
        this(Collections.emptyList());
    }

    private JwtAuthentication(@Nullable Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return null;
    }
}
