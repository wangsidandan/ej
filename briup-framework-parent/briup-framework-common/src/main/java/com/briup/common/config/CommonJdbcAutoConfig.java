package com.briup.common.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类-数据源配置
 */
@ConditionalOnProperty(prefix = "briup.common.datasource",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.datasource")
@Configuration
public class CommonJdbcAutoConfig {

    //默认值，如果读取不到配置信息，则使用默认值
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://47.101.150.92:3306/ej?characterEncoding=utf-8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8";
    private String username = "root";
    private String password = "system";
    
    private boolean enabled;
    
    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@SuppressWarnings("all")
    @Bean
    public DataSource getDataSource() {
//        System.out.println(driverClassName);
//        System.out.println(url);
//        System.out.println(username);
//        System.out.println(password);
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
