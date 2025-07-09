package com.github.YewonKimMe.create_spring_app.security.utils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface TokenStore {

    void save(String key, String token, long duration, TimeUnit timeUnit);

    Optional<String> get(String key);

    void remove(String key);
}
