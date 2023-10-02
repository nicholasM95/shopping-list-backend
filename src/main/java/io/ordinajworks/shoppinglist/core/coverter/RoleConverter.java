package io.ordinajworks.shoppinglist.core.coverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        if (jwt.hasClaim("cognito:groups")) {
            List<String> groups = jwt.getClaimAsStringList("cognito:groups");
            return groups.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
