package com.github.YewonKimMe.create_spring_app.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenDurationTime {

    ACCESS(1), // 24시간

    REFRESH(336); // 2주

    private final long time;
}
