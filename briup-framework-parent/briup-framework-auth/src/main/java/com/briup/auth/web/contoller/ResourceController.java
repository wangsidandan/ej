package com.briup.auth.web.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 获得认证信息，认证通过后，第三方应用可以请求的资源
 */
@RestController
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    
    
    @RequestMapping("/me")
    public Principal me(Principal principal) {
        logger.debug(principal.toString());
        return principal;
    }

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
    /*
     * demo远程调用此方法注销
     * 移除token即可
     */
    @GetMapping("/sign-out")
    public String revokeToken(String access_token) {
        if (consumerTokenServices.revokeToken(access_token)){
            return "注销成功";
        }else{
            return "注销失败";
        }
    }
    
}
