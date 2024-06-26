package com.buka.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buka.config.LoginUserDetails;
import com.buka.pojo.User;
import com.buka.service.UserService;
import com.buka.util.JwtUtil;
import com.buka.util.MyBeanUtil;
import com.buka.util.RedisUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,username);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUserDetails(user);
    }

    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate != null){
            LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
            redisUtil.set(String.valueOf(loginUserDetails.getUser().getId()),loginUserDetails);
            String token = JwtUtil.getToken(String.valueOf(loginUserDetails.getUser().getId()));
            UserVo userVo = MyBeanUtil.copyBean(loginUserDetails.getUser(), UserVo.class);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("userInfo",userVo);
            return ResponseResult.ok(map);
        }
        throw new RuntimeException("用户名或密码错误");
    }

    public ResponseResult logout() {
        LoginUserDetails loginUserDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUserDetails!=null){
            redisUtil.remove(String.valueOf(loginUserDetails.getUser().getId()));
        }
        return ResponseResult.ok();
    }
}
