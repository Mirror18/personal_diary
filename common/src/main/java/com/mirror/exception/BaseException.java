package com.mirror.exception;

import com.mirror.constant.ApiResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 基础异常
 * @author mirror
 */
@Setter
@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2612992235262400823L;
    private Integer code = null;


    public BaseException(String message) {
        super(message);
        this.code = ApiResponseCode.SERVICE_ERROR.getCode();
    }

    public BaseException(String message, Throwable t) {
        super(message, t);
        this.code = ApiResponseCode.SERVICE_ERROR.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }
}