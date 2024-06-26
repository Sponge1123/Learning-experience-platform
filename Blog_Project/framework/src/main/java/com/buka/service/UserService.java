package com.buka.service;

import com.buka.dto.UserDto;
import com.buka.dto.UserUpdateDto;
import com.buka.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;
import com.buka.vo.UserStatusVo;

/**
* @author SPONGE
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-06-12 13:50:49
*/
public interface UserService extends IService<User> {

    ResponseResult userInfo();


    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult searchUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserDto userDto);

    ResponseResult deleteUserById(Long id);

    ResponseResult searchUserInfo(Long id);

    ResponseResult updateInfo(UserUpdateDto userUpdateDto);

    ResponseResult changeUserStatus(UserStatusVo userStatusVo);
}
