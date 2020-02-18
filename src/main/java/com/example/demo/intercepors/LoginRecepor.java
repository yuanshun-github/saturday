package com.example.demo.intercepors;

import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.filter.TokenRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 拦截器方法
 */
@Component
public class LoginRecepor implements HandlerInterceptor {


    @Autowired
    private RedisService redisService;

    private static Logger logger = LoggerFactory.getLogger(LoginRecepor.class);

    /**
     * 登录方法拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isFlag = false;
        String token = request.getParameter("token");
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        String servletPath = request.getServletPath();
        System.out.println(requestURL);
        System.out.println(requestURI);
        System.out.println(servletPath);
        if(!requestURI.equals("/index.html")){
            if(token != null){
                Integer userId = (Integer)redisService.get(token);
                if(userId != null){
                    isFlag = true;
                    logger.info("用户"+userId+"：进入系统进行操作");
                }else{
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html; charset=utf-8");
                    response.setContentType("application/json");
                    PrintWriter writer = response.getWriter();
                    writer.println(WxResult.getInvalidTokenReselt("token过期，重新登录"));
                    writer.flush();
                    writer.close();
                }
            }else{
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.println(WxResult.getInvalidTokenReselt("token过期，重新登录"));
                writer.flush();
                writer.close();
            }
        }else{
            isFlag = true;
        }
        return isFlag;
    }
}
