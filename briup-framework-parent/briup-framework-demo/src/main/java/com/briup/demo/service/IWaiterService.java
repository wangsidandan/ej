package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.basic.Waiter;

public interface IWaiterService {
	List<Waiter> findAll();

	void saveOrUpdate(@Valid Waiter waiter);

	void deleteById(@NotNull Long id);

	void batchDelete(long[] ids);
}
