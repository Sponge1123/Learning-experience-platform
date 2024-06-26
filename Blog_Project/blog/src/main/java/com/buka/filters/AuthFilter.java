package com.buka.filters;

import com.alibaba.fastjson.JSON;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.config.LoginUserDetails;
import com.buka.util.JwtUtil;
import com.buka.util.RedisUtil;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (token == null || "".equals(token) || JwtUtil.isTokenExpired(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //从Token中获取ID
        String subjectFromToken = JwtUtil.getSubjectFromToken(token);
        //根据获取的ID从Redis中取出user信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) redisUtil.get(subjectFromToken);
        if (loginUserDetails == null){
            httpServletResponse.setContentType("text/html");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ResponseResult.getResponseResult(AppHttpCodeEnum.NEED_LOGIN)));
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetails,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
