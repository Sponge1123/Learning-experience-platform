package com.buka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.pojo.UserRole;
import com.buka.service.UserRoleService;
import com.buka.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author SPONGE
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-06-23 16:26:32
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




