package com.briup.common.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.briup.common.util.FieldError;

/**
 * 下载时候的异常类型
 */
public class CommonDownloadException extends CommonException {

	private static final long serialVersionUID = 1L;
	
	private Collection<FieldError> fieldErrors = new HashSet<>();

    public CommonDownloadException(Collection<FieldError> fieldErrors) {
        super(30000, "file_download_failer_error", "文件下载失败");
        fillFieldErrors(fieldErrors);
    }

    public CommonDownloadException(FieldError fieldError) {
        super(30000, "file_download_failer_error", "文件下载失败");
        fillFieldError(fieldError);
    }

    public CommonDownloadException(String beanName, String fieldName,
                                   String fieldError) {
        super(30000, "file_download_failer_error", "文件下载失败");
        fillFieldError(new FieldError(beanName, fieldName, fieldError));
    }

    public CommonDownloadException(String fieldName, String fieldError) {
        super(30000, "file_download_failer_error", "文件下载失败");
        fillFieldError(new FieldError("", fieldName, fieldError));
    }

    public CommonDownloadException(String fieldError) {
        super(30000, "file_download_failer_error", "文件下载失败");
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
            for (FieldError fieldError : fieldErrors) {
                msg.append(fieldError.getMessage()).append("\n");
            }
        }
        return msg.toString();
    }

}
