package com.briup.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自动配置类-跨域访问
 */
@ConditionalOnProperty(prefix = "briup.common.auth",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.auth")
@ComponentScan({"com.briup.common.web.auth"})
@Configuration
public class CommonWebAutoConfig implements WebMvcConfigurer {
	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
