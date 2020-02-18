package com.example.demo.common.net;

import com.alibaba.fastjson.JSON;
import com.example.demo.controller.WxLoginController;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Data
public class WxResult {

    private static Logger logger = LoggerFactory.getLogger(WxResult.class);

    private String status;

    private Object data;

    public WxResult(){
    }

    public WxResult(String status){
        this.status = status;
    }


    public WxResult(String status,Object data){
        this.status = status;
        this.data = data;
    }


    /**
     * 成功返回
     * @param data
     * @return
     */
    public static String getSuccessResult(Object data){
        logger.info("成功返回："+data);
        WxResult wxResult = new WxResult(ResultCode.resultSuccess, data);
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 返回token过期的响应结果
     * @param data
     * @return
     */
    public static String getInvalidTokenReselt(Object data){
        logger.info("用户token过期响应返回");
        WxResult wxResult = new WxResult(ResultCode.InvalidToken,data);
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 封装报错信息
     * @param e
     * @return
     */
    public static String getException(Exception e){
        logger.error("异常响应返回："+e);
        WxResult wxResult = new WxResult(ResultCode.resultFail, "系统异常" + e);
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 封装报错信息
     * @param e
     * @return
     */
    public static String getException(Exception e,String error){
        logger.error("异常返回："+error);
        WxResult wxResult = new WxResult(ResultCode.resultFail, error + e);
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 封装报错信息
     * @return
     */
    public static String getException(){
        WxResult wxResult = new WxResult(ResultCode.resultFail, "系统异常");
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 封装报错信息
     * @return
     */
    public static String getException(String error){
        logger.error(error);
        WxResult wxResult = new WxResult(ResultCode.resultFail, error);
        String result = JSON.toJSON(wxResult).toString();
        return result;
    }

    /**
     * 获取登录态失效的响应
     * @param response
     */
    public static void setLoginResponse(HttpServletResponse response){
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.setContentType("application/json");
            writer = response.getWriter();
            writer.flush();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            writer.close();
        }

    }
}
