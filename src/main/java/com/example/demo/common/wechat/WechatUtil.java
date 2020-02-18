package com.example.demo.common.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.net.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

/**
 * 处理微信的工具类
 */
public class WechatUtil {


    /**
     *
     * @param code 微信小程序前端发送过来的code
     * @return
     * 返回json的对象，获取openid和session_key
     */
    public static JSONObject getSessionKeyOrOpenId(String code,String appid,String appSecret,String url){
        //封装参数
        HashMap<String, String> param = new HashMap<>();
        param.put("appid",appid);
        param.put("secret",appSecret);
        param.put("js_code",code);
        param.put("grant_type","authorization_code");
        //向微信服务器发送请求获取
        String result = HttpClientUtil.doPost(url, param);
        JSONObject response = JSON.parseObject(result);
        return  response;
    }


}
