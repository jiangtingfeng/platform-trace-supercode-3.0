package com.jgw.supercodeplatform.trace.exception;

/**
 * 平台全局异常
 * Created by corbett on 2018/9/5.
 */
public class SuperCodeTraceException extends Exception {

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public SuperCodeTraceException() {
    }

    public SuperCodeTraceException(String message) {
        super(message);
    }

    public SuperCodeTraceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public SuperCodeTraceException(String message, Throwable cause) {
        super(message, cause);
    }
}
