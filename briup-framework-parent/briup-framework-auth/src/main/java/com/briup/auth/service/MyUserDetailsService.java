package com.briup.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.mapper.extend.BaseUserExtendMapper;
/*
 * UserDetailsService默认有实现
 */
@Service
public class MyUserDetailsService implements UserDetailsService{
	//userService接口的业务
	@Autowired
	private BaseUserExtendMapper baseUserExtendMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 BaseUserExtend baseUserExtend = baseUserExtendMapper.findUserByName(username);
		// System.out.println(baseUserExtend);
		if(baseUserExtend == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		List<GrantedAuthority> list = new ArrayList<>();
		baseUserExtend.getRoles().forEach(role -> role.getPrivileges().forEach(p-> list.add(new SimpleGrantedAuthority(p.getRoute()))));
		baseUserExtend.setAuthorities(list);
		/*
		 * baseUserExtend.getRoles()
		.forEach(roles->{roles.getPrivileges()
		.forEach(p->{System.out.println(p.getName());});});
		 */
		//将字段roles的值，解析为UserDetails的权限集
		//AuthorityUtils.commaSeparatedStringToAuthorityList
		//方法是spring中提供的使用逗号分隔的方法
//		List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
//		user.setAuthorities(list);
		
		return baseUserExtend;
//		return org.springframework.security.core.userdetails.User.withUsername("user").password("123").roles("USER").build();
//		return org.springframework.security.core.userdetails.User.withUserDetails(user).build();
	}
//	private List<SimpleGrantedAuthority> getAuthority(List<BaseRole> roles) {
//		List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
//		for (BaseRole baseRole : roles) {
//			list.add(new SimpleGrantedAuthority("ROLE_"+baseRole.getName()));
//		}
//		return list;
//	}y
	
	//不使用AuthorityUtils.commaSeparatedStringToAuthorityList
	//也可以自动定义解析方法
//	@SuppressWarnings("unused")
//	private List<GrantedAuthority> generateAuthorities(String roles){
//		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//		String[] roleArray = roles.split(";");
//		if(roles != null && !"".equals(roles)) {
//			for(String role : roleArray) {
//				list.add(new SimpleGrantedAuthority(role));
//			}
//		}
//		return list;
//		
//	}
	

}
