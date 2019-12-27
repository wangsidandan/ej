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
import com.briup.common.mapper.basic.CustomerMapper;
import com.briup.demo.service.ICustomerService;
@Service
public class CustomerServiceImpl implements ICustomerService{
	@Autowired
	private CustomerMapper customerMapper;
	@Override
	public List<Customer> findAll() {
		return customerMapper.selectAll();
	}
	@Override
	public void saveOrUpdate(@Valid Customer customer) {
		if(customer.getId()!=null) {
			customerMapper.updateByPrimaryKey(customer);
		}else {
			customerMapper.insert(customer);
		}
		
	}
	/*
	 * 一个方法调用另一个方法，事务控制失效
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteById(@NotNull Long id) throws Exception {
		int key = customerMapper.deleteByPrimaryKey(id);
		System.out.println(key);
		if(key<=0) {
			throw new Exception("删除失败");
		}
	}
	// @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	@Transactional(propagation = Propagation.REQUIRED)
	    @Override
	    public void batchDelete(Long[] ids) throws Exception {
	        for (long id:ids) {
	            this.deleteById(id);
	        }
	    }
	@Override
	public Customer findCustomerById(Long id) {
		return customerMapper.selectByPrimaryKey(id);
	}

	
	

}
