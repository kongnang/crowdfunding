package com.util;

import com.constant.CrowFundingConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author qiu
 * @create 2022-04-12 11:16
 */
public class AccessPassResources {

    public static final Set<String> PASS_RESOURCES = new HashSet<>();

    public static final Set<String> STATIC_RESOURCES = new HashSet<>();

    static {
        PASS_RESOURCES.add("/member/auth");
        PASS_RESOURCES.add("/member/auth/login");
        PASS_RESOURCES.add("/member/auth/register");
        PASS_RESOURCES.add("/member/auth/do/login");
        PASS_RESOURCES.add("/member/auth/logout");
        PASS_RESOURCES.add("/member/auth/send/message.json");
    }

    static{
        STATIC_RESOURCES.add("bootstrap");
        STATIC_RESOURCES.add("css");
        STATIC_RESOURCES.add("fonts");
        STATIC_RESOURCES.add("img");
        STATIC_RESOURCES.add("jquery");
        STATIC_RESOURCES.add("layer");
        STATIC_RESOURCES.add("script");
        STATIC_RESOURCES.add("ztree");
    }


    /* 判断是否请求静态资源*/
    public static boolean isStaticResources(String servletPath){

        if(servletPath == null || servletPath.length() == 0){
            throw new RuntimeException(CrowFundingConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 拆分字符串
        String[] split = servletPath.split("/");

        // /member/auth/css/carousel.css
        // ["","member","auth,"css","carousel",".","css"]
        String s = split[3];

        return STATIC_RESOURCES.contains(s);
    }

}
