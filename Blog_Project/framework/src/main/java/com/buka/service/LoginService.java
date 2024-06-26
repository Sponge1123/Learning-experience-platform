package com.buka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.pojo.User;
import com.buka.util.ResponseResult;

public interface LoginService{


    ResponseResult login(User user);

    ResponseResult logout();

}
