package com.lyqf.qianfanmall.core.config;

import cn.binarywang.wx.miniapp.api.WxMaSecurityService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaSecurityServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {

    @Autowired
    private WxMiniAppProperties miniAppProperties;

    @Autowired
    private WxMpProperties mpProperties;

    /**
     * 小程序配置
     */
    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(miniAppProperties.getAppId());
        config.setSecret(miniAppProperties.getAppSecret());
        config.setToken(miniAppProperties.getToken());
        config.setAesKey(miniAppProperties.getAesKey());
        return config;
    }

    /**
     * 小程序服务
     */
    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }

    /**
     * 公众号服务
     */
    @Bean
    public WxMpService wxMpService() {
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(mpProperties.getAppId());
        wxStorage.setSecret(mpProperties.getAppSecret());
        wxStorage.setToken(mpProperties.getToken());
        wxStorage.setAesKey(mpProperties.getAesKey());

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        return wxMpService;
    }

    /**
     * 公众号消息路由器
     */
    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        return new WxMpMessageRouter(wxMpService);
    }

    /**
     * 小程序内容安全检测服务
     */
    @Bean
    public WxMaSecurityService wxMaSecurityService(WxMaService wxMaService) {
        return new WxMaSecurityServiceImpl(wxMaService);
    }

    /**
     * 微信支付配置
     */
    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(miniAppProperties.getAppId());
        payConfig.setMchId(miniAppProperties.getMchId());
        payConfig.setMchKey(miniAppProperties.getMchKey());
        payConfig.setNotifyUrl(miniAppProperties.getNotifyUrl());
        payConfig.setKeyPath(miniAppProperties.getKeyPath());
        payConfig.setApiV3Key(miniAppProperties.getApiV3Key());
        payConfig.setPrivateKeyPath(miniAppProperties.getPrivateKeyPath());
        payConfig.setPrivateCertPath(miniAppProperties.getPrivateCertPath());
        payConfig.setTradeType("JSAPI");
        payConfig.setSignType("MD5");
        if (miniAppProperties.getUseSandbox() != null) {
            payConfig.setUseSandboxEnv(miniAppProperties.getUseSandbox());
        }
        return payConfig;
    }

    /**
     * 微信支付服务
     */
    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) throws WxPayException {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
