package com.briup.common.mapper.extend;

import java.util.List;

import com.briup.common.domain.basic.BaseRole;
import com.briup.common.domain.extend.BaseRoleExtend;

public interface BaseRoleExtendMapper {
	List<BaseRoleExtend>findRoleByUserId(Long userId);
	 
}
