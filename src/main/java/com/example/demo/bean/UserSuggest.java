package com.example.demo.bean;

import lombok.Data;

import java.util.Date;

@Data
public class UserSuggest {

    private Integer id;

    private Integer userId;

    private String suggest;

    private Integer isFlag;

    private Date writeDate;
}
