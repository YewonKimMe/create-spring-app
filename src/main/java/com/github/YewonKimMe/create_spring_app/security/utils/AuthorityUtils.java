package com.github.YewonKimMe.create_spring_app.security.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class AuthorityUtils {
    public static String populateAuthorities(Collection<? extends GrantedAuthority> collections) {
        return collections.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
