package com.buka.filter;

import com.buka.config.LoginUserDetails;
import com.buka.util.JwtUtil;
import com.buka.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token，指定你要获取的请求头叫什么
        String token = request.getHeader("token");
        //判空，不一定所有的请求都有请求头，所以上面那行的xxtoken可能为空
        //!StringUtils.hasText()方法用于检查给定的字符串是否为空或仅包含空格字符
        if (!StringUtils.hasText(token)) {
            //如果请求没有携带token，那么就不需要解析token，不需要获取用户信息，直接放行就可以
            filterChain.doFilter(request, response);
            //return之后，就不会走下面那些代码
            return;
        }
        if(JwtUtil.isTokenExpired(token)){
            filterChain.doFilter(request, response);
            //return之后，就不会走下面那些代码
            return;
        }
        //解析token
        String userid; //把userid定义在外面，才能同时用于下面的46行和52行
        try {
            Claims claims = JwtUtil.getClaimsFromToken(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("用户未登录");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        //LoginUser是我们在domain目录写的实体类
        LoginUserDetails loginUser = (LoginUserDetails) redisUtil.get(redisKey);
        //判断获取到的用户信息是否为空，因为redis里面可能并不存在这个用户信息，例如缓存过期了
        if(Objects.isNull(loginUser)){
            //抛出一个异常
            throw new RuntimeException("请重新登陆");
        }

        //把最终的LoginUser用户信息，通过setAuthentication方法，存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                //第一个参数是LoginUser用户信息，第二个参数是凭证(null)，第三个参数是权限信息(null)
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //全部做完之后，就放行
        filterChain.doFilter(request, response);
    }
}