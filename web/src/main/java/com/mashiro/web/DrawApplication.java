package com.mashiro.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrawApplication.class, args);
        System.out.println("Draw,启动!");
    }

}
