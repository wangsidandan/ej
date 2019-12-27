package com.briup.common.util;

import java.io.Serializable;

public class FieldError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String beanName;

    private String fieldName;

    private String message;

    public FieldError() {
    }

    public FieldError(String message) {

        this.message = message;
    }

    public FieldError(String fieldName, String message) {

        this.fieldName = fieldName;
        this.message = message;
    }

    public FieldError(String beanName, String fieldName, String message) {

        this.beanName = beanName;
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldError that = (FieldError) o;

        if (!beanName.equals(that.beanName)) {
            return false;
        }
        if (!fieldName.equals(that.fieldName)) {
            return false;
        }
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = beanName.hashCode();
        result = 31 * result + fieldName.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}