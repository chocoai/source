package com.jeesite.modules.asset.guideApp.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 *
 * @author Jace Xiong
 */
@Configuration
@ConfigurationProperties(prefix = "sysLogin")
public class AddressConfig {
    /**
     * 设置登录接口地址
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
