package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Customer;

public interface ICustomerService {
	List<Customer> findAll();

	void saveOrUpdate(@Valid Customer customer);

	void deleteById(@NotNull Long id) throws Exception;

	void batchDelete(Long[] ids) throws Exception;
	
	Customer findCustomerById(Long id);
}
