package com.util;


import javax.servlet.http.HttpServletRequest;

/**
 * @author qiu
 * @create 2021-12-25 10:56
 */
public class AdminUtil {

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
}
