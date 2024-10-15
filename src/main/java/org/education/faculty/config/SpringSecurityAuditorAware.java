package org.education.faculty.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SpringSecurityAuditorAware {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return Optional.of("SYSTEM");
        }
        Jwt jwt = (Jwt) authentication.getCredentials();
        String EMAIL = "email";
        String email = jwt.getClaims()
                .get(EMAIL).toString();
        return Optional.of(email);
    }
}
