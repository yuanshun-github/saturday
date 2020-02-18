package com.example.demo.controller;

import com.example.demo.bean.PageModel;
import com.example.demo.bean.UserSuggest;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.common.redis.handler.RedisHandler;
import com.example.demo.service.UserSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("suggest")
public class UserSuggestController {

    @Value("${suggest.num}")
    private Integer sheet;

    @Resource
    private UserSuggestService userSuggestService;

    @Autowired
    private RedisService redisService;

    /**
     * 保存用户的建议到数据库
     * @param userSuggest
     */
    @RequestMapping("saveSuggest")
    public void insertUserSuggest(@ModelAttribute UserSuggest userSuggest,HttpServletRequest request){
        String token = request.getParameter("token");
        RedisHandler.getUserSuggest(redisService,token,userSuggest);
        userSuggestService.insertSuggest(userSuggest);
    }


    @RequestMapping("selectSuggestByUser")
    public String selectSuggestByUserId(HttpServletRequest request){
        String beginStr = request.getParameter("begin");
        String token = request.getParameter("token");
        List<UserSuggest> suggestsByUser = getSuggestsByUser(token, beginStr);
        String result = "";
        if(suggestsByUser == null){
            result = WxResult.getException("没有数据");
        }else{
            result = WxResult.getSuccessResult(suggestsByUser);
        }
        return result;
    }

    @RequestMapping("selectSuggest")
    public String selectSuggest(HttpServletRequest request){
        String beginStr = request.getParameter("begin");
        List<UserSuggest> suggests = getSuggests(beginStr);
        String result = "";
        if(suggests == null){
            result = WxResult.getException("没有数据");
        }else{
            result = WxResult.getSuccessResult(suggests);
        }
        return result;
    }

    public List<UserSuggest> getSuggests(String beginStr){
        List<UserSuggest> userSuggests = null;
        Integer num = (Integer)redisService.get("suggest");
        Integer begin = Integer.parseInt(beginStr);
        if(begin <= num){
            userSuggests = userSuggestService.selectSuggest(begin, num);
        }
        return userSuggests;
    }


    public List<UserSuggest> getSuggestsByUser(String token, String beginStr){
        List<UserSuggest> userSuggests = null;
        //获取该用户信息
        Integer userId = (Integer)redisService.get("token");
        Integer suggest = (Integer)redisService.hmGet("" + userId, "suggest");
        int begin = Integer.parseInt(beginStr);
        Integer num = 0;
        if(suggest==null){
            num = userSuggestService.selectNumByUserId(userId);
            redisService.hmSet(""+userId,"suggest",num);
        }
        //比较是否已经到最大的条数
        if (begin < num){
            userSuggests = userSuggestService.selectSuggestByUserId(userId, begin, sheet);
        }
        return userSuggests;
    }

}
