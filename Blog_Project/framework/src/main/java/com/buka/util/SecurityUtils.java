package com.buka.util;

import com.buka.config.LoginUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtils {
    /**
     * 获取用户
     * @return
     */
    public static LoginUserDetails getLoginUser(){
        return (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断是否为管理员
     * @return
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }
    /**
     * 获取当前用户id
     */
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }
}
