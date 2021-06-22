package com.wen.sso.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * JWT 负载
 *
 * @author wenjun
 * @since 2021/6/20
 */
@Data
@Accessors(chain = true)
public class Payload<T> {

    /**
     * Token ID
     */
    private String id;

    /**
     * 用户信息
     */
    private T userInfo;

    /**
     * 过期时间
     */
    private Date expiration;
}
