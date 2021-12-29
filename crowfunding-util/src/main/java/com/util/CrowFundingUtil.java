package com.util;


import com.constant.CrowFundingConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qiu
 * @create 2021-12-25 10:56
 */
public class CrowFundingUtil {

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
            throw new RuntimeException(CrowFundingConstant.MESSAGE_STRING_INVALIDATE);
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
