package com.briup.common.constant;

/**
 * 项目中的常量或枚举
 */
public class CommonConstants {
    /**
     * 成功
     */
    public static final String SUCCESS = "success";

    /**
     * 失败
     */
    public static final String FAILURE = "failure";

    /**
     * 用户类型 admin
     */
    public static final String ADMIN = "ROLE_ADMIN";
    /**
     * 用户类型 user
     */
    public static final String USER = "ROLE_USER";
    /**
     * 用户类型 ANONYMOUS
     */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    /**
     * 统一的退出端点
     */
    public static final String SIGN_OUT = "/sign-out";

    /**
     * 日志类型,普通日志和登录日志
     */
    public enum LOGGING_TYPE {

        NORMAL(1, "普通"),
        LOGIN(2, "登录");

        private int value;

        private String name;

        LOGGING_TYPE(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

}
