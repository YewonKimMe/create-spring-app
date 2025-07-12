package com.github.YewonKimMe.create_spring_app.user.repository;

import com.github.YewonKimMe.create_spring_app.user.entity.QUser;
import com.github.YewonKimMe.create_spring_app.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        QUser user = QUser.user;

        User findUser = queryFactory.selectFrom(user)
                .leftJoin(user.authorities).fetchJoin()  // Authority 즉시 로딩
                .where(user.userId.eq(username))
                .fetchOne();
        return Optional.ofNullable(findUser);
    }
}
