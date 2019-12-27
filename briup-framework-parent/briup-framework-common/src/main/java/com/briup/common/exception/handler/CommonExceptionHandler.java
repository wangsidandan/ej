package com.briup.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
public class CommonExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

//        httpServletResponse.setContentType("application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        try {
//            httpServletResponse.getWriter().print(JSONString);
//            httpServletResponse.getWriter().flush();
//            httpServletResponse.getWriter().close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        mv.setView(view);

        if(e!=null && e instanceof AccessDeniedException){
            mv.addObject("status", HttpStatus.FORBIDDEN.value());
            mv.addObject("code", "Forbidden");
            mv.addObject("message", e.getMessage());
        }else{
            mv.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            mv.addObject("code", "Internal Server Error");
            mv.addObject("message", "数据异常，操作失败");
            e.printStackTrace();
        }
        return mv;
    }
}
