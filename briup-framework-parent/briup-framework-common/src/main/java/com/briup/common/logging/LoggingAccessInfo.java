package com.briup.common.logging;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 完整的日志实体类，可根据情况进行修改
 */
public class LoggingAccessInfo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private String id;

    /**
     * 请求者ip
     */
    private String clientIp;

    /**
     * 请求者原始ip
     */
    private String originalIp;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 请求耗时
     */
    private long usingTime = 1;

    /**
     * 对应的方法,格式为 HTTP方法
     */
    private String requestMethod;

    /**
     * java方法 如:list()
     */
    private String declaredMethodName;

    /**
     * 对应类名
     */
    private String declaredClassName;

    /**
     * referer信息
     */
    private String referer;

    /**
     * 客户端标识
     */
    private String userAgent;

    /**
     * 对应模块的英文简称，dms-admin dms-user
     */
    private String modular;

    /**
     * 对应操作,AccessLogging 方法级别的日志说明
     */
    private String operation;

    /**
     * 操作内容 loggingAccessInfoHolder.logging 中内容
     */
    private String logging;

    /**
     * 操作人ID
     */
    private String userId;

    /**
     * 操作人真实姓名
     */
    private String realName;

    /**
     * 操作人昵称
     */
    private String nickName;

    /**
     * 操作人账户信息集合
     */
    private String accounts;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 操作是否成功; 1:成功; 0: 失败
     */
    private boolean successful;

    private Integer errorStatus;

    private String errorCode;

    private String errorMessage;

    /**
     * 日志类型，1 普通日志，2 登录日志
     */
    private Integer type;

    private String unitId;
    
    private String app;

    private String device;


    public LoggingAccessInfo() {
        this.id = UUID.randomUUID().toString();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getOriginalIp() {
        return originalIp;
    }

    public void setOriginalIp(String originalIp) {
        this.originalIp = originalIp;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public long getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(long usingTime) {
        this.usingTime = usingTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getDeclaredMethodName() {
        return declaredMethodName;
    }

    public void setDeclaredMethodName(String declaredMethodName) {
        this.declaredMethodName = declaredMethodName;
    }

    public String getDeclaredClassName() {
        return declaredClassName;
    }

    public void setDeclaredClassName(String declaredClassName) {
        this.declaredClassName = declaredClassName;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLogging() {
        return logging;
    }

    public void setLogging(String logging) {
        this.logging = logging;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Integer getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(Integer errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "LoggingAccessInfo{" + "id='" + id + '\'' + ", clientIp='"
                + clientIp + '\'' + ", originalIp='" + originalIp + '\''
                + ", requestUri='" + requestUri + '\'' + ", requestTime="
                + requestTime + ", responseTime=" + responseTime
                + ", usingTime=" + usingTime + ", requestMethod='"
                + requestMethod + '\'' + ", declaredMethodName='"
                + declaredMethodName + '\'' + ", declaredClassName='"
                + declaredClassName + '\'' + ", referer='" + referer + '\''
                + ", userAgent='" + userAgent + '\'' + ", modular='" + modular
                + '\'' + ", operation='" + operation + '\'' + ", logging='"
                + logging + '\'' + ", userId='" + userId + '\''
                + ", realName='" + realName + '\'' + ", nickName='" + nickName
                + '\'' + ", accounts='" + accounts + '\'' + ", successful="
                + successful + ", errorStatus=" + errorStatus + ", errorCode='"
                + errorCode + '\'' + ", errorMessage='" + errorMessage + '\''
                + ", type='" + type + '\'' + ", app='" + app + '\''
                + ", device='" + device + '\'' + '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
