package com.example.demo.common.page;

import com.example.demo.bean.PeSituation;
import com.example.demo.common.redis.RedisService;
import com.example.demo.common.redis.handler.RedisHandler;
import com.example.demo.common.token.TokenUtils;
import com.example.demo.service.PeSituationService;
import org.springframework.beans.factory.annotation.Value;

/***
 * 页码处理类
 */
public class PageHandler {

    @Value("${problem.pe.type,num}")
    private String problemPeTypeNum;

    public static Integer getBeginNum(String token, String type, RedisService redisService){
        //获取该用户的题型数量
        Integer num = TokenUtils.getProblemNumByToken(token, redisService, type);
        //获取该类题型的数量
        Integer problemNum = (Integer)redisService.get(type);
        if (num >= problemNum)
            num = 0;
        return num;
    }

    /**
     * 判断题的数量，做的题锚点到达题量时，就重新开始
     * @param token
     * @param type
     * @param redisService
     * @param num
     * @param pageNum
     */
    public static void addBeginNum(String token, String type, RedisService redisService, Integer num, Integer pageNum, PeSituationService peSituationService){
        int newBeginNum = num + pageNum;
        Integer userId = (Integer)redisService.get(token);
        //获取该中类型题的数量
        Integer problemNum=(Integer)redisService.get(type);
        //判断该用户做题锚点是不是已经大于该题量
        if(problemNum <= newBeginNum) newBeginNum = 0;
        redisService.hmSet(""+userId,type,newBeginNum);
        //将最新的做题锚点更新到数据库
        PeSituation peSituation = RedisHandler.getPeSituationByUserId(redisService, userId);
        peSituationService.updateSituationByUserId(peSituation);
    }

}
