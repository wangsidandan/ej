package com.briup.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.briup.demo.constant.DemoConstants;

/**
 * 资源服务器配置类
 * 配置哪些资源需要认证，此处配置user/*资源需要认证，其他比如book资源不需要验证
 */
//全局方法验证，可以在demo》controller中使用 @PreAuthorize("hasRole('ADMIN')")@PreAuthorize("hasAuthority('ROLE_USER')")


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(DemoConstants.RESOURCE_ID);//配置资源服务器ID
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // /user/**端口作为资源服务器的资源，只拦截此相关路径
    	// hasRole('USER') hasRole('ROLE_USER') 都可以，但是大小写敏感
        http
    		.requestMatchers().antMatchers("/user/**")
            .and()
            .authorizeRequests()
            //放开options请求，允许访问，为跨域准备（预检查看是否能跨域）
            .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
//            可以在这设置权限，也可以在方法上使用注解
//            .antMatchers("/user").access("#oauth2.hasScope('all')")
//            .antMatchers("/user").access("hasRole('ROLE_USER')")
//            .antMatchers("/user").hasAnyAuthority("ROLE_USER")
//            .antMatchers("/oauth/token").permitAll()
            .anyRequest().authenticated();

    }

}
