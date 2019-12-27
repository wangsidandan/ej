package com.briup.common.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.briup.common.exception.handler.CommonExceptionHandler;
import com.briup.common.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自动配置类-全局异常处理器
 */
@ConditionalOnProperty(prefix = "briup.common.global-exception",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.global-exception")
@Configuration
public class CommonExceptionHandlerAutoConfig {
	private boolean enabled;
	
    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Bean
    public CommonExceptionHandler dmsExceptionHandler() {
        return new CommonExceptionHandler();
    }

    @Autowired
    private  ObjectMapper mapper;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request,response,authException)->{

            Throwable cause = authException.getCause();
            Response<String> info;
            if(cause!=null && cause instanceof InvalidTokenException){
                info = Response.ok(HttpStatus.UNAUTHORIZED.value() , "invalid_token","token无效");
            }else{
                info = Response.ok(HttpStatus.UNAUTHORIZED.value() , "Unauthorized","请先登录");
            }
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(mapper.writeValueAsString(info));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 目前测试结果发现该配置不起作用，403情况被普通的全局异常处理（DmsExceptionHandler）所拦截
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request,response,accessDeniedException)->{
            Response<String> info = Response.ok(HttpStatus.FORBIDDEN.value() , "Forbidden","无权访问");
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(mapper.writeValueAsString(info));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
    
    @ConditionalOnBean(type = "org.springframework.security.oauth2.provider.token.ResourceServerTokenServices")
    @Bean
    public CommonExceptionRestConfig dmsCommonExceptionRestConfig() {
        return new CommonExceptionRestConfig();
    }

}
