package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.Orderline;
import java.util.List;

public interface OrderlineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Orderline record);

    Orderline selectByPrimaryKey(Long id);

    List<Orderline> selectAll();

    int updateByPrimaryKey(Orderline record);
}