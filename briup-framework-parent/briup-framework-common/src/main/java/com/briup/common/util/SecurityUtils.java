package com.briup.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;

import com.briup.common.constant.CommonConstants;
import com.briup.common.domain.basic.BaseUser;
import com.briup.common.domain.extend.BaseUserExtend;


@SuppressWarnings("unchecked")
public final class SecurityUtils {

	private SecurityUtils() {
	}

	public static boolean isAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().stream()
					.noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(CommonConstants.ANONYMOUS));
		}
		return false;
	}

	public static boolean isCurrentUserInRole(String authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
		}
		return false;
	}

	public static Authentication authentication() {
		if (SecurityUtils.isAuthenticated()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication!=null) {
				return authentication;
			}
//			if (authentication != null && authentication instanceof OAuth2Authentication) {
//				return ((OAuth2Authentication) authentication).getUserAuthentication();
//			}
//			if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
//				return ((UsernamePasswordAuthenticationToken) authentication);
//			}
//			if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {
//				return ((OAuth2AuthenticationToken) authentication);
//			}
		} else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication instanceof AnonymousAuthenticationToken) {
				authentication.setAuthenticated(false);
			}
			return (AnonymousAuthenticationToken) authentication;
		}
		return null;
	}

	public static BaseUserExtend getCurrentUserInfo() {
		Authentication authentication = authentication();
		BaseUserExtend baseUserExtend = new BaseUserExtend();

		if (authentication == null || authentication.getPrincipal() == null) {
			return baseUserExtend;
		}

		if (authentication.getPrincipal() instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) authentication.getPrincipal();
			if (map.get("principal") instanceof Map) {
				Map<String, Object> data = (Map<String, Object>) map.get("principal");
				for (String key : data.keySet()) {
					Object value = data.get(key);
					if (null == value || "".equals(value)) {
						continue;
					}
					Field field = ReflectionUtils.findField(baseUserExtend.getClass(), key);
					if (field != null) {
						
						if(field.getName().equalsIgnoreCase("password")) {
							continue;
						}
						
						Method m = ReflectionUtils.findMethod(baseUserExtend.getClass(), setMethodName(key), field.getType());
						if (m != null) {
							try {
								// setId(Long) 传入 Integer类型对象，会报错
								if (field.getType() == Long.class && value.getClass() == Integer.class) {
									value = ((Integer) value).longValue();
								}
								ReflectionUtils.invokeMethod(m, baseUserExtend, value);
							} catch (Exception e) {

							}
						}
					}
				}
			}

		}

		return baseUserExtend;

	}

	public static String setMethodName(String name) {
		return "set" + toLowerCaseFirstOne(name);
	}

	public static String getMethodName(String name) {
		return "get" + toLowerCaseFirstOne(name);
	}

	public static String toLowerCaseFirstOne(String str) {
		if (null == str || "".equals(str)) {
			return "";
		}
		if (str.length() == 1) {
			return str.toUpperCase();
		}
		return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
	}

}
