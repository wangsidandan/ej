package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.Waiter;
import java.util.List;

public interface WaiterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Waiter record);

    Waiter selectByPrimaryKey(Long id);

    List<Waiter> selectAll();

    int updateByPrimaryKey(Waiter record);
}