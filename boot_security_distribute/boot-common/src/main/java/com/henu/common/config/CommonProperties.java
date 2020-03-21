package com.henu.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix="common")
public class CommonProperties {
    /*host和port属性必须保持与application.properties中的属性一致*/
    private String host;
    private String port;

    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ComponentProperties [host=" + host + ", port=" + port + "]";
    }
}
