package com.github.YewonKimMe.create_spring_app.security.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthorityUtils {
    public static String populateAuthorities(Collection<? extends GrantedAuthority> collections) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collections) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
