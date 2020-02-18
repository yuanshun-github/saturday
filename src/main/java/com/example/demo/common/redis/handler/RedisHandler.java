package com.example.demo.common.redis.handler;

import com.example.demo.bean.PageModel;
import com.example.demo.bean.PeSituation;
import com.example.demo.bean.User;
import com.example.demo.bean.UserSuggest;
import com.example.demo.common.redis.RedisService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserSuggestService;

/**
 * redis操作尽量在这里执行
 */
public class RedisHandler {

    /**
     * 通过userId将数据返回到redis
     * yyljybd,cspd,pdtl,slgx
     * @param redisService
     * @return
     */
    public static PeSituation getPeSituationByUserId(RedisService redisService,int userId){
        PeSituation peSituation = new PeSituation();
        Integer yyljybd = (Integer)redisService.hmGet(""+userId,"yyljybd");
        Integer cspd = (Integer)redisService.hmGet(""+userId,"cspd");
        Integer pdtl = (Integer)redisService.hmGet(""+userId,"pdtl");
        Integer slgx = (Integer)redisService.hmGet(""+userId,"slgx");
        peSituation.setYyljybd(yyljybd);
        peSituation.setCspd(cspd);
        peSituation.setPdtl(pdtl);
        peSituation.setSlgx(slgx);
        peSituation.setUserId(userId);
        System.out.println("将redis中数据取出封装为PeSituation对象中");
        return peSituation;
    }

    /**
     * 将对象存入redis对象
     * @param peSituation
     */
    public static void setPeSituationToRedis(RedisService redisService, PeSituation peSituation){
        //将做题情况保存到redis数据库
        redisService.hmSet(""+peSituation.getUserId() , "yyljybd" , peSituation.getYyljybd());
        redisService.hmSet(""+peSituation.getUserId() , "cspd" , peSituation.getCspd());
        redisService.hmSet(""+peSituation.getUserId() , "pdtl" , peSituation.getPdtl());
        redisService.hmSet(""+peSituation.getUserId() , "slgx" , peSituation.getSlgx());
        System.out.println("将做题情况保存在redis数据库中");
    }

    /**
     * 用户信息获取得到UserSuggest
     * @param redisService
     * @param token
     * @param userSuggest
     */
    public static UserSuggest getUserSuggest(RedisService redisService, String token, UserSuggest userSuggest){
        Integer userId = (Integer)redisService.get(token);
        userSuggest.setUserId(userId);
        return userSuggest;
    }






}
