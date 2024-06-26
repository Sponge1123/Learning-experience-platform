package com.buka.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buka.constants.SystemConstants;
import com.buka.mapper.MenuMapper;
import com.buka.config.LoginUserDetails;
import com.buka.pojo.User;
import com.buka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,username);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //返回用户信息
        if (!user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUserDetails(user,list);
        }
        return new LoginUserDetails(user,null);
    }
}
