package com.buka.security;

import com.alibaba.fastjson.JSON;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.util.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(ResponseResult.getResponseResult(AppHttpCodeEnum.NEED_LOGIN)));
    }
}
