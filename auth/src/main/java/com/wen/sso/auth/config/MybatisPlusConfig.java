package com.wen.sso.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis-Plus 配置类
 *
 * @author wenjun
 * @since 2021/6/20
 */
@Configuration
@MapperScan({"com.wen.sso.auth.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {
}
