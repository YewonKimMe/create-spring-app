package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UrlList {

    SIGNUP("/api/v1/sign-up"),
    LOGIN("/api/v1/login"),
    LOGOUT("/api/v1/logout"),
    USER_API_PATTERN("/api/v1/user/**"),
    ADMIN_PATTERN("/api/v1/admin/**");

    private final String url;
}
