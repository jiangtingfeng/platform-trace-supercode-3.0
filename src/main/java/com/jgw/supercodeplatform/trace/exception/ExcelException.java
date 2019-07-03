package com.jgw.supercodeplatform.trace.exception;

import com.jgw.supercodeplatform.exception.SuperCodeException;

/**
 * 描述：
 * <p>
 * Created by corbett on 2018/09/25.
 */
public class ExcelException extends SuperCodeException {
    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, int status) {
        super(message, status);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
