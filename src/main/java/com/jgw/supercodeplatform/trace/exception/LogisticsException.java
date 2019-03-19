package com.jgw.supercodeplatform.trace.exception;

import com.jgw.supercodeplatform.exception.SuperCodeException;

/**
 * @Author liujianqiang
 * @Date  2019/3/13
 * @Param
 * @return
 * @Description 物流异常
 **/
public class LogisticsException extends SuperCodeException {

    public LogisticsException() {
    }

    public LogisticsException(String message) {
        super(message);
    }

    public LogisticsException(String message, Throwable cause) {
        super(message, cause);
    }
}
