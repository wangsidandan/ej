package com.briup.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 * 资源服务器的指责是对来自于OAuth2 Client的access_token进行鉴权。一个资源服务器包含多个端点（接口），
 * 一部分端点是作为资源服务器的资源提供给OAuth2的Client访问,另一部分端点不由资源服务器管理
 * 有资源服务器管理的端点安全性配置在此类中，其余端点的安全性配置在SecurityConfiguration类中。
 * 当请求中包含OAuth2 access_token时Spring Security根据资源服务器配置进行过滤。
 * EnableResourceServer将会创建一个WebSecurityConfigurerAdapter执行顺序（Order）是3。在SecurityConfiguration类之前
 * 运行，有更高的优先级。
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "authorizationserver";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 只有/me端口作为资源服务器的资源
        http
        	.requestMatchers().antMatchers("/me")//配置访问权限控制，必须认证过后才可以访问
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
            .anyRequest().authenticated();

    }

}
