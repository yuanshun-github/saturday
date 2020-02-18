package com.example.demo.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择题封装类，用于响应小程序
 */
@Data
public class ProblemChoiceObj {

    public ProblemChoiceObj(){

    }

    public ProblemChoiceObj(Boolean collection){
        this.collection = collection;
    }

    private Integer id;

    private String problem;

    private boolean open;

    private ArrayList<String> choice;

    private String answer;

    private boolean collection;


}
