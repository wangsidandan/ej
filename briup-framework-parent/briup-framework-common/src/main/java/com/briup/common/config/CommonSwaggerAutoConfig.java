package com.briup.common.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 自动配置类-swagger配置
 */
@ConditionalOnProperty(prefix = "briup.common.swagger",name = "enabled",havingValue  = "true")
@ConfigurationProperties(prefix = "briup.common.swagger")
@EnableSwagger2
@Configuration
public class CommonSwaggerAutoConfig {

    // 默认值，如果读取不到配置信息，则使用默认值
    private String basePackage = "com.briup";
    private String title = "briup-api-document";
    private String description = "杰普软件科技有限公司（Briup Technology,Inc. ）";
    private String termsOfServiceUrl = "http://www.briup.com";
    private String version = "1.0";
    
    private boolean enabled;
    
	// 可以在yml文件中进行配置
    private List<String> antPaths = Arrays.asList("/user/**","/admin/**");


    @Bean
    public Docket createRestApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
                .ignoredParameterTypes(Principal.class, HttpServletResponse.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl("http://www.briup.com")
                .version(termsOfServiceUrl)
                .build();
    }

    private List<ApiKey> security() {
        return Collections.singletonList(
                new ApiKey("Authorization", "Authorization", "header")
        );
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(antPathsCondition(antPaths))
                        .build()
        );
    }

    private Predicate<String> antPathsCondition(List<String> antPaths){

        if(antPaths==null||antPaths.size()==0) {
            antPaths = new ArrayList<>();
            antPaths.add("/**");
        }

        List<Predicate<String>> list = new ArrayList<>();

        antPaths.forEach(path->list.add(PathSelectors.ant(path)));

        return Predicates.or(list);

    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(
                new SecurityReference("Authorization", authorizationScopes));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<String> getAntPaths() {
        return antPaths;
    }

    public void setAntPaths(List<String> antPaths) {
        this.antPaths = antPaths;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
}
