package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 进行网页跳转
 */
@RestController
@RequestMapping("web")
public class WebController {

    @GetMapping("/writePe")
    public String toWritePe(){
        System.out.println("进入方法");
        return "hello";
    }
}
