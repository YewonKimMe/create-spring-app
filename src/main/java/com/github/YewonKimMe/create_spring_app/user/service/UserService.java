package com.github.YewonKimMe.create_spring_app.user.service;

import com.github.YewonKimMe.create_spring_app.user.entity.User;

public interface UserService {
    User findByUsername(String username);
}
