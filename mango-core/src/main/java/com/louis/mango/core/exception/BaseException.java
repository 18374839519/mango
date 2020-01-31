package com.louis.mango.core.exception;

/**
 * 自定义异常类
 */
public class BaseException extends RuntimeException {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
