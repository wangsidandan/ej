package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.mapper.extend.BaseUserExtendMapper;
import com.briup.demo.service.IBaseUserServcie;
@Service
public class BaseUserServiceImpl implements IBaseUserServcie{
	@Autowired
	private BaseUserExtendMapper baseUserExtendMapper;
	@Override
	public List<BaseUserExtend> cascadeFindAll() {
		// TODO Auto-generated method stub
		return baseUserExtendMapper.cascadeFindAll();
	}
	@Override
	public BaseUserExtend findUserByName(String username) {
		// TODO Auto-generated method stub
		return baseUserExtendMapper.findUserByName(username);
	}

}
