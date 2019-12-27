package com.briup.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.briup.auth.service.MyUserDetailsService;
import com.briup.common.constant.CommonConstants;
import com.briup.common.domain.basic.BasePrivilege;
import com.briup.common.mapper.basic.BasePrivilegeMapper;
import com.briup.common.mapper.extend.BaseUserExtendMapper;

/**
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity(debug = true)
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private BasePrivilegeMapper basePrivilegeMapper;
	@Autowired
    private MyUserDetailsService myUserDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//如果使用自动配置的user和service，一定要加上这句
    	//super.configure(auth);
    	//从数据库获取账号信息
    	auth.userDetailsService(myUserDetailsService);
//    	auth.inMemoryAuthentication()
//        .withUser("user").password(passwordEncoder().encode("123")).roles("USER")
//        .and().withUser("admin").password(passwordEncoder().encode("123")).roles("ADMIN");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
//    	List<BasePrivilege> privilegeList = basePrivilegeMapper.selectAll();
//    	for (BasePrivilege basePrivilege : privilegeList) {
//    		authorizeRequests.antMatchers(basePrivilege.getRoute()).hasAuthority(basePrivilege.getName());
//		}
    	//http.csrf().disable();
    	authorizeRequests
            .antMatchers("/",CommonConstants.SIGN_OUT).permitAll()
            .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
            
            .anyRequest().hasAnyRole("USER", "ADMIN")
            .and()
            .formLogin()
            .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/favicon.ico") ;//忽略图标请求
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}
    
    @Bean
	public PasswordEncoder passwordEncoder() {
    	
//    	return new BCryptPasswordEncoder();
    	
		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.toString().equals(encodedPassword);
			}
			
		};
		
	}
    
    
}
