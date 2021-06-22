package com.wen.sso.common;

import com.wen.sso.common.util.RsaUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

/**
 * 公共测试类
 *
 * @author wenjun
 * @since 2021/6/20
 */
public class CommonTest {

    /**
     * 生成公钥、私钥文件
     *
     * @throws NoSuchAlgorithmException 异常
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        RsaUtil.generateKeyFile(
                RsaUtil.PUBLIC_KEY_FILE_PATH,
                RsaUtil.PRIVATE_KEY_FILE_PATH,
                RsaUtil.SECRET);
    }
}