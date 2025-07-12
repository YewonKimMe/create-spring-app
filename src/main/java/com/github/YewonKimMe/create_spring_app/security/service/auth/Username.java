package com.github.YewonKimMe.create_spring_app.security.service.auth;

import com.github.YewonKimMe.create_spring_app.security.enums.SecurityTokenKey;
import com.github.YewonKimMe.create_spring_app.security.enums.TokenType;
import lombok.Getter;

@Getter
public class Username {

    private final String name;

    public Username(String name) {
        this.name = name;
    }

    public String generateKey(TokenType tokenType) {

        if (tokenType == TokenType.ACCESS) {
            return SecurityTokenKey.generateKey(name, SecurityTokenKey.ACCESS_TOKEN);
        }

        return SecurityTokenKey.generateKey(name, SecurityTokenKey.REFRESH_TOKEN);

    }
}
