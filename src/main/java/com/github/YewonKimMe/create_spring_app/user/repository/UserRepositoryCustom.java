package com.github.YewonKimMe.create_spring_app.user.repository;

import com.github.YewonKimMe.create_spring_app.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUsername(String username);
}
