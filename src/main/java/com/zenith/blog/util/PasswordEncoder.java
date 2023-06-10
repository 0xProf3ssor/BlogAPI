package com.zenith.blog.util;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PasswordEncoder implements Function<String, String> {
    @Override
    public String apply(String password) {
        return password;
    }
}
