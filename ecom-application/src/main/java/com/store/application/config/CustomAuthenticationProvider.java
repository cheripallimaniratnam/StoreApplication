package com.store.application.config;

import com.store.application.model.UserLogin;
import com.store.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository repo;
    private PasswordEncoder encoder;

    public CustomAuthenticationProvider(UserRepository repo,
                                        PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserLogin user = repo.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        // 🔥 Custom validation logic
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }


        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}