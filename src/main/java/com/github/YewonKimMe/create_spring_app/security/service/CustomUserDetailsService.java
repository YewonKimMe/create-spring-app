package com.github.YewonKimMe.create_spring_app.security.service;

import com.github.YewonKimMe.create_spring_app.security.user.SecurityUserDetails;
import com.github.YewonKimMe.create_spring_app.user.entity.User;
import com.github.YewonKimMe.create_spring_app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findUser = userService.findByUsername(username);

        if (findUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new SecurityUserDetails(findUser);
    }
}
