package com.member.filter;

import com.util.AccessPassResources;
import com.constant.CrowFundingConstant;
import com.member.entities.vo.MemberLoginVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


/**
 * @author qiu
 * @create 2022-04-12 15:29
 */
@Component
public class MyZuulFilter extends ZuulFilter {
    /**
     *
     * @return 返回“pre”意思是在目标微服务前执行过滤
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 登录检查
     * @return 返回true表示需要做登录检查
     */
    @Override
    public boolean shouldFilter() {
        // 1.获取RequestContext对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 2.获取ServletPath
        String servletPath = requestContext.getRequest().getServletPath();

        // 3.判断是否放行
        if(AccessPassResources.PASS_RESOURCES.contains(servletPath) || AccessPassResources.isStaticResources(servletPath)){
            return false;
        }

        return true;
    }

    /**
     * 获取session中的对象
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 1.获取RequestContext对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 2.获取Session
        HttpSession session = requestContext.getRequest().getSession();

        MemberLoginVO member =(MemberLoginVO) session.getAttribute(CrowFundingConstant.MEMBER_LOGIN_NAME);
        // 3.登录对象为空则未登录，返回到登录界面
        if(Objects.isNull(member)){
            session.setAttribute("message",CrowFundingConstant.MESSAGE_ACCESS_FORBIDEN);

            // 4.重定向至登录界面
            HttpServletResponse response = requestContext.getResponse();
            try {
                response.sendRedirect("/member/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
