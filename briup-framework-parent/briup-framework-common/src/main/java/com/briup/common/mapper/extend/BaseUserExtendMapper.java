package com.briup.common.mapper.extend;

import java.util.List;

import com.briup.common.domain.basic.BaseRole;
import com.briup.common.domain.basic.BaseUser;
import com.briup.common.domain.extend.BaseRoleExtend;
import com.briup.common.domain.extend.BaseUserExtend;

public interface BaseUserExtendMapper {
	BaseUserExtend findUserByName(String username);
	List<BaseUserExtend> cascadeFindAll();
}
