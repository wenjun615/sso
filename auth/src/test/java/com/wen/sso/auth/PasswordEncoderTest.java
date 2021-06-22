package com.wen.sso.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * 密码编码测试
 * </p>
 *
 * @author wenjun
 * @since 2021-06-22
 */
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        System.out.println("password:" + passwordEncoder.encode("123456"));
    }
}
