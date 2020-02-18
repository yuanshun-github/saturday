package com.example.demo.common.net;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 自定义响应数据结构
 *              这个类是提供给门户，ios，安卓，微信商城用的
 *              门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 *              其他自行处理
 *              200：表示成功
 *              500：表示错误，错误信息在msg字段中
 *              501：bean验证错误，不管多少个错误都以map形式返回
 *              502：拦截器拦截到用户token出错
 *              555：异常抛出信息
 */
public class GlobalResult {

    //响应网络状态
    @Getter
    @Setter
    private Integer status;

    //响应消息
    @Getter
    @Setter
    private String msg;

    //响应中的数据
    @Getter
    @Setter
    private  Object data;

    public GlobalResult(){

    }

    public GlobalResult(Integer status,String msg,Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResult(Object data){
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    /**
     *
     * @param status
     * @param msg
     * @param data
     * @return
     * 返回相应状态嘛，状态值，数据
     */
    public static GlobalResult build(Integer status,String msg,Object data){
        return new GlobalResult(status,msg,data);
    }

    /**
     *
     * @param data
     * @return
     * 只返回值
     */
    public  static  GlobalResult getData(Object data){
        return new GlobalResult(data);
    }

    /**
     *
     * @return
     * 什么都没有返回
     */
    public static GlobalResult getData(){
        return new GlobalResult(null);
    }

    public static GlobalResult errorMsg(String msg){
        return new GlobalResult(500,msg,null);
    }


    public  static GlobalResult errorMsg(Object data){
        return new GlobalResult(500,"error",data);
    }

    public static GlobalResult errorTokenMsg(String msg) {
        return new GlobalResult(502, msg, null);
    }

    public static GlobalResult errorException(String msg) {
        return new GlobalResult(555, msg, null);
    }

    public Boolean isSuccess(){
        return this.status == 200;
    }



}
