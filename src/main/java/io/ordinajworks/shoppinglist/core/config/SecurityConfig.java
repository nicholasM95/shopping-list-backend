package io.ordinajworks.shoppinglist.core.config;

import io.ordinajworks.shoppinglist.core.coverter.RoleConverter;
import io.ordinajworks.shoppinglist.core.handler.AuthErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((AbstractHttpConfigurer::disable));
        http.cors(Customizer.withDefaults());

        http.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.GET, "/item").hasRole("ADMIN");
            request.requestMatchers(HttpMethod.POST, "/item").hasRole("ADMIN");
            request.anyRequest().authenticated();
        });
        http.oauth2ResourceServer(resourceServer -> {
            resourceServer.authenticationEntryPoint(new AuthErrorHandler());
            resourceServer.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(roleConverter());
            });
        });

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> roleConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new RoleConverter());
        return jwtAuthenticationConverter;
    }
}
