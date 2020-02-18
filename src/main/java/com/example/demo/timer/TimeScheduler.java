package com.example.demo.timer;

import com.example.demo.common.redis.RedisService;
import com.example.demo.service.ProblemChoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时类，将进行定时任务
 */
@Component
public class TimeScheduler {

    @Value("${problem.type,num}")
    private String problemNumStr;

    @Resource
    private ProblemChoiceService problemChoiceService;

    @Autowired
    private RedisService redisService1;

    private static Logger logger = LoggerFactory.getLogger(TimeScheduler.class);


    //每隔6个小时执行一次60*60000
    @Scheduled(fixedRate = 6*60*60000)
    public void doForTime() {
        logger.info("开始定时刷新题库的题量");
        String[] problemNumArr = problemNumStr.split(",");
        //将各类题型保存在redis的缓存中
        for (int i=0 ; i<problemNumArr.length ; i++){
            int problemNum = problemChoiceService.selectNumByType(problemNumArr[i]);
            logger.info("题型："+problemNumArr[i]+";题数量："+problemNum);
            redisService1.set(problemNumArr[i],problemNum);
        }
    }

    //每天1：00执行执行
    @Scheduled(cron = "0 0 01 ? * *")
    public void doForDay() {
        System.out.println("定时任务执行时间每天1点执行：");
    }
}
