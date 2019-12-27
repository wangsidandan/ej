package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Category;
import com.briup.common.domain.extend.AddressExtend;

public interface IAddressService {
	List<Address> findAll();

	void saveOrUpdate(@Valid Address a);

	void deleteById(@NotNull Long id);

	void batchDelete(Long[] ids);
	
	Address findByCustomerId(Long customer_id);
	AddressExtend findAllAddressWithCustomer();
}
