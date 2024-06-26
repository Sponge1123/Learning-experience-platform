package com.buka.mapper;

import com.buka.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2024-06-19 20:56:52
* @Entity com.buka.pojo.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRoutersMenuTreeByUserId(Long userId);
}




