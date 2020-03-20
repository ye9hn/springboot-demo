package com.henu.source.config;

import com.henu.common.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;
/**
 * 这边只做token验证不做登陆判断
 */

@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {
    private String pubKeyFile;

    private PublicKey publicKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }


    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtils.getPublicKey(pubKeyFile);
    }

    public String getPubKeyFile() {
        return pubKeyFile;
    }

    public void setPubKeyFile(String pubKeyFile) {
        this.pubKeyFile = pubKeyFile;
    }
}
