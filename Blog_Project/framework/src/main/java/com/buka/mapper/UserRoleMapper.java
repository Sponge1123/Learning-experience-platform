package com.buka.mapper;

import com.buka.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2024-06-23 16:26:32
* @Entity com.buka.pojo.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("select role_id from sys_user_role where user_id = #{id}")
    List<String> getRoleListById(Long id);
}




