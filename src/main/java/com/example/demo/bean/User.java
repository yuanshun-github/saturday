package com.example.demo.bean;

import lombok.Data;

@Data
public class User {

    public User(){

    }

    public User(String userCode){
        this.userCode=userCode;
    }

    public User(String userCode,Integer roleId){
        this.userCode=userCode;
        this.roleId=roleId;
    }

    public User(Integer id,String userName){
        this.id=id;
        this.userName=userName;
    }

    public User(Integer id,String userName,String userCode){
        this.id = id;
        this.userName = userName;
        this.userCode = userCode;
    }

    public User(Integer userId,String userName,String province,String city,Integer gender){
        this.id = userId;
        this.userName = userName;
        this.province = province;
        this.city = city;
        this.gender = gender;
    }

    private Integer id;

    private String userName;

    private String userCode;

    private Integer roleId;

    //省份
    private String province;
    //城市
    private String city;
    //性别
    private Integer gender;

}
