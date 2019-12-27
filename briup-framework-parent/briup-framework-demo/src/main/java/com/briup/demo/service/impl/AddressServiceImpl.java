package com.briup.demo.service.impl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.extend.AddressExtend;
import com.briup.common.mapper.basic.AddressMapper;
import com.briup.common.mapper.extend.AddressExtendMapper;
import com.briup.demo.service.IAddressService;
@Service
public class AddressServiceImpl implements IAddressService{
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private AddressExtendMapper addressExtendMapper;
	
	@Override
	public List<Address> findAll() {
		// TODO Auto-generated method stub
		return addressMapper.selectAll();
	}

	@Override
	public void saveOrUpdate(@Valid Address address) {
		// TODO Auto-generated method stub
		if(address.getId()!=null) {
			addressMapper.updateByPrimaryKey(address);
		}else {
			addressMapper.insert(address);
		}
	}

	@Override
	public void deleteById(@NotNull Long id) {
		addressMapper.deleteByPrimaryKey(id);
	}
	//批量删除ID
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	@Override
	public void batchDelete(Long[] ids) {
		for (Long id : ids) {
			addressMapper.deleteByPrimaryKey(id);
		}
	}
	
	//根据customer_id查找地址
	@Override
	public Address findByCustomerId(Long customer_id) {
		return addressExtendMapper.findByCustomerId(customer_id);
	}

	@Override
	public AddressExtend findAllAddressWithCustomer() {
		// TODO Auto-generated method stub
		return addressExtendMapper.findAllAddressWithCustomer();
	}

}
