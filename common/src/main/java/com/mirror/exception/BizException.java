package com.mirror.exception;


import com.mirror.constant.ApiResponseCode;

import java.io.Serial;

/**
 * 业务处理异常
 * @author mirror
 */
public class BizException extends BaseException {

    @Serial
    private static final long serialVersionUID = 628904681759624791L;

    public BizException(String message) {
        super(ApiResponseCode.BUSINESS_ERROR.getCode(), message);
    }

    public BizException(int code, String message) {
        super(code, message);
    }

    public BizException(String message, Throwable t) {
        super(message, t);
    }
}
