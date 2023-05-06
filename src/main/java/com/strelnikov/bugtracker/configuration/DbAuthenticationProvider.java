package com.strelnikov.bugtracker.configuration;

import com.strelnikov.bugtracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DbAuthenticationProvider implements AuthenticationProvider {

    UserRepository userRepository;

    public DbAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var password = authentication.getCredentials().toString();
        if (!"password".equals(password)) {
            throw new AuthenticationServiceException("Invalid username or password");
        }
        return userRepository.findByUsername(authentication.getName())
                .map(user -> new PlainAuthentication(user.getId()))
                .orElseThrow(() -> new AuthenticationServiceException("Invalid username or password"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
