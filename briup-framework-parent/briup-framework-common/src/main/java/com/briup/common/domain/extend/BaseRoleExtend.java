package com.briup.common.domain.extend;

import java.util.List;

import com.briup.common.domain.basic.BasePrivilege;
import com.briup.common.domain.basic.BaseRole;

public class BaseRoleExtend extends BaseRole{
	private static final long serialVersionUID = 1L;
	private List<BasePrivilege>privileges;

	public List<BasePrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<BasePrivilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return "BaseRoleExtend [privileges=" + privileges + "]";
	}
	
	
}
