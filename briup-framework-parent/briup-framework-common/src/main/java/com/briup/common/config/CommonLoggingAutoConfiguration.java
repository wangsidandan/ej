package com.briup.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.briup.common.logging.LoggingAccessAspectResolver;
import com.briup.common.logging.LoggingAccessPersisting;
import com.briup.common.logging.service.LoggingAccessPersistingJDBCImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自动配置类-日志模块
 */
@ConditionalOnProperty(prefix = "briup.common.logging",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.logging")
@Configuration
@Order
public class CommonLoggingAutoConfiguration {
	private boolean enabled;
	
	@Bean
    public LoggingAccessAspectResolver loggingAccessAspectResolver(ObjectMapper jacksonObjectMapper, LoggingAccessPersisting loggingAccessPersisting) {
		return new LoggingAccessAspectResolver(jacksonObjectMapper, loggingAccessPersisting);
    }

//    @Bean
//    @ConditionalOnMissingBean(LoggingAccessPersisting.class)
//    public LoggingAccessPersisting persisting(ObjectMapper jacksonObjectMapper) {
//        return new LoggingAccessSlf4jPersisting(jacksonObjectMapper);
//    }

    @Bean
    @ConditionalOnMissingBean(LoggingAccessPersisting.class)
    public LoggingAccessPersisting loggingAccessPersisting() {
        return new LoggingAccessPersistingJDBCImpl();
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}