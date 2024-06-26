package com.buka.controller;

import com.buka.pojo.Menu;
import com.buka.service.MenuService;
import com.buka.service.RoleService;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    /**
     * 获取菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/system/menu/list")
    public ResponseResult searchMenuList(String status,String menuName){
        return menuService.searchMenuList(status,menuName);
    }
    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @PostMapping("/system/menu")
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    /**
     * 根据id查询菜单数据
     * @param id
     * @return
     */
    @GetMapping("/system/menu/{id}")
    public ResponseResult searchMenuById(@PathVariable("id") Long id){
        return menuService.searchMenuById(id);
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PutMapping("/system/menu")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    /**
     * 根据Id删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/system/menu/{id}")
    public ResponseResult deleteMenuById(@PathVariable("id") Long id){
        return menuService.deleteMenuById(id);
    }

    /**
     * 加载对应角色菜单列表树接口
     * @return
     */
    @GetMapping("/system/menu/treeselect")
    public ResponseResult selectTree(){
        return menuService.selectTree();
    }

}
