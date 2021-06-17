package com.bezkoder.spring.data.jpa.test.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {

    public String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String encrytedPassword = new EncrytedPasswordUtils().encrytePassword(password);
        System.out.println("Encryted Password: " + encrytedPassword);
    }

}
