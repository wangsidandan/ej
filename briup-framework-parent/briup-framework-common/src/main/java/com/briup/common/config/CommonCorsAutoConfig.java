package com.briup.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动配置类-跨域访问
 */
@ConditionalOnProperty(prefix = "briup.common.cors",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.cors")
@Configuration
public class CommonCorsAutoConfig implements WebMvcConfigurer {
    private static Logger logger = LoggerFactory.getLogger(CommonCorsAutoConfig.class);

    private List<String> allowDomains = new ArrayList<>();
    private boolean enabled;
    
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
    */
    
    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@SuppressWarnings("all")
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new MyCorsFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    private class MyCorsFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            logger.info("允许跨域请求的客户端地址："+allowDomains);
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

            HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest)req;

            String originHeads = request.getHeader("Origin");

            logger.debug("originHeads："+originHeads);

            if(originHeads != null && (allowDomains.contains(originHeads) || allowDomains.contains("*"))) {
                //设置允许跨域的配置
                response.setHeader("Access-Control-Allow-Origin", originHeads);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT, OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
            }

            chain.doFilter(req, res);
        }

        @Override
        public void destroy() {}
    }

    public List<String> getAllowDomains() {
        return allowDomains;
    }

    public void setAllowDomains(List<String> allowDomains) {
        this.allowDomains = allowDomains;
    }
}
