package com.mirror.exception;

import com.mirror.constant.ApiResponseCode;

import java.io.Serial;

/**
 * 登录异常
 * @author mirror
 */
public class LoginException extends BaseException {

    @Serial
    private static final long serialVersionUID = 979094253305695687L;

    public LoginException(String message) {
        super(ApiResponseCode.LOGIN_ERROR.getCode(), message);
    }

    public LoginException(String message, Throwable t) {
        super(message, t);
    }
}
