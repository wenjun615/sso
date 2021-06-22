package com.wen.sso.auth.config;

import com.wen.sso.common.util.RsaUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 公钥、私钥配置类
 *
 * @author wenjun
 * @since 2021/6/20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 私钥
     */
    private PrivateKey privateKey;

    /**
     * 私钥文件路径
     */
    private String privateKeyFilePath;

    /**
     * 公钥文件路径
     */
    private String publicKeyFilePath;

    /**
     * 创建公钥、私钥。PostConstruct 修饰的方法会在系统启动时，依赖注入以后，调用一次
     *
     * @throws Exception 异常
     */
    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtil.getPublicKey(publicKeyFilePath);
        privateKey = RsaUtil.getPrivateKey(privateKeyFilePath);
    }
}
