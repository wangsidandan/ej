package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.BaseUser;
import java.util.List;

public interface BaseUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseUser record);

    BaseUser selectByPrimaryKey(Long id);

    List<BaseUser> selectAll();

    int updateByPrimaryKey(BaseUser record);
}