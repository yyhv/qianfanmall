package com.lyqf.qianfanmall.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui/index.html 查看在线文档
 *
 * @author enilu
 * @version 2018-07-24
 */

@Configuration
public class AdminSwagger2Configuration {
    @Bean
    public OpenAPI adminOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("qianfanmall-admin API")
                        .description("qianfanmall管理后台API")
                        .version("1.0")
                        .termsOfService("https://github.com/linlinjava/qianfanmall"));
    }
}
