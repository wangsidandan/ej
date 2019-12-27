package com.briup.auth.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.ClassUtils;
/*
 * 合作机构需要oauth2认证的步骤：
 * 	1.申请获取appId和appKey
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
    @Autowired
    private DataSource dataSource;
    
    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点,/oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security
        .tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
        .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token 允许check_token访问
        .allowFormAuthenticationForClients();//允许表单认证
    }

    /**
     * 此处通过配置ClientDetailsService，来配置注册到此授权服务器的客户端Clients信息。
     * 注意，除非在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了
     * 一个AuthenticationManager，否则密码授权方式不可用。
     * 至少要配置一个client，否则服务器将不会启动。
     * 在内存中或者数据库中配置AuthenticationManager的账号和密码
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients.jdbc(dataSource);
    	/*	 配置appId,appKey,回调地址,token有效期
    	 *   clients.inMemory()
                .withClient("test")//客户端ID
                .authorizedGrantTypes("password", "refresh_token")//设置验证方式
                .scopes("read", "write") //授权范围
                .secret("123456")//客户端密钥 可以加密设置.encode
                .accessTokenValiditySeconds(10000) //token过期时间
                .refreshTokenValiditySeconds(10000); //refresh过期时间
                .redirectUris //回调地址
                
    	 */
    }
    
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
	private UserDetailsService userDetailsService;

    /**
     * 该方法是用来配置授权服务器端点特性（Authorization Server endpoints），主要是一些非安全的特性。
     * 比如token存储、token自定义、授权类型等等的
     * 默认不需要任何配置如果是需要密码授权则需要提供一个AuthenticationManager
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	DefaultAccessTokenConverter defaultAccessTokenConverter=new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new MyUserAuthenticationConverter());
        
        endpoints.tokenStore(tokenStore())
        		 .authenticationManager(authenticationManager)
        		 .accessTokenConverter(defaultAccessTokenConverter)
        		//配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
				.userDetailsService(userDetailsService)
        		 .reuseRefreshTokens(false);
    }
    
    
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource) {
        	@Override
        	protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        		return MySerializationUtils.deserialize(token);
        	}
        	@Override
        	protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        		return MySerializationUtils.serialize(token);
        	}
        	@Override
        	protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        		return MySerializationUtils.deserialize(authentication);
        	}
        	@Override
        	protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        		return MySerializationUtils.serialize(authentication);
        	}
        	@Override
        	protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        		return MySerializationUtils.deserialize(token);
        	}
        	@Override
        	protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        		return MySerializationUtils.serialize(token);
        	}
        	
        };
//        return new JdbcTokenStore(dataSource);
//        return new InMemoryTokenStore();
    }
    
    public static class MySerializationUtils {

    	private static final List<String> ALLOWED_CLASSES;

    	static {
    		List<String> classes = new ArrayList<String>();
    		classes.add("java.lang.");
    		classes.add("java.util.");
    		classes.add("org.springframework.security.");
    		classes.add("com.briup.");
    		ALLOWED_CLASSES = Collections.unmodifiableList(classes);
    	}

    	public static byte[] serialize(Object state) {
    		ObjectOutputStream oos = null;
    		try {
    			ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
    			oos = new ObjectOutputStream(bos);
    			oos.writeObject(state);
    			oos.flush();
    			return bos.toByteArray();
    		}
    		catch (IOException e) {
    			throw new IllegalArgumentException(e);
    		}
    		finally {
    			if (oos != null) {
    				try {
    					oos.close();
    				}
    				catch (IOException e) {
    					// eat it
    				}
    			}
    		}
    	}

    	public static <T> T deserialize(byte[] byteArray) {
    		ObjectInputStream oip = null;
    		try {
    			oip = new SaferObjectInputStream(new ByteArrayInputStream(byteArray),
    					Thread.currentThread().getContextClassLoader(), ALLOWED_CLASSES);
    			@SuppressWarnings("unchecked")
    			T result = (T) oip.readObject();
    			return result;
    		}
    		catch (IOException e) {
    			throw new IllegalArgumentException(e);
    		}
    		catch (ClassNotFoundException e) {
    			throw new IllegalArgumentException(e);
    		}
    		finally {
    			if (oip != null) {
    				try {
    					oip.close();
    				}
    				catch (IOException e) {
    					// eat it
    				}
    			}
    		}
    	}

    	private static class SaferObjectInputStream extends ObjectInputStream {

    		private final List<String> allowedClasses;

    		private final ClassLoader classLoader;

    		SaferObjectInputStream(InputStream in, ClassLoader classLoader, List<String> allowedClasses)
    				throws IOException {

    			super(in);
    			this.classLoader = classLoader;
    			this.allowedClasses = Collections.unmodifiableList(allowedClasses);
    		}

    		@Override
    		protected Class<?> resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
    			if (isProhibited(classDesc.getName())) {
    				throw new NotSerializableException("Not allowed to deserialize " + classDesc.getName());
    			}
    			if (this.classLoader != null) {
    				return ClassUtils.forName(classDesc.getName(), this.classLoader);
    			}
    			return super.resolveClass(classDesc);
    		}

    		private boolean isProhibited(String className) {
    			for (String allowedClass : this.allowedClasses) {
    				if (className.startsWith(allowedClass)) {
    					return false;
    				}
    			}
    			return true;
    		}
    	}

    }
    
    
	private class MyUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
	    @Override
	    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
	    	
	    	Map<String, Object> response = new LinkedHashMap<String, Object>();
			response.put(USERNAME, authentication);
			if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
				response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
			}
	        return response;
	    }
	}
    
}
