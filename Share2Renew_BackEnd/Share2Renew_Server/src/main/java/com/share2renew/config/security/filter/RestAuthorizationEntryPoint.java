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
