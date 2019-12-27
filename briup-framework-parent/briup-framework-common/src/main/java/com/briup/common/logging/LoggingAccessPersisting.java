package com.briup.common.logging;

/**
 * 日志记录的持久化接口，
 * ../service/包下有默认的jdbc实现
 * 当前包下也有默认的一个实现，直接输出到控制台的
 */
public interface LoggingAccessPersisting {
    void persist(LoggingAccessInfo loggingAccessInfo);
}
