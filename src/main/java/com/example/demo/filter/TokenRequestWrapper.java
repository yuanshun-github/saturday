package com.example.demo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


/**
 * 过滤器
 *
 * 过滤器最先执行>>>>>然后时拦截器>>>>>>>然后时AOP
 */
public class TokenRequestWrapper extends HttpServletRequestWrapper {


    public TokenRequestWrapper(HttpServletRequest  request){
        super(request);
    }

    /**
     * 此方法用于请求时pojo类时
     *      通过此方法进行参数遍历，对pojo类赋值等
     * @return
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> enumeration = super.getParameterNames();
        ArrayList<String> list = Collections.list(enumeration);
        //当有token字段时动态的添加uid字段
        if (list.contains("token")){
            list.add("uid");
            return Collections.enumeration(list);
        }else {
            return super.getParameterNames();
        }
    }


    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return super.getParameterValues(name);
    }


}
