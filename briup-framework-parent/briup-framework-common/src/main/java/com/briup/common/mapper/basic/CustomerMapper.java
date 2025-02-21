package com.briup.common.mapper.basic;

import com.briup.common.domain.basic.Customer;
import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);
}