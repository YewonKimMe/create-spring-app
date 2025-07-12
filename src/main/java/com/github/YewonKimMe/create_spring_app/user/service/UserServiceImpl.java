package com.github.YewonKimMe.create_spring_app.user.service;

import com.github.YewonKimMe.create_spring_app.user.entity.User;
import com.github.YewonKimMe.create_spring_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " : 유저가 존재하지 않습니다."));
    }
}
