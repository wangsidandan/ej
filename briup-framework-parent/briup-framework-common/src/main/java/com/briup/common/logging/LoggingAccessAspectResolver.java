package com.briup.common.logging;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.briup.common.constant.CommonConstants;
import com.briup.common.domain.basic.BaseUser;
import com.briup.common.exception.CommonException;
import com.briup.common.util.AopUtil;
import com.briup.common.util.ClassUtil;
import com.briup.common.util.NetworkUtil;
import com.briup.common.util.SecurityUtils;
import com.briup.common.util.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 日志注解LoggingAccess的解析类
 */
@Aspect
@Order(1)
public class LoggingAccessAspectResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAccessAspectResolver.class);

    @SuppressWarnings("unused")
	private ObjectMapper jacksonObjectMapper;

    private LoggingAccessPersisting loggingAccessPersisting;

    public LoggingAccessAspectResolver(ObjectMapper jacksonObjectMapper,
                                       LoggingAccessPersisting loggingAccessPersisting) {
    	
        LOGGER.info("initialing logging processor[{}]...", loggingAccessPersisting.getClass().getName());

        this.jacksonObjectMapper = jacksonObjectMapper;
        this.loggingAccessPersisting = loggingAccessPersisting;
        if (this.loggingAccessPersisting == null) {
            loggingAccessPersisting = new LoggingAccessSlf4jPersisting(jacksonObjectMapper);
        }
    }

//    @Pointcut("within(com.briup..web.contoller..*)")
//    @Pointcut("within(com.briup..web.contoller..*)")
    @Pointcut("execution(* com.briup..web..*.*(..))")
    public void loggingPoincut() {
    }

    @AfterThrowing(pointcut = "loggingPoincut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {

        LoggingAccessInfo loggingAccessInfo = LoggingAccessInfoHolder.initial();

        loggingAccessInfo.setSuccessful(false);

        if (throwable instanceof CommonException) {
            CommonException error = (CommonException) throwable;
            Optional<CommonException> result = Optional.of(error);
            loggingAccessInfo.setErrorCode(result.map(CommonException::getCode).orElse("unkown"));
            loggingAccessInfo.setErrorStatus(result.map(CommonException::getStatus).orElse(0));
            loggingAccessInfo.setErrorMessage(result.map(CommonException::getMessage).orElse("未分类异常和错误"));
            loggingAccessInfo.setErrorMessage(loggingAccessInfo.getErrorMessage().length() >= 255 ? loggingAccessInfo.getErrorMessage().substring(0, 254) : loggingAccessInfo.getErrorMessage());
        } else {
            loggingAccessInfo.setErrorCode("unkown");
            loggingAccessInfo.setErrorStatus(0);
            loggingAccessInfo.setErrorMessage("未分类异常和错误");
        }

        try {
            loggingAccessPersisting.persist(loggingAccessInfo);
        } catch (Exception exception) {
            LOGGER.error("An exception[{}] occurred while saving the log", exception);
        }

        LoggingAccessInfoHolder.clear();
    }

    @Around("loggingPoincut()")
    public Object loggingAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	
        LoggingAccessInfo loggingAccessInfo = LoggingAccessInfoHolder.initial();
        
        /**设置当前用户的信息*/
        loggingUserDetails(loggingAccessInfo);

        //设置当前请求时间
        loggingAccessInfo.setRequestTime(new Date());

        MethodSignature methodSignature = ((MethodSignature) proceedingJoinPoint.getSignature());
        Method method = methodSignature.getMethod();
        
        //获取方法的签名，包含方法名和参数列表
        String methodName = AopUtil.getMethodName(proceedingJoinPoint);
        
        Object[] args = proceedingJoinPoint.getArgs();
        
        // target （被代理对象的类型）
        Class<?> target = proceedingJoinPoint.getTarget().getClass();

        loggingAccessInfo.setDeclaredClassName(target.getName());
        loggingAccessInfo.setDeclaredMethodName(methodName);
        
        //设置从LoggingAccess注解上获取的内容新
        //flag为true表示在类上 或者 方法上加了LoggingAccess注解
        boolean flag = loggingAnnotation(loggingAccessInfo, target, method);

        //设置请求中的相关信息
        loggingHeaders(loggingAccessInfo);

        Object results = null;
        try {
            results = proceedingJoinPoint.proceed();
        } catch (IllegalArgumentException exp) {
            LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(args), methodSignature.getDeclaringTypeName(), methodSignature.getName());
            throw exp;
        }

        //如果没有 在类上 或者 方法上任何一个地方加入LoggingAccess注解的话，那么就直接返回，不做日志记录
        if(!flag) return results;

        /**设置当前用户的信息*/
        loggingUserDetails(loggingAccessInfo);
        
        //设置响应时间
        loggingAccessInfo.setResponseTime(new Date());
        
        //设置访问结果为成功
        loggingAccessInfo.setSuccessful(true);

        LoggingAccessInfoHolder.clear();

        try {
            loggingAccessPersisting.persist(loggingAccessInfo);
        } catch (Exception exception) {
            LOGGER.error("An exception[{}] occurred while saving the log", exception);
        }

        return results;
    }

    private void loggingHeaders(LoggingAccessInfo loggingAccessInfo) {
    	
    	//获取请求对象
        HttpServletRequest request = WebUtil.getHttpServletRequest();

        if (request == null) {
            return;
        }
        
        //设置请求方式
        loggingAccessInfo.setRequestMethod(request.getMethod());

        // 取头自定义的信息（如果有的话）
        getCustomHearers(request, loggingAccessInfo);

        //设置unitId
        if (ObjectUtils.isEmpty(loggingAccessInfo.getUnitId())) {
            loggingAccessInfo.setUnitId(getUnitId(request));
        }

        // 设置uid
        loggingAccessInfo.setUid(getUid(request));

        
        //获取并设置请求中基本的头信息
        Map<String, String> headers = WebUtil.getHeaders(request);

        loggingAccessInfo.setReferer(headers.get("Referer"));//referer
        loggingAccessInfo.setUserAgent(headers.get("User-Agent"));//客户端标识
        loggingAccessInfo.setRequestUri(request.getRequestURI());//请求相对路径
        loggingAccessInfo.setClientIp(NetworkUtil.getIpAddress(request));//ip地址

        String xForwardedHost = headers.get("x-forwarded-host");
        String xForwardedPort = headers.get("x-forwarded-port");
        if (xForwardedHost != null) {
            loggingAccessInfo.setOriginalIp(xForwardedHost.concat(":").concat(xForwardedPort));//ip地址
        }

        if (StringUtils.isEmpty(loggingAccessInfo.getApp())) {

            if (!StringUtils.isEmpty(loggingAccessInfo.getDeclaredClassName())) {
                if (loggingAccessInfo.getDeclaredClassName().startsWith("com.dms.uaa")) {
                    loggingAccessInfo.setApp("uaa");
                } else {
                    String[] feilds = org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(loggingAccessInfo.getDeclaredClassName(), ".");
                    if (feilds.length > 4) {
                        loggingAccessInfo.setApp(feilds[3]);
                    }
                }
            }
        }

        if (StringUtils.isEmpty(loggingAccessInfo.getDevice())) {
            loggingAccessInfo.setDevice("pc");
        }
    }

    private String getUnitId(HttpServletRequest request) {
        return getCookieName(request, "unitId");
    }

    private void getCustomHearers(HttpServletRequest request, LoggingAccessInfo loggingAccessInfo) {
        // dms-Headers: device=pc;app=dms-user
        String customHearers = request.getHeader("dms-Headers");

        if (StringUtils.isEmpty(customHearers)) {
            loggingAccessInfo.setApp("");
            loggingAccessInfo.setDevice("");
        } else {
            String[] fields = org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(customHearers, ";");
            for (int i = 0; i < fields.length; i++) {
                if (i == 0) {
                    String[] _field = org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(fields[i], "=");
                    if (_field.length == 2) {
                        loggingAccessInfo.setDevice(_field[1]);
                    } else {
                        loggingAccessInfo.setDevice("");
                    }
                } else {
                    String[] _field = org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(fields[i], "=");
                    if (_field.length == 2) {
                        loggingAccessInfo.setApp(_field[1]);
                    } else {
                        loggingAccessInfo.setApp("");
                    }
                }
            }
        }
    }

    private void loggingUserDetails(LoggingAccessInfo loggingAccessInfo) {

        BaseUser user = SecurityUtils.getCurrentUserInfo();

        if (user == null) {
            return;
        }

        if (loggingAccessInfo == null) {
            LoggingAccessInfoHolder.initial();
        }

        if (loggingAccessInfo.getUserId() != null &&
                !"".equals(loggingAccessInfo.getUserId().trim())) {
            return;
        }

        if (loggingAccessInfo.getRealName() != null &&
                !"".equals(loggingAccessInfo.getRealName().trim())) {
            return;
        }

        if (loggingAccessInfo.getNickName() != null &&
                !"".equals(loggingAccessInfo.getNickName().trim())) {
            return;
        }


        loggingAccessInfo.setUserId(user.getId()+"");
        loggingAccessInfo.setRealName(user.getUsername());
        loggingAccessInfo.setNickName(user.getUsername());
        loggingAccessInfo.setUnitId(null);

        StringBuffer accounts = new StringBuffer();

        String username = user.getUsername();
//        String logingMobile = userDetails.getLoginMobile();
//        String loginEmail = userDetails.getLoginEmail();
//        String clientId = userDetails.getClientId();
//
        if (!StringUtils.isEmpty(username)) {
            accounts.append(username);
        }
//        if (!StringUtils.isEmpty(logingMobile)) {
//            accounts.append(",").append(logingMobile);
//        }
//        if (!StringUtils.isEmpty(loginEmail)) {
//            accounts.append(",").append(loginEmail);
//        }
//        if (!StringUtils.isEmpty(clientId)) {
//            accounts.append(",").append(clientId);
//        }
        loggingAccessInfo.setAccounts(accounts.toString());
    }

    private boolean loggingAnnotation(LoggingAccessInfo loggingAccessInfo, Class<?> target, Method method) throws JsonProcessingException {
    	
    	//获取指定方法上的指定类型注解，会自动查找父类中方法
        LoggingAccess methodAnnotation = ClassUtil.getAnnotation(method, LoggingAccess.class);
        
        //获取指定类上的指定类型注解，会自动查找父类
        LoggingAccess classAnnotation = ClassUtil.getAnnotation(target, LoggingAccess.class);

        if (classAnnotation != null) {
            loggingAccessInfo.setModular(classAnnotation.value());
        }

        //先给一个默认值
        loggingAccessInfo.setType(CommonConstants.LOGGING_TYPE.NORMAL.getValue());

        if (methodAnnotation != null) {
        	//设置注解中获取内容的，就是将来的日志内容
            loggingAccessInfo.setOperation(methodAnnotation.value());
            
            //设置日志的类型，登录日志还是普通访问日志
            if (!ObjectUtils.isEmpty(methodAnnotation.type())) {
                loggingAccessInfo.setType(methodAnnotation.type());
            }

        }

        return classAnnotation != null || methodAnnotation != null;

    }

    private String getUid(HttpServletRequest request) {
        return getCookieName(request, "userId");
    }

    private String getCookieName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

}
