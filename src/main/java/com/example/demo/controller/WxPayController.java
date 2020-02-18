package com.example.demo.controller;

import com.example.demo.bean.OrderReturnInfo;
import com.example.demo.common.net.HttpRequest;
import com.example.demo.common.pay.WXPayConstants;
import com.example.demo.common.pay.WXPayUtil;
import com.example.demo.common.tool.RandomStringGenerator;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxPayController {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.payUrl}")
    private String payUrl;

    @Value("${wx.mch_id}")
    private String mch_id;

    @Value("${wx.notify_url}")
    private String notify_url;

    @Value("${wx.key}")
    private String key;



    @RequestMapping("/payment")
    public void payment(HttpServletRequest request, HttpServletResponse response){
        //获取request的参数，查看买的何种商品
        String openid = "";

        //封装支付的参数数据
        HashMap<String, String> data = new HashMap<>();
        data.put("appid",appid);
        data.put("mch_id",mch_id);
        data.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));//随机字符串
        //data.put("sign",);//签名代表什么意思
        data.put("body", "");//商品描述
        data.put("out_trade_no",RandomStringGenerator.getRandomStringByLength(18));//订单号
        data.put("fee_type","CNY");//货币类型
        data.put("total_fee","100");//货币总金额
        data.put("spbill_create_ip","192.11.0.11");//终端ip，调用微信支付的终端ip，即手机ip
        data.put("notify_url",notify_url);
        data.put("trade_type","JSAPI");
        //生成签名
        String signXml= "";
        try {
            signXml = WXPayUtil.generateSignature(data, key, WXPayConstants.SignType.MD5);
            //发送订单请求，获取用户信息
            String result = HttpRequest.sendPost(payUrl, signXml);
            XStream xStream = new XStream();
            XStream.setupDefaultSecurity(xStream);
            xStream.allowTypes(new Class[]{OrderReturnInfo.class});
            xStream.alias("xml",OrderReturnInfo.class);
            //将返回的内容封装到bean类
            OrderReturnInfo orderReturnInfo = (OrderReturnInfo) xStream.fromXML(result);
            if("SUCCESS".equals(orderReturnInfo.getReturn_code())&&orderReturnInfo.getReturn_code().equals(orderReturnInfo.getResult_code())){
                long time = System.currentTimeMillis()/1000;
                //生成签名（官方给出来的签名方法）
                Map<String,String> map2 = new HashMap<String,String>();
                map2.put("appid",appid);
                map2.put("timeStamp",String.valueOf(time));
                map2.put("nonceStr",orderReturnInfo.getNonce_str());
                map2.put("package","prepay_id="+orderReturnInfo.getPrepay_id());
                map2.put("signType","MD5");

            }

        }catch (Exception e){

        }finally {

        }



    }
}
