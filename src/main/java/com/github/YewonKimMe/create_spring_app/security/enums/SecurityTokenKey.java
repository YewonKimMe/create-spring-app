package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SecurityTokenKey {

    ACCESS_TOKEN("access_token:"),
    REFRESH_TOKEN("refresh_token:"),

    BLACK_LIST("black_list:");

    private final String key;

    public static String generateKey(String identifier, SecurityTokenKey type) {

        return type.key + identifier;
    }
}
