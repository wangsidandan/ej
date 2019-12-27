package com.briup.car.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security配置，目前不需要使用
 * 保护资源使用->类似filter
 * 先拦截资源服务器，再拦截security拦截器。
 */
//@Configuration
//@EnableWebSecurity(debug = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")
//        .and().formLogin();
        http.authorizeRequests()
	        .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
        	.antMatchers("/**").authenticated()
        	.and()
        	.oauth2Login();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.ignoring().antMatchers("/favicon.ico") ;
//    }

}
