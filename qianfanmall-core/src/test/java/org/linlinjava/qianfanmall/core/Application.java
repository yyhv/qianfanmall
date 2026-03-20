package org.linlinjava.qianfanmall.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.linlinjava.qianfanmall.db", "org.linlinjava.qianfanmall.core"})
@MapperScan("org.linlinjava.qianfanmall.db.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}