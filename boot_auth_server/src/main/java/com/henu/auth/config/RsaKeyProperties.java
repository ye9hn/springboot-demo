package com.henu.auth.config;

import com.henu.common.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {
    private String pubKeyFile;
    private String priKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey= RsaUtils.getPublicKey(pubKeyFile);
        privateKey=RsaUtils.getPrivateKey(priKeyFile);
    }

    public String getPubKeyFile() {
        return pubKeyFile;
    }

    public void setPubKeyFile(String pubKeyFile) {
        this.pubKeyFile = pubKeyFile;
    }

    public String getPriKeyFile() {
        return priKeyFile;
    }

    public void setPriKeyFile(String priKeyFile) {
        this.priKeyFile = priKeyFile;
    }
}
