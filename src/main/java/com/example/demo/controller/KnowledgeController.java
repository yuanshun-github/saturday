package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.CollectionProblem;
import com.example.demo.bean.ProblemChoice;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.page.PageHandler;
import com.example.demo.common.redis.RedisService;
import com.example.demo.common.tool.HandlerObject;
import com.example.demo.service.CollectionProblemService;
import com.example.demo.service.PeSituationService;
import com.example.demo.service.ProblemChoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("knowledge")
public class KnowledgeController {

    private static Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

    @Value("${problem.num}")
    private Integer pageNum;

    @Resource
    private ProblemChoiceService problemChoiceService;

    @Resource
    private PeSituationService peSituationService;

    @Resource
    private CollectionProblemService collectionProblemService;

    @Autowired
    private RedisService redisService;


    /**
     * 查看收藏的选择题
     * @param request
     */
    @RequestMapping("collection")
    @ResponseBody
    public String seeCollection(HttpServletRequest request){
        String token = request.getParameter("token");
        String tt = request.getParameter("userId");
        logger.info("----------------------"+tt);
        String type = request.getParameter("type");
        String beginStr = request.getParameter("begin");
        int begin = Integer.parseInt(beginStr);
        String result = "";
        try {
            Integer userId = (Integer)redisService.get(token);
            List<ProblemChoice> problemChoices = problemChoiceService.selectCollectChoice(userId, begin, pageNum);
            Object data = HandlerObject.handerChoiceProblem(problemChoices);
            logger.info("用户"+userId+":获取收藏的题："+data);
            result = WxResult.getSuccessResult(data);
        }catch (Exception e){
            result = WxResult.getException("没有获取到收藏的题");
        }finally {
            return result;
        }
    }

    /**
     * 录入题查看
     *      1，现在只支持共考题
     * @param request
     * @return
     */
    @RequestMapping("write")
    @ResponseBody
    public String seeWrite(HttpServletRequest request){
        String token = request.getParameter("token");
        String type = request.getParameter("type");
        String beginStr = request.getParameter("begin");
        int begin = Integer.parseInt(beginStr);
        String result = "";
        try {
            Integer  userId=(Integer)redisService.get(token);
            List<ProblemChoice> problemChoices = problemChoiceService.selectWritePe(userId,begin,pageNum);
            Object data = HandlerObject.handerChoiceProblem(problemChoices);
            logger.info("用户"+userId+":录入题成功");
            result = WxResult.getSuccessResult(data);
        }catch (Exception e){
            result = WxResult.getException("系统错误");
        }finally {
            return result;
        }
    }


    /**
     * 收藏题
     * @param request
     * @return
     */
    @RequestMapping("collectionProblemChoice")
    @ResponseBody
    public String collectionProblem(HttpServletRequest request){
        //获取请求参数
        String token = request.getParameter("token");
        String idStr = request.getParameter("id");
        String result = "";
        try {
            Integer userId = (Integer)redisService.get(token);
            CollectionProblem collectionProblem = new CollectionProblem();
            collectionProblem.setUserId(userId);
            collectionProblem.setProblemId(Integer.parseInt(idStr));
            collectionProblem.setIsFlag(0);
            collectionProblemService.insertCollectionProblem(collectionProblem);
            result = WxResult.getSuccessResult("收藏成功");
        }catch (Exception e){
            result = WxResult.getException("收藏失败");
        }finally {
            return result;
        }

    }


    /**
     * 取消收藏题
     * @param request
     * @return
     */
    @RequestMapping("noCollectionProblemChoice")
    @ResponseBody
    public String noCollectionProblem(HttpServletRequest request){
        //获取请求参数
        String token = request.getParameter("token");
        String idStr = request.getParameter("id");
        String result = "";
        try {
            Integer userId = (Integer)redisService.get(token);
            CollectionProblem collectionProblem = new CollectionProblem();
            collectionProblem.setUserId(userId);
            collectionProblem.setProblemId(Integer.parseInt(idStr));
            collectionProblem.setIsFlag(0);
            collectionProblemService.deleteCollectionProblem(collectionProblem);
            result = WxResult.getSuccessResult("取消收藏成功");
        }catch (Exception e){
            result = WxResult.getException("取消收藏失败");
        }finally {
            return result;
        }

    }


    /**
     * 将题进行录入到数据库
     * @param request
     * @param problemChoice
     * @return
     */
    @RequestMapping("/writeChoiceDE")
    @ResponseBody
    public String writeChoiceDE(HttpServletRequest request ,@ModelAttribute ProblemChoice problemChoice){
        String result = "";
        String token = request.getParameter("token");
        Integer userId = (Integer)redisService.get(token);
        if(userId != null){
            problemChoice.setUserId(userId);
            problemChoice = HandlerObject.handlerChoice(problemChoice);
            System.out.println(problemChoice);
            problemChoiceService.insertProblemChoice(problemChoice);
            //更新题库中的总量
            Integer num = (Integer)redisService.get(problemChoice.getType());
            redisService.set(problemChoice.getType() , num+1);
            result = WxResult.getSuccessResult("成功插入");
        }else{
            result = WxResult.getException("登录失效");
        }
        return result;
    }

    /**
     * 进行查询题型
     * @param request
     * @return
     */
    @RequestMapping("/doChoiceDE")
    @ResponseBody
    public String doChoiceDE(HttpServletRequest request){
        //获取请求中的token和查询的题型
        String type = request.getParameter("type");
        String token = request.getParameter("token");
        WxResult wxResult = null;
        Integer beginNum = 0;
        boolean isFlag = true;
        try {
            //从token中获取redis中题型数量
            beginNum = PageHandler.getBeginNum(token, type, redisService);
            //分页查询题
            List<ProblemChoice> problemChoices = problemChoiceService.selectProblemChoiceSheetByType(type, beginNum, pageNum);
            //将结果封装成你想要的结果
            Object data = HandlerObject.handerChoiceProblem(problemChoices);
            wxResult = new WxResult("200",data);
        }catch (Exception e){
            wxResult = new WxResult("500","无法查寻到该数据");
            isFlag = false;
        }finally {
            String result = JSON.toJSON(wxResult).toString();
            if(isFlag){
                //从token中获取redis中题型数量
                PageHandler.addBeginNum(token,type,redisService,beginNum,pageNum,peSituationService);
            }
            return result;
        }
    }

    /**
     * 通过网页进行手动录入页面，设计到回调问题
     * @param request
     * @param problemChoice
     * @return
     */
    @RequestMapping("webDoChoice")
    @ResponseBody
    public String webWritePe(HttpServletRequest request,@ModelAttribute ProblemChoice problemChoice){
        String callback = request.getParameter("callback");
        String result = "";
        try{
            int i = problemChoiceService.insertProblemChoice(problemChoice);
            result = WxResult.getSuccessResult("success");
        }catch (Exception e){
            result = WxResult.getException("fail");
        }finally {
            result = callback+"("+result+")";
            return result;
        }
    }


    /**
     * 改变用户状态
     * @param request
     * @return
     */
    @RequestMapping("updateProblemChoiceFalse")
    @ResponseBody
    public String updateProblemChoiceFalse(HttpServletRequest request){
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        String isFlag = request.getParameter("isFlag");
        String result = "";
        try {
            Integer userId = (Integer)redisService.get(token);
            ProblemChoice problemChoice = new ProblemChoice();
            problemChoice.setUserId(userId);
            problemChoice.setId(Integer.parseInt(id));
            problemChoice.setIsFlag(Integer.parseInt(isFlag));
            problemChoiceService.updateProblemChoiceIsFlag(problemChoice);
            result = WxResult.getSuccessResult("删除成功");
        }catch (Exception e){
            result = WxResult.getException("删除失败");
        }finally {
            return result;
        }
    }


    /**
     * 改变用户状态
     * @param request
     * @return
     */
    @RequestMapping("deleteProblemChoiceUserId")
    @ResponseBody
    public String deleteProblemChoiceUserId(HttpServletRequest request){
        String idStr = request.getParameter("id");
        String result = "";
        try {
            problemChoiceService.updateProblemChoiceUserId(Integer.parseInt(idStr));
            result = WxResult.getSuccessResult("删除成功");
        }catch (Exception e){
            System.out.println(e);
            result = WxResult.getException("删除失败");
        }finally {
            return result;
        }
    }


    /**
     * 改变用户题的数据，题不完善，需要审核员手动更改
     * @param problemChoice
     * @return
     */
    @RequestMapping("updapteProblemChoiceById")
    @ResponseBody
    public String updapteProblemChoiceById(@ModelAttribute ProblemChoice problemChoice){
        String result = "";
        try {
            problemChoiceService.updapteProblemChoiceById(problemChoice);
            result = WxResult.getSuccessResult("更改成功");
        }catch (Exception e){
            result = WxResult.getException("更改失败");
        }finally {
            return result;
        }
    }


    /**
     * 根据题id删除，真删除
     * @param request
     * @return
     */
    @RequestMapping("deleteProblemChoiceById")
    @ResponseBody
    public String deleteProblemChoiceById(HttpServletRequest request){
        String result = "";
        String idStr = request.getParameter("id");
        try {
            problemChoiceService.deleteProblemChoiceById(Integer.parseInt(idStr));
            result = WxResult.getSuccessResult("删除成功");
        }catch (Exception e){
            result = WxResult.getException("删除失败");
        }finally {
            return result;
        }
    }


    /**
     * 根据题id查询题
     * @param request
     * @return
     */
    @RequestMapping("selectProblemChoiceById")
    @ResponseBody
    public String selectProblemChoiceById(HttpServletRequest request){
        String result = "";
        String idStr = request.getParameter("id");
        try {
            ProblemChoice problemChoice = problemChoiceService.selectProblemChoiceById(Integer.parseInt(idStr));
            result = WxResult.getSuccessResult(problemChoice);
        }catch (Exception e){
            result = WxResult.getException("查询失败");
        }finally {
            return result;
        }
    }

    /**
     * 获取所有待审批的数据
     * @param request
     * @returnd String
     */
    @RequestMapping("approvalSelect")
    @ResponseBody
    public String approvalSelectProblemChoice(HttpServletRequest request){
        String result = "";
        String begin = request.getParameter("begin");
        try {
            List<ProblemChoice> problemChoices = problemChoiceService.selectProblemChoiceByIsFlag(1, Integer.parseInt(begin), pageNum);
            Object data = HandlerObject.handerChoiceProblem(problemChoices);
            result = WxResult.getSuccessResult(data);
        }catch (Exception e){
            result = WxResult.getException(e);
        }finally {
            return result;
        }
    }


    /**
     * 审批该题，通过或者退回
     * @return
     */
    @RequestMapping("approval")
    @ResponseBody
    public String approvalProblemChoiceById(HttpServletRequest request){
        String result = "";
        String id = request.getParameter("id");
        String isFlag = request.getParameter("isFlag");
        try {
            problemChoiceService.updateProblemChoiceIsFlag(new ProblemChoice(Integer.parseInt(id),Integer.parseInt(isFlag)));
            result = WxResult.getSuccessResult("操作成功");
        }catch (Exception e){
            result = WxResult.getException(e);
        }finally {
            return result;
        }
    }


}
