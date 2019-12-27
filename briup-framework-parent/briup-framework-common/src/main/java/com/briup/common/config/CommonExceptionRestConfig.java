package com.briup.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 异常信息返回rest-json格式，配合 全局异常设置 来使用
 */
public class CommonExceptionRestConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> implements ResourceServerConfigurer {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ResourceServerTokenServices tokenService;

    public OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter() {
        OAuth2AuthenticationProcessingFilter filter = new OAuth2AuthenticationProcessingFilter();

        OAuth2AuthenticationManager o = new OAuth2AuthenticationManager();

        o.setTokenServices(tokenService);

        filter.setAuthenticationManager(o);

        filter.setAuthenticationEntryPoint(authenticationEntryPoint);

        return filter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(oAuth2AuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

}
