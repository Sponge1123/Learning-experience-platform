package com.buka.service.impl;

import com.buka.config.LoginUserDetails;
import com.buka.pojo.User;
import com.buka.service.LoginService;
import com.buka.util.JwtUtil;
import com.buka.util.RedisUtil;
import com.buka.util.ResponseResult;
import com.buka.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    //先在SecurityConfig，使用@Bean注解重写官方的authenticationManagerBean类，然后这里才能注入成功
    private AuthenticationManager authenticationManager;
    @Autowired
    //RedisCache是我们在utils目录写好的类
    private RedisUtil redisUtil;
    @Override
    //ResponseResult和user是我们在domain目录写好的类
    public ResponseResult login(User user) {

        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断上面那行的authenticate是否为null，如果是则认证没通过，就抛出异常
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，就使用userid生成一个jwt，然后把jwt存入ResponseResult后返回
        LoginUserDetails loginUser = (LoginUserDetails) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.getToken(userid);
        //把完整的用户信息存入redis，其中userid作为key，注意存入redis的时候加了前缀 login:
        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);
        redisUtil.set("login:"+userid,loginUser);
        return ResponseResult.ok(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登陆的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisUtil.remove("login:"+userId);
        return ResponseResult.ok();
    }
}
