package com.util;


import com.aliyun.oss.*;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.constant.CrowFundingConstant;
import com.exception.LoginFailedException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qiu
 * @create 2021-12-25 10:56
 */
public class CrowFundingUtil {


    /**
     * 将文件上传到OSS服务器
     * @param endPoint
     * @param bucketName
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketDomain
     * @param inputStream
     * @param originalFileName 原始文件名 如 example.txt
     * @return
     */
    public static ResultEntity<String> uploadFileToOSS(String endPoint,
                                                       String bucketName,
                                                       String accessKeyId,
                                                       String accessKeySecret,
                                                       String bucketDomain,
                                                       InputStream inputStream,
                                                       String originalFileName){

        // 文件目录 20220412
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 文件后缀名
        String extentionName = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 文件主体名，使用UUID随机生成
        String fileMainName = UUID.randomUUID().toString().replace("-","");
        // 填写Object完整路径，例如20220412/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = folderName + "/" + fileMainName + extentionName;


        // 创建OSSClient实例
        OSS oss = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            // 上传文件并获得响应结果
            PutObjectResult result = oss.putObject(bucketName, objectName, inputStream);
            // 从响应体中获取响应信息
            ResponseMessage response = result.getResponse();
            // 根据响应体内容判断是否上传成功
            if(Objects.isNull(response)){
                String uploadUrl = bucketDomain + "/" + objectName;
                return ResultEntity.successWithData(uploadUrl);
            }else{
                int statusCode = response.getStatusCode();
                String errorMessage = response.getErrorResponseAsString();
                return ResultEntity.fail("状态码："+statusCode+"  "+"错误信息："+errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }finally {
            // 关闭ossclient
            oss.shutdown();
        }
    }


    /**
     *
     * @param host 短信接口调用的url地址
     * @param path 具体发送短信功能的地址
     * @param method 请求方式
     * @param appcode 你自己的AppCode
     * @param phone_number 手机号
     * @param template_id 短信模板id
     * @return 成功：返回验证码；失败：返回错误信息
     */
    public static ResultEntity<String> sendShortMessage(String host,
                                                        String path,
                                                        String method,
                                                        String appcode,
                                                        String phone_number,
                                                        String template_id){
//        // 生成验证码
//        StringBuilder stringBuilder = new StringBuilder();
//        for(int i=0;i<4;i++){
//            int random =(int)(Math.random()*10);
//            stringBuilder.append(random);
//        }
//        String code = stringBuilder.toString();
//
//        // 封装其它参数
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        Map<String, String> querys = new HashMap<String, String>();
//        Map<String, String> bodys = new HashMap<String, String>();
//        bodys.put("content", "code:"+code);
//        bodys.put("phone_number", phone_number);
//        bodys.put("template_id", template_id);
//
//        try {
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            StatusLine statusLine = response.getStatusLine();
//            // 获取状态码
//            int statusCode = statusLine.getStatusCode();
//            // 获取错误信息
//            String reasonPhrase = statusLine.getReasonPhrase();
//
//            if(statusCode == 200){
//                return ResultEntity.successWithData(code);
//            }
//            return ResultEntity.fail(reasonPhrase);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultEntity.fail(e.getMessage());
//        }

        // 短信调用次数不足了，暂且模拟发送短信
        String code = "1234";
        return ResultEntity.successWithData(code);
    }

    /**
     * 判断请求是否为Ajax请求
     * @param request
     * @return 如果请求头信息中的Accept:application/json或X-Request-With:XMLHttpRequest返回true
     */
    public static boolean judgeAjaxRequest(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String xRequestWith = request.getHeader("X-Request-With");

        return((accept != null && accept == "application/json")
                ||
                (xRequestWith != null && xRequestWith == "XMLHttpRequest"));

    }

    /**
     * md5加密
     * @param source
     * @return
     */
    public static String encrypt(String source){
        // 1.判断传入的字符串是否有效
        if(source == null || source.length() == 0){
            throw new LoginFailedException(CrowFundingConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            String algorithm = "md5";
            // 2.获取 MessageDigest 对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 3.获得明文字符串对应的字节数组
            byte[] bytes = source.getBytes();
            // 4.使用字节数组进行加密
            byte[] digest = messageDigest.digest(bytes);
            // 5.字节数组转换为十六进制再转化成字符串
            BigInteger bigInteger = new BigInteger(1, digest);
            String cryptograph = bigInteger.toString(16).toUpperCase();

            return cryptograph;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
