package com.briup.common.domain.extend;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.briup.common.domain.basic.BaseUser;

public class BaseUserExtend extends BaseUser implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private List<GrantedAuthority> authorities;
	private List<BaseRoleExtend> roles;

	public List<BaseRoleExtend> getRoles() {
		return roles;
	}

	public void setRoles(List<BaseRoleExtend> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*
		 * 报错： java.util.LinkedHashMap cannot be cast to
		 * com.briup.common.domain.extend.BaseRoleExtend; nested exception is
		 * com.fasterxml.jackson.databind.JsonMappingException:
		 */
//		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
//			for (BaseRoleExtend role : getRoles()) {
//				for (BasePrivilege privilege :role.getPrivileges() ) {
//					//此处将该用户的权限信息添加到GrantedAuthority对象中
//					list.add(new SimpleGrantedAuthority(privilege.getRoute()));
//				}
//			}
//			list.add(new SimpleGrantedAuthority("user/find"));
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "BaseUserExtend [roles=" + roles + "]";
	}

}
