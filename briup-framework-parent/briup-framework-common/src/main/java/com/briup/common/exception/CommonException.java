package com.briup.common.exception;

/**
 * 自定义异常
 */
public class CommonException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Integer status = 20000;
    private String code = "unexpected_business_exception";

    public CommonException() {
        super("业务异常");
    }

    public CommonException(Integer status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public CommonException(Integer status, String code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    public CommonException(Integer status, String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
        this.code = code;
    }



    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
