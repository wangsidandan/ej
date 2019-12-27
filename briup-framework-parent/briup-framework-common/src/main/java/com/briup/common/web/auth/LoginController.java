package com.briup.common.web.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.briup.common.constant.CommonConstants;
import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.exception.CommonException;
import com.briup.common.logging.LoggingAccess;
import com.briup.common.util.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Api(tags = "用户登录模块")
@LoggingAccess("登录模块")
//配置demo远程调用commons模块时传过来的参数  application.yml
@ConfigurationProperties(prefix = "security.oauth2.client")
@RestController
public class LoginController {
	
    //http://localhost:9999/oauth/token  url from application.yml
    private String accessTokenUri;
    
    private String authServer = "http://localhost:9999";
    //public static final String SIGN_OUT = "/sign-out";
    private String signOut = authServer+CommonConstants.SIGN_OUT;

    private RestTemplate restTemplate;

    private String clientId;
    private String clientSecret;

    private String grantType;

    private RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }
    

    @ApiOperation(value = "登录获取token",notes = "登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,defaultValue = "xiaoma"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,defaultValue = "123456"),
    })
    @PostMapping("/login")
    public JSONObject login(String username,String password) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, header);

        return this.postForObject(requestEntity);

//        JSONObject json = null;
//		try {
//			json = getRestTemplate().postForObject(accessTokenUri, requestEntity, JSONObject.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DmsException("用户名密码错误");
//		}
//
//        json.put("msg", "Bearer "+json.get("access_token"));
//
//        return json;
    }

    @ApiOperation(value = "刷新token",notes = "使用refresh_token来获取新的token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refresh_token",value = "refresh_token",required = true),
    })
    @PostMapping("/refresh")
    public JSONObject refresh_token(String refresh_token) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData= new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refresh_token);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, header);

        return this.postForObject(requestEntity);

    }

    private JSONObject postForObject(HttpEntity<MultiValueMap<String, String>> requestEntity){
        JSONObject json;
        try {
        	//通过restTemplate远程调用 //http://localhost:9999/oauth/token  url from application.yml 产生token
            json = getRestTemplate().postForObject(accessTokenUri, requestEntity, JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("获取token失败："+e.getMessage());
        }

        json.put("Authorization", "Bearer "+json.get("access_token"));
        
        JSONObject model = new JSONObject();
        model.put("status",200);
        model.put("code","ok");
        model.put("message","成功");
        model.put("data",json);
		/*
		 * "status": 200, "code": "ok", "message": "成功", "data": { "access_token":
		 * "30d65897-3a26-46ee-b5a3-e9a3f15ae84a", "token_type": "bearer",
		 * "refresh_token": "52bef854-7198-4d1c-8cbd-c889abc03e77", "expires_in": 4537,
		 * "scope": "all", "Authorization":
		 * "Bearer 30d65897-3a26-46ee-b5a3-e9a3f15ae84a"
		 */
        return model;
    }



    @ApiOperation(value = "退出",notes = "退出token失效")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token",value = "access_token",required = true),
    })
    @GetMapping("/sign-out")
    public Response<String> revokeToken(String access_token) {
        try {
        	//http://localhost:9999/signOut
            String result = getRestTemplate().getForObject(signOut+"?access_token="+access_token,String.class);
            return Response.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            return Response.error("注销失败:"+e.getMessage());
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

	public String getAuthServer() {
		return authServer;
	}

	public void setAuthServer(String authServer) {
		this.authServer = authServer;
	}
}
