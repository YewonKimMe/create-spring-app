package com.github.YewonKimMe.create_spring_app.security.user;

import com.github.YewonKimMe.create_spring_app.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUserDetails implements UserDetails {

    private final User user;

    public SecurityUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.getIsExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.getIsBlocked();
    }

    @Override
    public boolean isEnabled() {
        return this.user.getIsEnabled();
    }
}
