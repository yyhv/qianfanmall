package com.lyqf.qianfanmall.wx.config;

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
public class WxSwagger2Configuration {
    @Bean
    public OpenAPI wxOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("qianfanmall-wx API")
                        .description("qianfanmall小商场API")
                        .version("1.0")
                        .termsOfService("https://github.com/linlinjava/qianfanmall"));
    }
}
