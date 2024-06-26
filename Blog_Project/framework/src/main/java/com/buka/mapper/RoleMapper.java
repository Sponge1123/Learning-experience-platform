package com.buka.mapper;

import com.buka.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2024-06-19 20:56:58
* @Entity com.buka.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




