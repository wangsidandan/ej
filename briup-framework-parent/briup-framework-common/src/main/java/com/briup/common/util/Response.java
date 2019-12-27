package com.briup.common.util;

import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * web响应实体类
 */
@SuppressWarnings("all")
public class Response<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer status;
    private String code;
    private String message;
    //    @JsonIgnore
    private Collection<FieldError> fieldErrors;
    //    @JsonIgnore
    private T data;

    /**
     *  默认成功响应，并携带数据
     */
    private Response(T data) {
        this.status = 200;
        this.code = "ok";
        this.message = "成功";
        this.data = data;
    }

    /**
     *  默认成功响应，并携带数据
     */
    private Response(T data,String message) {
        this.status = 200;
        this.code = "ok";
        this.message = message;
        this.data = data;
    }

    /**
     *  默认的成功响应，不携带数据
     */
    private Response() {
        this(null);
    }

    /**
     *  自定义响应，不携带数据
     */
    private Response(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    /**
     *  自定义响应,携带数据
     */
    private Response(Integer status, String code, String message,T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 200成功，带数据的响应返回
     */
    public static <T> Response<T> ok(T data) {
        return new Response<>(data);
    }

    /**
     * 自定义返回
     */
    public static <T> Response<T> ok(Integer status, String code, String message) {
        return new Response<>(status,code,message);
    }

    /**
     * 200成功，带数据的响应返回,并给出message信息
     */
    public static <T> Response<T> ok(T data,String message) {
        return new Response<>(data,message);
    }

    /**
     * 200成功，不带数据的响应返回
     */
    public static Response<String> ok() {
        return new Response<>();
    }


    /**
     * 操作失败，返回的响应，携带数据，携带错误信息
     * 默认是500
     */
    public static <T> Response<T> error(T data,String errorMsg) {
        Response<T> response = new Response<>(data);
        response.setStatus(500);
        response.setCode("Internal Server Error");
        response.setMessage(errorMsg);
        return response;
    }

    /**
     * 操作失败，返回的响应，不携带数据
     */
    public static <T> Response<T> error(String errorMsg) {
        return error(null,errorMsg);
    }


    /**
     * 数据校验失败，返回的响应，并携带相关数据data
     */
    private Response(T data, Collection<FieldError> fieldErrors) {
        this.status = 10000;
        this.code = "field_errors";
        this.message = "数据校验失败";
        this.fieldErrors = new HashSet<>();
        this.fieldErrors.clear();
        if (fieldErrors != null && fieldErrors.size() > 0) {
            this.fieldErrors.addAll(fieldErrors);
        }
    }
    /**
     * 数据校验失败，返回的响应，不携带相关数据data
     */
    public static Response error(Collection<FieldError> fieldErrors) {
        return new Response(null, fieldErrors);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Collection<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
