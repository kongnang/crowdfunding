package com.member;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import com.util.HttpUtils;

/**
 * @author qiu
 * @create 2022-03-08 19:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestShortMessage {
    @Test
    public void test(){
        // 短信接口调用的url地址
        String host = "https://dfsns.market.alicloudapi.com";
        // 具体发送短信功能的地址
        String path = "/data/send_sms";
        // 请求方式
        String method = "POST";
        // 你自己的AppCode
        String appcode = "15236a288675494f88cdfb849557aed8";

        // 封装其它参数
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:12345");
        bodys.put("phone_number", "18760060751");
        bodys.put("template_id", "TPL_0000");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            StatusLine statusLine = response.getStatusLine();
            // 获取状态码
            int statusCode = statusLine.getStatusCode();
            // 获取错误信息
            String reasonPhrase = statusLine.getReasonPhrase();

//            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
