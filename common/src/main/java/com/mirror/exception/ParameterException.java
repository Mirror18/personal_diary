package com.mirror.exception;

import com.mirror.constant.ApiResponseCode;
import lombok.Getter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数异常
 * @author mirror
 */
@Getter
public class ParameterException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, String> fieldErrors;

    public ParameterException(String message) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), message);
    }

    public ParameterException(int code, String message) {
        super(code, message);
    }

    public ParameterException(Map<String, String> fieldErrors) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), ApiResponseCode.PARAMETER_INVALID.getMessage());
        this.fieldErrors = fieldErrors;
    }

    public ParameterException(String key, String value) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), ApiResponseCode.PARAMETER_INVALID.getMessage());
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put(key, value);
        this.fieldErrors = fieldErrors;
    }

    public ParameterException(String message, Throwable t) {
        super(message, t);
    }
}
