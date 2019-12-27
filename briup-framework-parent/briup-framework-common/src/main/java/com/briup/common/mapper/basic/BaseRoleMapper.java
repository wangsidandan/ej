package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.BaseRole;
import java.util.List;

public interface BaseRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseRole record);

    BaseRole selectByPrimaryKey(Long id);

    List<BaseRole> selectAll();

    int updateByPrimaryKey(BaseRole record);
}