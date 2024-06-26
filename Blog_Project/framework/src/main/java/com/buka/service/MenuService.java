package com.buka.service;

import com.buka.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2024-06-19 20:56:52
*/
public interface MenuService extends IService<Menu> {


    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult searchMenuList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult searchMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenuById(Long id);

    ResponseResult selectTree();

}
