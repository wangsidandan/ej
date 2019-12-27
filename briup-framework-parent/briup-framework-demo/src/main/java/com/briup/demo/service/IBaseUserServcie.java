package com.briup.demo.service;

import java.util.List;

import com.briup.common.domain.basic.BaseUser;
import com.briup.common.domain.extend.BaseUserExtend;

public interface IBaseUserServcie {
	List<BaseUserExtend> cascadeFindAll();
	BaseUserExtend findUserByName(String username);
}
