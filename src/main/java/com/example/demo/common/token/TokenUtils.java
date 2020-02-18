package com.example.demo.common.token;

import com.example.demo.bean.PeSituation;
import com.example.demo.bean.User;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.service.PeSituationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * token生成
 */
@Service
public class TokenUtils {

    private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    //将用户登录信息保存为半个小时
    private static Long expireTime =  30*60L;


    /**
     * 生成token，然后保存token的值到redis数据看
     * @param user
     * @param redisService
     * @param peSituation
     * @return
     */
    public static String saveWxToken(User user, RedisService redisService, PeSituation peSituation, PeSituationService peSituationService){
        String token = "";
        String result = "";
        try {
            token = UUID.randomUUID().toString();
            redisService.set(token,user.getId(),expireTime);
            logger.info("用户"+user.getId()+":保存token成功，token是："+token);
            //封装数据到响应结果
            result = WxResult.getSuccessResult(token);
            //将做题情况保存到redis数据库
            redisService.hmSet(""+peSituation.getUserId() , "yyljybd" , peSituation.getYyljybd());
            redisService.hmSet(""+peSituation.getUserId() , "cspd" , peSituation.getCspd());
            redisService.hmSet(""+peSituation.getUserId() , "pdtl" , peSituation.getPdtl());
            redisService.hmSet(""+peSituation.getUserId() , "slgx" , peSituation.getSlgx());
            //RedisHandler.setPeSituationToRedis(redisService,peSituation);
        }catch (Exception e){
            result = WxResult.getException(e,"token存续错误");
        }finally {
            return result;
        }
    }

    public static Integer getProblemNumByToken(String token,RedisService redisService,String type){
        Integer userId = (Integer)redisService.get(token);
        Integer num = (Integer)redisService.hmGet(""+userId, type);
        return num;
    }
}
