package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.PeSituation;
import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.common.token.TokenUtils;
import com.example.demo.common.wechat.WechatUtil;
import com.example.demo.service.PeSituationService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("wx")
public class WxLoginController {

    private static Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.loginUrl}")
    private String url;

    @Resource
    private UserService userService;

    @Resource
    private PeSituationService peSituationService;


    @Autowired
    private RedisService redisService;


    @RequestMapping("/login")
    public String getWxCode(HttpServletRequest request){
        //获取微信小程序中发送过来的code
        String code = request.getParameter("code");
        String result = "";
        try {
            logger.info("获取到了用户的code："+code);
            //发送微信服务器，获得sessionKey和OpenId
            JSONObject sessionKeyOrOpenId = WechatUtil.getSessionKeyOrOpenId(code,appid,appSecret,url);
            logger.info("微信服务器返回session："+sessionKeyOrOpenId);
            User user = new User(sessionKeyOrOpenId.getString("openid"),2);
            user = userService.wxLogin(user);
            //用户token保存
            PeSituation peSituation = peSituationService.selectPeSituationByUserId(user.getId());
            TokenUtils tokenUtils = new TokenUtils();
            result = tokenUtils.saveWxToken(user,redisService,peSituation,peSituationService);
        }catch (Exception e){
            logger.error("登录失败");
            result = WxResult.getException();
        }finally {
            return result;
        }

    }

    /**
     * 获取用户权限
     * @param request
     * @return
     */
    @RequestMapping("/getRoleUrl")
    public String getRoles(HttpServletRequest request){
        String token = request.getParameter("token");
        String name = request.getParameter("name");
        String result = "";
        try {
            Integer userId = (Integer)redisService.get("token");
            User user = new User(userId, name);
            userService.updateUserName(user);
            List<Role> roles = userService.selectUserRole(userId);
            logger.info("用户"+name+"(id是"+userId+"):查询到权限-----"+roles);
            result = WxResult.getSuccessResult(roles);
        }catch (Exception e){
            result = WxResult.getException("查询权限异常");
        }finally {
            return result;
        }
    }








}
