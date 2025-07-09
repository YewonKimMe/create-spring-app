package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SecurityRedisKey {

    ACCESS_TOKEN("access_token:"),
    REFRESH_TOKEN("refresh_token:"),

    BLACK_LIST("black_list:");

    private final String key;

    public static String generateKey(String identifier, SecurityRedisKey type) {

        return type.key + identifier;
    }
}
