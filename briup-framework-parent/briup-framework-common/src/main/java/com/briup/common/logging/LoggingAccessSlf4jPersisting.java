package com.briup.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 日志记录的持久化接口的一个默认实现
 */
public class LoggingAccessSlf4jPersisting implements LoggingAccessPersisting {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAccessSlf4jPersisting.class);

    @SuppressWarnings("unused")
	private ObjectMapper jacksonObjectMapper;

    public LoggingAccessSlf4jPersisting(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Override
    public void persist(LoggingAccessInfo loggingAccessInfo) {
        LOGGER.info("\n############################################################################################################################################" +
                "\n\tLOGGER:[{}]" +
                "\n############################################################################################################################################", loggingAccessInfo);
    }
}
