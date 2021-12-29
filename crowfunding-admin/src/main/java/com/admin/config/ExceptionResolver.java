package com.admin.config;

import com.admin.service.AdminService;
import com.exception.AccessForbiddenException;
import com.exception.LoginFailedException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.ResultEntity;
import com.util.CrowFundingUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.nio.channels.ReadPendingException;

/**
 * @author qiu
 * @create 2021-12-24 22:04
 *
 *  基于注解的异常处理类
 */
@ControllerAdvice //表示当前类是一个处理异常的类
public class ExceptionResolver{

    /**
     * 未登录访问主页异常
     * @param accessForbiddenException
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resloveAccessForbiddenException(AccessForbiddenException accessForbiddenException,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) throws IOException {

        String  viewName = "admin-login";

        return commonExceptionResolver(accessForbiddenException,request,response,viewName);
    }

    /**
     * 管理员登录异常
     * @param loginFailedException
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resloveLoginException(LoginFailedException loginFailedException,
                                              HttpServletRequest request,
                                              HttpServletResponse response)throws IOException{

        String viewName = "admin-login";

        return commonExceptionResolver(loginFailedException,request, response,viewName);
    }

    /**
     * 异常处理的核心方法，具体异常处理调用该方法即可
     * @param exception
     * @param request
     * @param response
     * @param viewName 视图名称
     * @return
     * @throws IOException
     */
    private ModelAndView commonExceptionResolver(Exception exception,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String viewName) throws IOException {
        // 1.判断是当前请求是普通请求还是Ajax请求（普通请求需要返回页面，Ajax请求只需要返回jason）
        boolean judgeAjaxRequest = CrowFundingUtil.judgeAjaxRequest(request);
        // 2.如果是Ajax请求
        if(judgeAjaxRequest){
            // 3.获取错误信息
            String message = exception.getMessage();
            // 4.创建ResultEntity对象，统一Ajax请求的返回类型
            ResultEntity<Object> resultEntity = ResultEntity.fail(message);
            // 5.创建gson对象，将resultEntity转换为json字符串
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            // 6.返回json
            PrintWriter writer = response.getWriter();
            writer.write(json);
            // 7.返回null，不返回ModelAndView对象
            return null;
        }

        // 如果是普通请求
        // 8.创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 9.将Exception存入对象
        modelAndView.addObject("Exception",exception);
        // 10.设置目标视图名称
        modelAndView.setViewName(viewName);
        // 11.返回modelAndView对象
        return modelAndView;
    }
}
