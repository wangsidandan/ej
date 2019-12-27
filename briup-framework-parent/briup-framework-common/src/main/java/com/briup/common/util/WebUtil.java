package com.briup.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public final class WebUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

    public static final void revoke(HttpServletRequest request, HttpServletResponse response, String... parameters) {
        if (response == null || parameters == null || parameters.length < 1) {
            return;
        }

        List<Cookie> parameterCookies = new ArrayList<>();

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] != null && parameters[i].equalsIgnoreCase(cookie.getName())) {
                        parameterCookies.add(cookie);
                        break;
                    }
                }
            }
        }

        for (Cookie cookie : parameterCookies) {
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public static final String extract(HttpServletRequest request, String parameter) {
        if (request == null || parameter == null) {
            return null;
        }

        Cookie parameterCookie = null;

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (parameter.equalsIgnoreCase(cookie.getName())) {
                    parameterCookie = cookie;
                    break;
                }
            }
        }

        if (parameterCookie != null) {
            return parameterCookie.getValue();
        }


        String parameterHeaderValue = request.getHeader(parameter);

        if (parameterHeaderValue != null) {
            return parameterHeaderValue;
        }

        String parameterValue = request.getParameter(parameter);

        if (parameterValue != null) {
            return parameterValue;
        }

        return null;
    }

    public static final void addCookie(HttpServletResponse response,
                                       String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path,
                                       String domain,
                                       Boolean secure,
                                       Boolean httpOnly,
                                       Integer version,
                                       String comment) {

        Assert.notNull(response.isCommitted(), "response.isCommitted() is not null");
        Assert.notNull(response, "response is not null");
        Assert.notNull(cookieName, "cookieName is not null");
        Assert.notNull(cookieValue, "cookieValue is not null");
        Assert.notNull(maxAge, "maxAge is not null");
        Assert.notNull(path, "path is not null");
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (secure != null) {
            cookie.setSecure(secure);
        }
        if (httpOnly != null) {
            cookie.setHttpOnly(httpOnly);
        }
        if (version != null) {
            cookie.setVersion(version);
        }
        if (comment != null) {
            cookie.setComment(comment);
        }

        response.addCookie(cookie);
    }

    public static final void addCookie(HttpServletResponse response, String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path,
                                       String domain,
                                       Boolean secure,
                                       Boolean httpOnly,
                                       Integer version) {
        addCookie(response, cookieName, cookieValue, maxAge, path, domain, secure, httpOnly, version, null);
    }

    public static final void addCookie(HttpServletResponse response, String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path,
                                       String domain,
                                       Boolean secure,
                                       Boolean httpOnly) {
        addCookie(response, cookieName, cookieValue, maxAge, path, domain, secure, httpOnly, null, null);
    }

    public static final void addCookie(HttpServletResponse response, String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path,
                                       String domain,
                                       Boolean secure) {
        addCookie(response, cookieName, cookieValue, maxAge, path, domain, secure, null, null, null);
    }

    public static final void addCookie(HttpServletResponse response, String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path,
                                       String domain) {
        addCookie(response, cookieName, cookieValue, maxAge, path, domain, null, null, null, null);
    }

    public static final void addCookie(HttpServletResponse response, String cookieName,
                                       String cookieValue,
                                       Integer maxAge,
                                       String path) {
        addCookie(response, cookieName, cookieValue, maxAge, path, null);
    }


    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    //尝试获取当前请求的HttpServletRequest实例
    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    //解析参数列表为map
    public static Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> param = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] varr = entry.getValue();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < varr.length; i++) {
                String var = varr[i];
                if (i != 0) {
                    builder.append(",");
                }
                builder.append(var);
            }
            param.put(key, builder.toString());
        }
        return param;
    }

    //获取请求客户端的真实ip地址
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(" x-forwarded-for ");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //web应用绝对路径
    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }

    // 向客户端写回数据
    public static void write(final String context, final HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(context);
        } catch (IOException e) {
            LOGGER.error("HttpServletResponse writer context failed, {}.", e);
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

}