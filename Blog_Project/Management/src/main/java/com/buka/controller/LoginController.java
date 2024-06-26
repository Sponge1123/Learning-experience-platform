package com.buka.controller;

import com.buka.config.LoginUserDetails;
import com.buka.pojo.Menu;
import com.buka.pojo.User;
import com.buka.service.LoginService;
import com.buka.service.MenuService;
import com.buka.service.RoleService;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.util.SecurityUtils;
import com.buka.vo.AdminUserInfoVo;
import com.buka.vo.RoutersVo;
import com.buka.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    /**
     * 用户登陆
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }
    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前登陆的用户
        LoginUserDetails loginUser = SecurityUtils.getLoginUser();
        //根据id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserVo userVo = MyBeanUtil.copyBean(user, UserVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userVo);
        return ResponseResult.ok(adminUserInfoVo);

    }
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.ok(new RoutersVo(menus));
    }
    /**
     * 退出登陆
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
