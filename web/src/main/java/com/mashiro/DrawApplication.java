package com.mashiro.draw.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan("com.mashiro.draw.mapper")
@SpringBootApplication
public class DrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrawApplication.class, args);
        System.out.println("DrawAi启动成功，" +
                "访问地址为：http://127.0.0.1:8080/doc.html");
    }

}
