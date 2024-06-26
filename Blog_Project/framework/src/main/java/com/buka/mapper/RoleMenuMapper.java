package com.buka.mapper;

import com.buka.pojo.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
* @createDate 2024-06-24 15:20:18
* @Entity com.buka.pojo.RoleMenu
*/
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select menu_id from sys_role_menu where role_id = #{id}")
    List<Long> getMenuIds(Long id);
}




