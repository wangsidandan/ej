package com.briup.common.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.briup.common.util.FieldError;

/**
 * 数据验证时候的异常
 */
public class CommonDataValidateFailedException extends CommonException {

	private static final long serialVersionUID = 1L;
	
	private Collection<FieldError> fieldErrors = new HashSet<>();

    public CommonDataValidateFailedException(Collection<FieldError> fieldErrors) {
        super(10000, "data_validate_failed_error", "数据校验失败");
        fillFieldErrors(fieldErrors);
    }

    public CommonDataValidateFailedException(FieldError fieldError) {
        super(10000, "data_validate_failed_error", "数据校验失败");
        fillFieldError(fieldError);
    }

    public CommonDataValidateFailedException(String beanName, String fieldName, String fieldError) {
        super(10000, "data_validate_failed_error", "数据校验失败");
        fillFieldError(new FieldError(beanName, fieldName, fieldError));
    }

    public CommonDataValidateFailedException(String fieldName, String fieldError) {
        super(10000, "data_validate_failed_error", "数据校验失败");
        fillFieldError(new FieldError("", fieldName, fieldError));
    }

    public CommonDataValidateFailedException(String fieldError) {
        super(10000, "data_validate_failed_error", "数据校验失败");
        fillFieldError(new FieldError("", "", fieldError));
    }

    public Collection<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    private final void fillFieldErrors(Collection<FieldError> fieldErrors) {
        if (Objects.nonNull(fieldErrors)) {
            this.fieldErrors.addAll(fieldErrors);
        }
    }

    private final void fillFieldError(FieldError fieldError) {
        if (Objects.nonNull(fieldErrors)) {
            this.fieldErrors.add(fieldError);
        }
    }

    @Override
    public String getMessage() {
        StringBuffer msg = new StringBuffer(super.getMessage());
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            msg.append(":");
            for (FieldError fieldError :
                    fieldErrors) {
                msg.append(fieldError.getMessage()).append("\n");
            }
        }
        return msg.toString();
    }
}
