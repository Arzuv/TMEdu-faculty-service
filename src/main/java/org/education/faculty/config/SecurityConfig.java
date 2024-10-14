package org.education.faculty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String SCOPE_READ = "SCOPE_RESOURCE_READ";
    private static final String SCOPE_ADMIN = "SCOPE_RESOURCE_ADMIN";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAuthority(SCOPE_READ)
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAuthority(SCOPE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasAuthority(SCOPE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAuthority(SCOPE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/v1/swagger-ui/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(getJwtAuthenticationConverter())
                        ));
        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
