package com.example.demo.bean;

import java.util.UUID;

/**
 * 微信
 */
public class WXSessionModel {

    public static void main(String[] args) {
        String token = UUID.randomUUID().toString();

        System.out.println("token"+token);
    }
}
