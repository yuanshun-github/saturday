package com.example.demo.common.tool;

import com.example.demo.bean.ProblemChoice;
import com.example.demo.bean.ProblemChoiceObj;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理对象，将用户转换为新用户或者新的字符串
 */
public class HandlerObject {

    public static Object handerChoiceProblem(List<ProblemChoice> problemChoices){
        List<ProblemChoiceObj> problemChoiceObjs = new ArrayList<>();
        //遍历取出来进行封装
        for(ProblemChoice problemChoice:problemChoices){
            String s ;
            ProblemChoiceObj problemChoiceObj = new ProblemChoiceObj(false);
            //封装问题和答案和id
            problemChoiceObj.setId(problemChoice.getId());
            problemChoiceObj.setProblem(problemChoice.getProblemName());
            problemChoiceObj.setAnswer(problemChoice.getAnswer());
            problemChoiceObj.setOpen(false);
            //封装选择题
            ArrayList<String> choice = new ArrayList<>();
            s= (problemChoice.getProblemChoiceA() == null)? "" : HandlerString.handlerChoice(problemChoice.getProblemChoiceA(),"A");
            choice.add(s);
            s= (problemChoice.getProblemChoiceB() == null)? "" : HandlerString.handlerChoice(problemChoice.getProblemChoiceB(),"B");
            choice.add(s);
            s= (problemChoice.getProblemChoiceC() == null)? "" : HandlerString.handlerChoice(problemChoice.getProblemChoiceC(),"C");
            choice.add(s);
            s= (problemChoice.getProblemChoiceD() == null)? "" : HandlerString.handlerChoice(problemChoice.getProblemChoiceD(),"D");
            choice.add(s);
            problemChoiceObj.setChoice(choice);
            //判断是否收藏该题
            if(problemChoice.getIsCollection() != null){
                problemChoiceObj.setCollection(true);
            }
            problemChoiceObjs.add(problemChoiceObj);
        }
        return problemChoiceObjs;
    }


    /**
     * 将选择题选项进行处理
     * @param problemChoice
     * @return
     */
    public static ProblemChoice handlerChoice(ProblemChoice problemChoice){
        //处理选择题选项
        problemChoice.setProblemChoiceA(HandlerString.handlerChoice(problemChoice.getProblemChoiceA(),"A"));
        problemChoice.setProblemChoiceB(HandlerString.handlerChoice(problemChoice.getProblemChoiceB(),"B"));
        problemChoice.setProblemChoiceC(HandlerString.handlerChoice(problemChoice.getProblemChoiceC(),"C"));
        problemChoice.setProblemChoiceD(HandlerString.handlerChoice(problemChoice.getProblemChoiceD(),"D"));
        return problemChoice;
    }
}
