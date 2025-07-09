package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {

    ACCESS("Access-Token"),

    REFRESH("Refresh-Token");

    private final String tokenType;
}
