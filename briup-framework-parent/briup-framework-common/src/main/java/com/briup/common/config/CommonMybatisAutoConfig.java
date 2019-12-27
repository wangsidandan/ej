package com.briup.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.github.pagehelper.PageInterceptor;

/**
 * 自动配置类-mybatis配置
 */
@ConditionalOnProperty(prefix = "briup.common.mybatis",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.mybatis")
@MapperScan({"com.briup.common.mapper.basic","com.briup.common.mapper.extend","com.briup.common.mapper.logging","com.briup.common.domain"})
@Configuration
public class CommonMybatisAutoConfig {
	private boolean enabled;
	
    //默认值，如果读取不到配置信息，则使用默认值
    private String mapperLocations = "classpath*:mapper/**/*Mapper.xml";

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration cfg = new org.apache.ibatis.session.Configuration();//configuration
        sqlSessionFactoryBean.setConfiguration(cfg);
       
        return sqlSessionFactoryBean;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
