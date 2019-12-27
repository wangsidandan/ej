package com.briup.common.logging;

import org.springframework.util.StringUtils;

/**
 * 日志模块的辅助类，完成具体的日志记录工作
 */
public final class LoggingAccessInfoHolder {

    private static final ThreadLocal<LoggingAccessInfo> LOGGING_ACCESS_INFO_THREAD_LOCAL = new ThreadLocal<>();

    static {
    }

    public static final LoggingAccessInfo initial() {
        LoggingAccessInfo loggingAccessInfo = LOGGING_ACCESS_INFO_THREAD_LOCAL.get();
        if (loggingAccessInfo == null) {
        	//构造器中，直接设置了UUID
            loggingAccessInfo = new LoggingAccessInfo();
            LOGGING_ACCESS_INFO_THREAD_LOCAL.set(loggingAccessInfo);
        }
        return loggingAccessInfo;
    }

    public static final void clear() {
        LOGGING_ACCESS_INFO_THREAD_LOCAL.remove();
    }


    /**
     * 在代码中使用该方法，可以添加日志
     * 需要在类上 或者 方法上至少一个地方加入LoggingAccess注解，最后才会生成日志记录
     * @param logging
     */
    public static final void logging(String logging) {
        LoggingAccessInfo loggingAccessInfo = LOGGING_ACCESS_INFO_THREAD_LOCAL.get();
        if (loggingAccessInfo == null) {
            loggingAccessInfo = new LoggingAccessInfo();
            LOGGING_ACCESS_INFO_THREAD_LOCAL.set(loggingAccessInfo);
        }
        String logging_ = loggingAccessInfo.getLogging();
        if (StringUtils.isEmpty(logging_)) {
            loggingAccessInfo.setLogging(logging);
        } else {
            StringBuffer loggingBuffer = new StringBuffer();
            loggingBuffer.append(logging_);
            loggingBuffer.append(",");
            loggingBuffer.append(logging);
            loggingAccessInfo.setLogging(loggingBuffer.toString());
        }
    }

    /**
     * admin系统的详细日志记录
     *
     * @param logging
     */
    public static final void adminLogging(String logging) {
        detailLoging("admin", logging);
    }
    
    /**
     * user系统的详细日志记录
     *
     * @param logging
     */
    public static final void userLogging(String logging) {
        detailLoging("user", logging);
    }

    /**
     * 添加模块后的日志记录实践
     *
     * @param modular 模块名
     * @param logging 日志内容
     */
    public static final void detailLoging(String modular, String logging) {
        LoggingAccessInfo loggingAccessInfo = LOGGING_ACCESS_INFO_THREAD_LOCAL.get();
        if (loggingAccessInfo == null) {
            loggingAccessInfo = new LoggingAccessInfo();
            LOGGING_ACCESS_INFO_THREAD_LOCAL.set(loggingAccessInfo);
        }
        String logging_ = loggingAccessInfo.getLogging();
        if (StringUtils.isEmpty(logging_)) {
            loggingAccessInfo.setLogging(logging);
        } else {
            StringBuffer loggingBuffer = new StringBuffer();
            loggingBuffer.append(logging_);
            loggingBuffer.append(",");
            loggingBuffer.append(logging);
            loggingAccessInfo.setLogging(loggingBuffer.toString());
            loggingAccessInfo.setModular(modular != null ? modular : "");
        }
    }
}
