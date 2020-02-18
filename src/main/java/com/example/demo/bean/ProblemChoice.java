package com.example.demo.bean;

import lombok.Data;

/**
 * 微信内部数据库对应的数据
 */
@Data
public class ProblemChoice {

    public ProblemChoice(){

    }

    public ProblemChoice(Integer id,Integer isFlag){
        this.id = id;
        this.isFlag = isFlag;
    }

    private Integer id;

    private String problemName ;

    private String problemChoiceA;

    private String problemChoiceB;

    private String problemChoiceC;

    private String problemChoiceD;

    private String answer;

    private String type;

    private Integer userId;

    private Integer isFlag;

    private Integer isCollection;
}
