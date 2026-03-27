package com.lyqf.qianfanmall.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qianfanmall.wx.mini-app")
public class WxMiniAppProperties {

    /**
     * 小程序AppID
     */
    private String appId;

    /**
     * 小程序AppSecret
     */
    private String appSecret;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥（API V2密钥）
     */
    private String mchKey;

    /**
     * 微信支付API V3密钥
     */
    private String apiV3Key;

    /**
     * 商户证书文件路径（p12格式）
     */
    private String keyPath;

    /**
     * 商户私钥文件路径（pem格式）
     */
    private String privateKeyPath;

    /**
     * 商户证书文件路径（pem格式）
     */
    private String privateCertPath;

    /**
     * 支付回调通知地址
     */
    private String notifyUrl;

    /**
     * 消息推送配置Token
     */
    private String token;

    /**
     * 消息推送配置AesKey
     */
    private String aesKey;

    /**
     * 小程序页面路径
     */
    private String pagePath;

    /**
     * 是否使用沙盒环境
     */
    private Boolean useSandbox;
}
