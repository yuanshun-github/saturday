package com.example.demo.common.tool;

public class StringCode {

    //第一级，二级不需要映射，第3级需要映射到响应的数据
    //搜索词年级的三级匹配
    public static String gradeFirst = "一年级,二年级,三年级,四年级,五年级,六年级,1年级,2年级,3年级,4年级,5年级,6年级,初一,初1,初二,初2,初三,初3,高一,高1,高二,高2,高三,高3,高四,高4";
    public static String gradeSecond = "小学,初中,高中,年级";
    public static String gradeThird = "小,初,高,中";//未实现
    //搜索词学科的三级匹配
    public static String subjectFirst = "数学,英语,语文,化学,物理,生物";
    public static String subjectSecond = "数,英,语,语言,化,理,英文,数字";
    public static String subjectThird = "中数,电";//未实现

    //搜索词的级别
    public static Integer searchLeval = 2;



}
