package com.henu.commom.test;

import com.henu.common.utils.RsaUtils;
import org.junit.Test;

public class BootCommonApplicationTest {
    private String privateFilePath="D:\\auth_key\\id_key_rsa";
    private String publicFilePath="D:\\auth_key\\id_key_rsa.pub";
    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"itheima",2048);
    }
    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }
    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }
}
