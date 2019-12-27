package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.BasePrivilege;
import java.util.List;

public interface BasePrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BasePrivilege record);

    BasePrivilege selectByPrimaryKey(Long id);

    List<BasePrivilege> selectAll();

    int updateByPrimaryKey(BasePrivilege record);
}