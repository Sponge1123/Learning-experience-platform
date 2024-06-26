package com.buka.controller;

import com.buka.pojo.User;
import com.buka.security.MyUserDetailsServiceImpl;
import com.buka.service.UserService;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return myUserDetailsService.login(user);
    }
    /**
     * 退出登陆
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return myUserDetailsService.logout();
    }
    /**
     * 查看个人信息
     * @return
     */
    @GetMapping("/user/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @PutMapping("/user/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    /**
     * 注册
     */
    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
