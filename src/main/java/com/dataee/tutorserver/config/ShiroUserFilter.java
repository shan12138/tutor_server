package com.dataee.tutorserver.config;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决shiro框架在跨域时遇到的options问题
 * 重写UserFilter
 *
 * @author JinYue
 * @CreateDate 2019/5/22 9:04
 */
public class ShiroUserFilter extends UserFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletResponse response1 = (HttpServletResponse) response;
        HttpServletRequest request1 = (HttpServletRequest) request;
        //允许所有的options通过
        if (request1.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        } else {
            return super.isAccessAllowed(request, response, mappedValue);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequest(request);
        //返回未登录的json
        WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "请重新登录！");
        return false;
        //return super.onAccessDenied(request, response);
    }
}
