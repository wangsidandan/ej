package com.briup.common.mapper.logging;

import java.util.List;

import com.briup.common.logging.LoggingAccessInfo;

/**
 * 日志类的dao层接口，当前采用mybatis实现
 */
public interface LoggingMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoggingAccessInfo record);

    LoggingAccessInfo selectByPrimaryKey(String id);

    List<LoggingAccessInfo> selectAll();

    int updateByPrimaryKey(LoggingAccessInfo record);
}
