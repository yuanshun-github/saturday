package com.example.demo.common.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 处理字符串
 */
public class HandlerString {

    private static Logger logger = LoggerFactory.getLogger(HandlerString.class);

    private static String gradeFirst = "一年级,二年级,三年级,四年级,五年级,六年级,1年级,2年级,3年级,4年级,5年级,6年级,初一,初1,初二,初2,初三,初3,高一,高1,高二,高2,高三,高3,高四,高4";
    private static String gradeSecond = "小学,初中,高中,年级";
    private static String gradeThird = "小,初,高,中";


    /**
     * 关键字进行解析，解析出对应的数据
     * @param keyword
     * @param type grade,subject,type
     * @param level
     * @return
     */
    public static String keywordHandler(String keyword,String type,Integer level){
        //关键字去掉空格
        keyword = keyword.replaceAll(" ","");
        //要返回的值
        String keywordResult = "";
        String levelStr = getThesaurusByTypeLevel(type,level);

        //处理本级别的
        String[] levelArr = levelStr.split(",");
        for (int i=0;i<levelArr.length;i++){
            if(keyword.indexOf(levelArr[i])>=0)
                keywordResult += levelArr[i]+"|";
        }
        //这一级没有数据就需要开启下一级匹配
        if(keywordResult.length()==0){
            //判断是不是最后一级
            if(level< StringCode.searchLeval){
                level ++;
                keywordResult = keywordHandler(keyword, type, level);
            }else{
                logger.info("搜索词汇("+keyword+")在最后一级也没有匹配到");
            }
        }else{
            //第一级查询有数据时
            keywordResult = keywordResult.substring(0, keywordResult.length() - 1);
            logger.info("第"+level+"级匹配得到结果："+keywordResult);
        }
        return keywordResult;
    }


    /**
     * 通过级别和类型获取---对应的关键字词库，用于匹配
     * @param type
     * @param level
     * @return
     */
    public static String getThesaurusByTypeLevel(String type,Integer level){
        String levelStr = "";
        //判断级别
        if(level==1){
            switch (type){
                case "grade":
                    levelStr = StringCode.gradeFirst;
                    break;
                case "subject":
                    levelStr = StringCode.subjectFirst;
                    break;
                default:
                    break;
            }
        }else if(level==2){
            switch (type){
                case "grade":
                    levelStr = StringCode.gradeSecond;
                    break;
                case "subjec":
                    levelStr = StringCode.subjectSecond;
                    break;
                default:
                    break;
            }
        }else{
            switch (type){
                case "grade":
                    levelStr = StringCode.gradeThird;
                    break;
                case "subjec":
                    levelStr = StringCode.subjectThird;
                    break;
                default:
                    break;
            }
        }
        return levelStr;
    }




    /**
     *
     * 处理json类型的字符串
     * 将json类型字符串，处理为map类型
     * 当想自定义解析json字符串时，可以使用该方法
     */
    public  static  HashMap<String, String> jsonToMap(String jsonString){
        HashMap<String, String> jsonMap = new HashMap<>();
        String substring = jsonString.substring(1, jsonString.length()-1);
        String[] split = substring.split(",");
        if (split.length>1){
            for(int i=0;i<split.length;i++){
                String[] kv = split[i].split(":");
                String key = kv[0].substring(1, kv[0].length() - 1);
                String value = kv[1].substring(1, kv[1].length() - 1);
                jsonMap.put(key,value);
            }
        }
        return jsonMap;
    }

    /**
     * 处理选择题，将统一成统一格式的
     *         A：开头的
     * @param choice
     * @param type
     * @return
     */
    public static String handlerChoice(String choice,String type){
        if(choice != null){
            int i = choice.indexOf(type);
            if(i == 0){
                int i1 = choice.indexOf(type + "：");
                //如果是A：就不用修改,不等于就需要再确认
                if(i1 != 0){
                    int i2 = choice.indexOf(type + ":");
                    if(i2 !=0){
                        //这就表示是A开头
                        choice = type+"："+choice.substring(1,choice.length());
                    }else{
                        choice = type+"："+choice.substring(2,choice.length());
                    }
                }
            }else{
                choice = type+"："+choice;
            }
        }
        return choice;
    }


    /**
     * 将两个字符串进行分割，交叉插入
     * @param str1
     * @param str2
     * @param splitCode 切割符号，切割字符串未数组
     * @param linkCode 交叉插入时，两个字符串中间的连接符
     * @return
     */
    public static String handlerTwoStrToOne(String str1,String str2,String splitCode,String linkCode){
        String result = "";
        //字符1有值的时候处理
        if (!str1.isEmpty()){
            String[] firstArr = str1.split("["+splitCode+"]");
            //字符2是否有值
            if (!str2.isEmpty()){
                String[] secondeArr = str2.split("["+splitCode+"]");

                for (int i=0;i<firstArr.length;i++){
                    for (int j=0;j<secondeArr.length;j++){
                        System.out.println(secondeArr[j]);
                        result = result + splitCode + firstArr[i]+linkCode+secondeArr[j];
                    }
                }
                result = result.substring(1,result.length());
            }
            //字符1有值，字符2没值，直接返回字符2
            else{
                result = str1;
            }

        }
        //字符1无值的时候
        else{
            //字符2有值
            if(!str2.isEmpty()){
                result = str2;
            }
        }
        logger.info("获取到解析之后搜索字段："+result);
        return result;
    }




}
