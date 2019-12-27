package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.Address;
import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    Address selectByPrimaryKey(Long id);

    List<Address> selectAll();

    int updateByPrimaryKey(Address record);
}