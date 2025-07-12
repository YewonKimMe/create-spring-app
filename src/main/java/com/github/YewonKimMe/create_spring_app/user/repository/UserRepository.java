package com.github.YewonKimMe.create_spring_app.user.repository;

import com.github.YewonKimMe.create_spring_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long>, UserRepositoryCustom {
}
