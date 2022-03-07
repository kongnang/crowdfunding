package com.admin.interceptor;

import com.admin.entity.Admin;
import com.constant.CrowFundingConstant;
import com.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author qiu
 * @create 2021-12-29 19:36
 */
@Deprecated
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取Session域中的对象
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(CrowFundingConstant.ADAMIN_LOGIN_NAME);

        // 若session域中取得的对象为null，抛出登录异常
        if(admin == null){
            throw new AccessForbiddenException(CrowFundingConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        return true;
    }

}
