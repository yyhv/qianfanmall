package com.lyqf.qianfanmall.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.lyqf.qianfanmall.db", "com.lyqf.qianfanmall.core", "com.lyqf.qianfanmall.wx"})
@MapperScan("com.lyqf.qianfanmall.db.dao")
@EnableTransactionManagement
@EnableScheduling
public class WxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class, args);
    }

}