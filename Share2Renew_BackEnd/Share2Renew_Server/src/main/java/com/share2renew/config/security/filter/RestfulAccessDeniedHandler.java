package com.share2renew.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share2renew.pojo.GeneralBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: Share2Renew_BackEnd
 * @description: Blocking when accessing interfaces without permissions(无权限时拦截)
 * @author: Junxian Cai
 **/

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter output  = httpServletResponse.getWriter();
        GeneralBean generalBean = GeneralBean.error("Sorry, insufficient authority.");
        generalBean.setCode(403);
        output.write(new ObjectMapper().writeValueAsString(generalBean));
        output.flush();
        output.close();
    }
}
