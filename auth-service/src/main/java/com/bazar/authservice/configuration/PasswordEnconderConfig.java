package com.bazar.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEnconderConfig {
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
