package com.wen.sso.common.util;

import cn.hutool.core.io.FileUtil;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * 非对称加密工具类
 *
 * @author wenjun
 * @since 2021/6/20
 */
public class RsaUtil {

    /**
     * 生成公钥、私钥文件的秘钥
     */
    public static final String SECRET = "secret";

    /**
     * 私钥文件路径
     */
    public static final String PRIVATE_KEY_FILE_PATH = "D:/Users/Administrator/IdeaProjects/sso/common/src/main/resources/rsa_key/id_key_rsa";

    /**
     * 公钥文件路径
     */
    public static final String PUBLIC_KEY_FILE_PATH = "D:/Users/Administrator/IdeaProjects/sso/common/src/main/resources/rsa_key/id_key_rsa.pub";

    /**
     * 非对称加密算法
     */
    private static final String ALGORITHM = "RSA";

    private static KeyFactory KEY_FACTORY;

    /**
     * 获取公钥
     *
     * @param filePath 公钥文件路径
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String filePath) throws Exception {
        byte[] bytes = readBytes(filePath);
        if (Objects.isNull(KEY_FACTORY)) {
            KEY_FACTORY = KeyFactory.getInstance(ALGORITHM);
        }
        return KEY_FACTORY.generatePublic(new X509EncodedKeySpec(bytes));
    }

    /**
     * 获取私钥
     *
     * @param filePath 私钥文件路径
     * @return 私钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String filePath) throws Exception {
        byte[] bytes = readBytes(filePath);
        if (Objects.isNull(KEY_FACTORY)) {
            KEY_FACTORY = KeyFactory.getInstance(ALGORITHM);
        }
        return KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }

    /**
     * 生成公钥、私钥文件
     *
     * @param publicKeyFilePath  公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @param secret             生成公钥、私钥文件的秘钥
     */
    public static void generateKeyFile(String publicKeyFilePath, String privateKeyFilePath, String secret) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048, new SecureRandom(secret.getBytes()));
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = Base64.getEncoder().encode(keyPair.getPublic().getEncoded());
        FileUtil.writeBytes(publicKeyBytes, publicKeyFilePath);
        byte[] privateKeyBytes = Base64.getEncoder().encode(keyPair.getPrivate().getEncoded());
        FileUtil.writeBytes(privateKeyBytes, privateKeyFilePath);
    }

    /**
     * 读取文件字节
     *
     * @param filePath 文件路径
     * @return 文件字节
     */
    private static byte[] readBytes(String filePath) {
        byte[] bytes = FileUtil.readBytes(filePath);
        return Base64.getDecoder().decode(bytes);
    }
}
