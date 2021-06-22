package com.wen.sso.auth.common.exception;

import com.wen.sso.auth.common.api.CommonCode;
import lombok.Getter;

/**
 * 自定义 API 异常
 *
 * @author wenjun
 * @since 2020/12/17
 */
@Getter
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 5067589373508363550L;

    /**
     * 公共响应码枚举
     */
    private CommonCode commonCode;

    public ApiException(CommonCode commonCode) {
        super(commonCode.getMessage());
        this.commonCode = commonCode;
    }

    public ApiException(String message) {
        super(message);
    }
}
