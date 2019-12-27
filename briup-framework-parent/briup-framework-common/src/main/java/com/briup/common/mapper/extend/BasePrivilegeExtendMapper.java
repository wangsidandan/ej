package com.briup.common.mapper.extend;

import java.util.List;

import com.briup.common.domain.basic.BasePrivilege;
import com.briup.common.domain.basic.BaseRole;

public interface BasePrivilegeExtendMapper {
	List<BasePrivilege>findPrivilegeByRoleId(Long roleId);
	 
}
