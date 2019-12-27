package com.briup.common.logging.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.briup.common.logging.LoggingAccessInfo;
import com.briup.common.logging.LoggingAccessPersisting;
import com.briup.common.mapper.logging.LoggingMapper;


public class LoggingAccessPersistingJDBCImpl implements LoggingAccessPersisting {

	@Autowired
	private LoggingMapper loggingMapper;

	@Override
	public void persist(LoggingAccessInfo logging) {
		loggingMapper.insert(logging);
	}
	
}
