package com.briup.common.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.briup.common.exception.CommonDataValidateFailedException;
import com.briup.common.exception.CommonDownloadException;



public final class Asserts {

    private static final Validator VALIDATOR;

    static {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target    字符序列
     * @param pattern   正则规则
     * @param beanName  对象名
     * @param fieldName 字段名
     * @param message   提示消息
     */
    public static void match(String target, Pattern pattern, String beanName, String fieldName, String message) {

        //notEmpty(target);

        Assert.notNull(pattern, "校验正则表达式不能为空");
        Assert.notNull(message, "校验失败提示消息不能为空");

        boolean matcher = pattern.matcher(target).matches();

        if (matcher) {
            return;
        }

        throw new CommonDataValidateFailedException(new FieldError(
                StringUtils.isEmpty(beanName) ? "" : beanName,
                StringUtils.isEmpty(fieldName) ? "" : fieldName,
                message
        ));
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target    字符序列
     * @param pattern   正则规则
     * @param fieldName 字段名
     * @param message   提示消息
     */
    public static void match(String target, Pattern pattern, String fieldName, String message) {

        match(target, pattern, null, fieldName, message);
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target  字符序列
     * @param pattern 正则规则
     * @param message 提示消息
     */
    public static void match(String target, Pattern pattern, String message) {

        match(target, pattern, null, message);
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target  字符序列
     * @param pattern 正则规则
     */
    public static void match(String target, Pattern pattern) {

        match(target, pattern, "非法的数据格式");
    }

    /**
     * 校验对象是否为空
     *
     * @param target    字符序列、数组、对象、集合
     * @param beanName  对象名
     * @param fieldName 对象内字段名
     * @param message   提示消息
     */
    public static void notEmpty(Object target, String beanName, String fieldName, String message) {

        if (!ObjectUtils.isEmpty(target)) {
            return;
        }

        throw new CommonDataValidateFailedException(new FieldError(
                StringUtils.isEmpty(beanName) ? "" : beanName,
                StringUtils.isEmpty(fieldName) ? "" : fieldName,
                message == null ? "" : message
        ));
    }

    /**
     * 校验对象是否为空
     *
     * @param target    字符序列、数组、对象、集合
     * @param fieldName 对象内字段名
     * @param message   提示消息
     */
    public static void notEmpty(Object target, String fieldName, String message) {

        notEmpty(target, "", fieldName, message);
    }

    /**
     * 校验对象是否为空
     *
     * @param target  字符序列、数组、对象、集合
     * @param message 提示消息
     */
    public static void notEmpty(Object target, String message) {

        notEmpty(target, "", message);
    }

    /**
     * 校验对象是否为空
     *
     * @param target 字符序列、数组、对象、集合
     */
    public static void notEmpty(Object target) {

        notEmpty(target, "关键数据不能为空");
    }

    /**
     * 校验数字是否超过最大值
     *
     * @param target    被校验数字
     * @param max       最大值
     * @param fieldName 数字名
     * @param message   提示消息
     */
    public static void max(Object target, Integer max, String fieldName, String message) {

        if (null == target) {

            return;
        }
        if (!(target instanceof Integer)) {

            throw new CommonDataValidateFailedException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    "不能为非整数类型"
            ));
        }

        if ((Integer) target > max) {

            throw new CommonDataValidateFailedException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    StringUtils.isEmpty(message) ? "不能大于".concat("" + max) : message
            ));
        }
    }

    /**
     * 校验数字是否超过最大值
     *
     * @param target  被校验数字
     * @param max     最大值
     * @param message 提示消息
     */
    public static void max(Object target, Integer max, String message) {

        max(target, max, "", message);
    }

    /**
     * 校验数字是否超过最大值
     *
     * @param target 被校验数字
     * @param max    最大值
     */
    public static void max(Object target, Integer max) {

        max(target, max, "", "");
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target    被校验数字
     * @param min       最小值
     * @param fieldName 数字名
     * @param message   提示消息
     */
    public static void min(Object target, Integer min, String fieldName, String message) {

        if (null == target) {

            return;
        }

        if (!(target instanceof Integer)) {

            throw new CommonDataValidateFailedException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    "不能为非整数类型"
            ));
        }

        if ((Integer) target < min) {

            throw new CommonDataValidateFailedException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    StringUtils.isEmpty(message) ? "不能小于".concat("" + min) : message
            ));
        }
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target  被校验数字
     * @param min     最小值
     * @param message 提示消息
     */
    public static void min(Object target, Integer min, String message) {

        min(target, min, "", message);
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target 被校验数字
     * @param min    最小值
     */
    public static void min(Object target, Integer min) {

        min(target, min, "");
    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target    字符序列
     * @param longest   最大长度
     * @param shortest  最小长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void length(String target, Integer longest, Integer shortest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > longest || length < shortest) {

                throw new CommonDataValidateFailedException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能小于" + shortest + "且不能大于" + longest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target   字符序列
     * @param longest  最大长度
     * @param shortest 最小长度
     * @param message  提示消息
     */
    public static void length(String target, Integer longest, Integer shortest, String message) {

        length(target, longest, shortest, null, message);
    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target   字符序列
     * @param longest  最大长度
     * @param shortest 最小长度
     */
    public static void length(String target, Integer longest, Integer shortest) {

        length(target, longest, shortest, null);
    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target    字符序列
     * @param longest   最大长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void longest(String target, Integer longest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > longest) {

                throw new CommonDataValidateFailedException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能大于" + longest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target  字符序列
     * @param longest 最大长度
     * @param message 提示消息
     */
    public static void longest(String target, Integer longest, String message) {

        longest(target, longest, null, message);
    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target  字符序列
     * @param longest 最大长度
     */
    public static void longest(String target, Integer longest) {

        longest(target, longest, null);
    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target    字符序列
     * @param shortest  最小长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void shortest(String target, Integer shortest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > shortest) {

                throw new CommonDataValidateFailedException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能小于" + shortest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target   字符序列
     * @param shortest 最小长度
     * @param message  提示消息
     */
    public static void shortest(String target, Integer shortest, String message) {

        shortest(target, shortest, null, message);
    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target   字符序列
     * @param shortest 最小长度
     */
    public static void shortest(String target, Integer shortest) {

        shortest(target, shortest, null);
    }

    /**
     * 校验bean对象
     *
     * @param target   bean对象
     * @param beanName bean字段名
     * @param message  提示消息
     * @param groups   bean对象校验分组
     * @param <T>      对象泛型
     */
    public static <T> void validate(T target, String beanName, String message, Class<?>... groups) {

        notEmpty(target, beanName, "", message);

        Set<ConstraintViolation<T>> errors = VALIDATOR.validate(target, groups);
        Set<FieldError> fieldErrors = new HashSet<>();
        if (!CollectionUtils.isEmpty(errors)) {
            errors(beanName, errors, fieldErrors);

            throw new CommonDataValidateFailedException(fieldErrors);
        }
    }

    /**
     * 校验bean对象
     *
     * @param target   bean对象
     * @param beanName bean字段名
     * @param message  提示消息
     * @param <T>      对象泛型
     */
    public static <T> void validate(T target, String beanName, String message) {

        validate(target, beanName, message, Default.class);
    }

    /**
     * 校验bean对象
     *
     * @param target   bean对象
     * @param beanName bean字段名
     * @param <T>      对象泛型
     */
    public static <T> void validate(T target, String beanName) {

        validate(target, beanName, "");
    }

    /**
     * 校验bean对象
     *
     * @param target     bean对象
     * @param properties bean内需要校验的字段名
     * @param beanName   bean字段名
     * @param message    提示消息
     * @param groups     bean对象校验分组
     * @param <T>        对象泛型
     */
    public static <T> void validate(T target, String[] properties, String beanName, String message, Class<?>... groups) {

        notEmpty(target, beanName, "", message);

        if (properties != null && properties.length > 0) {
            Set<FieldError> fieldErrors = new HashSet<>();
            for (String property : properties) {
                Set<ConstraintViolation<T>> errors = VALIDATOR.validateProperty(target, property, groups);
                errors(beanName, errors, fieldErrors);
            }
            if (fieldErrors != null && fieldErrors.size() > 0) {
                throw new CommonDataValidateFailedException(fieldErrors);
            }
        }
    }

    /**
     * 校验bean对象
     *
     * @param target     bean对象
     * @param properties bean内需要校验的字段名
     * @param beanName   bean字段名
     * @param message    提示消息
     * @param <T>        对象泛型
     */
    public static <T> void validate(T target, String[] properties, String beanName, String message) {

        validate(target, properties, beanName, message, Default.class);
    }

    /**
     * 校验bean对象
     *
     * @param target     bean对象
     * @param properties bean内需要校验的字段名
     * @param beanName   bean字段名
     * @param <T>        对象泛型
     */
    public static <T> void validate(T target, String[] properties, String beanName) {

        validate(target, properties, beanName, "");
    }

    private static <T> void errors(String beanName, Set<ConstraintViolation<T>> errors, Set<FieldError> fieldErrors) {

        for (ConstraintViolation<T> error : errors) {

            Path path = error.getPropertyPath();
            FieldError fieldError = new FieldError(
                    StringUtils.isEmpty(beanName) ? "" : beanName,
                    ObjectUtils.isEmpty(path) ? "" : path.toString(),
                    error.getMessage()
            );
            fieldErrors.add(fieldError);
        }
    }

    /**
     * 校验对象是否为空,作为下载
     *
     * @param target    字符序列、数组、对象、集合
     * @param beanName  对象名
     * @param fieldName 对象内字段名
     * @param message   提示消息
     */
    public static void notEmptyAsDownload(Object target, String beanName, String fieldName, String message) {

        if (!ObjectUtils.isEmpty(target)) {
            return;
        }
//        new FieldError();

        throw new CommonDownloadException(new FieldError(
                StringUtils.isEmpty(beanName) ? "" : beanName,
                StringUtils.isEmpty(fieldName) ? "" : fieldName,
                message == null ? "" : message
        ));
    }

    /**
     * 校验对象是否为空,作为下载
     *
     * @param target    字符序列、数组、对象、集合
     * @param fieldName 对象内字段名
     * @param message   提示消息
     */
    public static void notEmptyAsDownload(Object target, String fieldName, String message) {

        notEmptyAsDownload(target, "", fieldName, message);
    }

    /**
     * 校验对象是否为空,作为下载
     *
     * @param target  字符序列、数组、对象、集合
     * @param message 提示消息
     */
    public static void notEmptyAsDownload(Object target, String message) {

        notEmptyAsDownload(target, "", message);
    }

    /**
     * 校验对象是否为空,作为下载
     *
     * @param target 字符序列、数组、对象、集合
     */
    public static void notEmptyAsDownload(Object target) {

        notEmptyAsDownload(target, "关键数据不能为空");
    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target    字符序列
     * @param longest   最大长度
     * @param shortest  最小长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void lengthAsDownload(String target, Integer longest, Integer shortest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > longest || length < shortest) {

                throw new CommonDownloadException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能小于" + shortest + "且不能大于" + longest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target   字符序列
     * @param longest  最大长度
     * @param shortest 最小长度
     * @param message  提示消息
     */
    public static void lengthAsDownload(String target, Integer longest, Integer shortest, String message) {

        lengthAsDownload(target, longest, shortest, null, message);
    }

    /**
     * 校验字符序列长度是否超过最大或最小长度
     *
     * @param target   字符序列
     * @param longest  最大长度
     * @param shortest 最小长度
     */
    public static void lengthAsDownload(String target, Integer longest, Integer shortest) {

        lengthAsDownload(target, longest, shortest, null);
    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target    字符序列
     * @param longest   最大长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void longestAsDownload(String target, Integer longest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > longest) {

                throw new CommonDownloadException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能大于" + longest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target  字符序列
     * @param longest 最大长度
     * @param message 提示消息
     */
    public static void longestAsDownload(String target, Integer longest, String message) {

        longestAsDownload(target, longest, null, message);
    }

    /**
     * 校验字符序列是否超过最大长度
     *
     * @param target  字符序列
     * @param longest 最大长度
     */
    public static void longestAsDownload(String target, Integer longest) {

        longestAsDownload(target, longest, null);
    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target    字符序列
     * @param shortest  最小长度
     * @param fieldName 字符序列字段名
     * @param message   提示消息
     */
    public static void shortestAsDownload(String target, Integer shortest, String fieldName, String message) {

        if (!StringUtils.isEmpty(target)) {

            Integer length = target.length();

            if (length > shortest) {

                throw new CommonDownloadException(new FieldError(
                        "",
                        StringUtils.isEmpty(fieldName) ? "" : fieldName,
                        StringUtils.isEmpty(message) ? "关键数据不能小于" + shortest : message
                ));
            }
        }


    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target   字符序列
     * @param shortest 最小长度
     * @param message  提示消息
     */
    public static void shortestAsDownload(String target, Integer shortest, String message) {

        shortestAsDownload(target, shortest, null, message);
    }

    /**
     * 校验字符序列是否超过最小长度
     *
     * @param target   字符序列
     * @param shortest 最小长度
     */
    public static void shortestAsDownload(String target, Integer shortest) {

        shortestAsDownload(target, shortest, null);
    }


    /**
     * 校验数字是否超过最大值
     *
     * @param target    被校验数字
     * @param max       最大值
     * @param fieldName 数字名
     * @param message   提示消息
     */
    public static void maxAsDownload(Object target, Integer max, String fieldName, String message) {

        if (null == target) {

            return;
        }
        if (!(target instanceof Integer)) {

            throw new CommonDownloadException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    "不能为非整数类型"
            ));
        }

        if ((Integer) target > max) {

            throw new CommonDownloadException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    StringUtils.isEmpty(message) ? "不能大于".concat("" + max) : message
            ));
        }
    }

    /**
     * 校验数字是否超过最大值
     *
     * @param target  被校验数字
     * @param max     最大值
     * @param message 提示消息
     */
    public static void maxAsDownload(Object target, Integer max, String message) {

        maxAsDownload(target, max, "", message);
    }

    /**
     * 校验数字是否超过最大值
     *
     * @param target 被校验数字
     * @param max    最大值
     */
    public static void maxAsDownload(Object target, Integer max) {

        maxAsDownload(target, max, "", "");
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target    被校验数字
     * @param min       最小值
     * @param fieldName 数字名
     * @param message   提示消息
     */
    public static void minAsDownload(Object target, Integer min, String fieldName, String message) {

        if (null == target) {

            return;
        }

        if (!(target instanceof Integer)) {

            throw new CommonDownloadException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    "不能为非整数类型"
            ));
        }

        if ((Integer) target < min) {

            throw new CommonDownloadException(new FieldError(
                    "",
                    StringUtils.isEmpty(fieldName) ? "" : fieldName,
                    StringUtils.isEmpty(message) ? "不能小于".concat("" + min) : message
            ));
        }
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target  被校验数字
     * @param min     最小值
     * @param message 提示消息
     */
    public static void minAsDownload(Object target, Integer min, String message) {

        minAsDownload(target, min, "", message);
    }

    /**
     * 校验数字是否超过最小值
     *
     * @param target 被校验数字
     * @param min    最小值
     */
    public static void minAsDownload(Object target, Integer min) {

        minAsDownload(target, min, "");
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target    字符序列
     * @param pattern   正则规则
     * @param beanName  对象名
     * @param fieldName 字段名
     * @param message   提示消息
     */
    public static void matchAsDownload(String target, Pattern pattern, String beanName, String fieldName, String message) {

        //notEmpty(target);

        Assert.notNull(pattern, "校验正则表达式不能为空");
        Assert.notNull(message, "校验失败提示消息不能为空");

        boolean matcher = pattern.matcher(target).matches();

        if (matcher) {
            return;
        }

        throw new CommonDownloadException(new FieldError(
                StringUtils.isEmpty(beanName) ? "" : beanName,
                StringUtils.isEmpty(fieldName) ? "" : fieldName,
                message
        ));
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target    字符序列
     * @param pattern   正则规则
     * @param fieldName 字段名
     * @param message   提示消息
     */
    public static void matchAsDownload(String target, Pattern pattern, String fieldName, String message) {

        matchAsDownload(target, pattern, null, fieldName, message);
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target  字符序列
     * @param pattern 正则规则
     * @param message 提示消息
     */
    public static void matchAsDownload(String target, Pattern pattern, String message) {

        matchAsDownload(target, pattern, null, message);
    }

    /**
     * 校验字符序列是否匹配正则规则
     *
     * @param target  字符序列
     * @param pattern 正则规则
     */
    public static void matchAsDownload(String target, Pattern pattern) {

        match(target, pattern, "非法的数据格式");
    }


    /**
     * 校验字符串是不是在指定数组中,忽略大小写
     *
     * @param str  字符串
     * @param arr  数组
     */
    public static void contains(String str,String[] arr) {

        if (ObjectUtils.isEmpty(str)) {
            throw new CommonDataValidateFailedException("数据不能为空");
        }

        if(!java.util.Arrays.asList(arr).contains(str)){
            throw new CommonDataValidateFailedException("参数值不对，需要为以下值："+java.util.Arrays.toString(arr));
        }
    }

}
