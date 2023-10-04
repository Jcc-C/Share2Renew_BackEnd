package com.share2renew.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share2renew.pojo.GeneralBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: Share2Renew_BackEnd
 * @description: Custom interceptor when user is not logged in or token is not valid(未登陆时拦截)
 * @author: Junxian Cai
 **/

/**
 * //todo
 * 在Spring Security中，AuthenticationEntryPoint是一个接口，用于定义当用户尝试访问一个需要身份验证的资源但当前未经身份验证时如何开始身份验证过程的策略。
 *
 * 简单地说，当未经认证的用户尝试访问受保护的资源时，AuthenticationEntryPoint决定了应该如何响应。这个响应可能是重定向到登录页面、返回一个特定的错误响应，或者执行其他任何必要的操作。
 *
 * 这在无状态应用程序，如RESTful web服务中，尤为有用。对于这样的服务，与重定向用户到登录页面不同，您可能想返回一个HTTP 401 Unauthorized状态码。
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        // Get the output stream
        PrintWriter output = httpServletResponse.getWriter();
        GeneralBean generalBean = GeneralBean.error("Please Login First.");
        generalBean.setCode(401);
        output.write(new ObjectMapper().writeValueAsString(generalBean));
        output.flush();
        output.close();
    }
}
