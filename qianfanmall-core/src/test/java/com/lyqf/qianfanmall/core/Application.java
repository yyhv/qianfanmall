package com.lyqf.qianfanmall.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.lyqf.qianfanmall.db", "com.lyqf.qianfanmall.core"})
@MapperScan("com.lyqf.qianfanmall.db.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}