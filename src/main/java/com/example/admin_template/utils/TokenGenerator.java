package com.example.admin_template.utils;

import java.util.UUID;

public class TokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}