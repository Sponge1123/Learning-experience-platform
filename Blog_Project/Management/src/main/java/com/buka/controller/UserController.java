package com.buka.controller;

import com.buka.dto.UserDto;
import com.buka.dto.UserUpdateDto;
import com.buka.service.UserService;
import com.buka.util.ResponseResult;
import com.buka.vo.UserStatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @return
     */
    @GetMapping("/system/user/list")
    public ResponseResult searchUserList(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.searchUserList(pageNum,pageSize,userName,phonenumber,status);
    }

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    @PostMapping("/system/user")
    public ResponseResult addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/system/user/{id}")
    public ResponseResult deleteUserById(@PathVariable("id") Long id){
        return userService.deleteUserById(id);
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/system/user/{id}")
    public ResponseResult searchUserInfo(@PathVariable("id") Long id){
        return userService.searchUserInfo(id);
    }

    /**
     * 更新用户信息
     * @param userUpdateDto
     * @return
     */
    @PutMapping("/system/user")
    public ResponseResult updateInfo(@RequestBody UserUpdateDto userUpdateDto){
        return userService.updateInfo(userUpdateDto);
    }
    //system/user/changeStatus

    /**
     * 修改用户状态
     * @param userStatusVo
     * @return
     */
    @PutMapping("/system/user/changeStatus")
    public ResponseResult changeUserStatus(@RequestBody UserStatusVo userStatusVo){
        return userService.changeUserStatus(userStatusVo);
    }
}
