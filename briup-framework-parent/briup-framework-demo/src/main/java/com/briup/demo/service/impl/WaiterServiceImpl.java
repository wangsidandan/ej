package com.briup.demo.service.impl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.basic.Waiter;
import com.briup.common.mapper.basic.CustomerMapper;
import com.briup.common.mapper.basic.WaiterMapper;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.service.IWaiterService;
@Service
public class WaiterServiceImpl implements IWaiterService{
	@Autowired
	private WaiterMapper waiterMapper;
	@Override
	public List<Waiter> findAll() {
		return waiterMapper.selectAll();
	}
	@Override
	public void saveOrUpdate(@Valid Waiter waiter) {
		if(waiter.getId()!=null) {
			waiterMapper.updateByPrimaryKey(waiter);
		}else {
			waiterMapper.insert(waiter);
		}
		
	}
	@Override
	public void deleteById(@NotNull Long id) {
		waiterMapper.deleteByPrimaryKey(id);
	}
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	    @Override
	    public void batchDelete(long[] ids) {
	        for (long id:ids) {
	            this.deleteById(id);
	        }
	    }

	
	

}
