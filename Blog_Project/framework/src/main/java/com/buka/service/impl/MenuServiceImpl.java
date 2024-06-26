package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.constants.SystemConstants;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.pojo.Menu;
import com.buka.service.MenuService;
import com.buka.mapper.MenuMapper;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.MenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author SPONGE
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2024-06-19 20:56:52
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        if (userId == 1){
            //超级管理员
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //不是管理员
            menus = menuMapper.selectRoutersMenuTreeByUserId(userId);
        }
        List<Menu> menuList = buildMenuTree(menus,0L);
        return menuList;
    }

    @Override
    public ResponseResult searchMenuList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isAllBlank(status,menuName)) {
            if (StringUtils.isNotBlank(status)) {
                queryWrapper.eq(Menu::getStatus, status);
            }
            if (StringUtils.isNotBlank(menuName)) {
                queryWrapper.like(Menu::getMenuName, menuName);
            }
            queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
            List<Menu> list = list(queryWrapper);
            return ResponseResult.ok(list);
        }else {
            queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
            List<Menu> list = list();
            return ResponseResult.ok(list);
        }
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult searchMenuById(Long id) {
        Menu menu = getById(id);
        return ResponseResult.ok(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        boolean b = updateById(menu);
        if (b){
            return ResponseResult.ok();
        }
        else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteMenuById(Long id) {
        boolean b = removeById(id);
        if (b){
            return ResponseResult.ok();
        }
        else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult selectTree() {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = menuMapper.selectAllRouterMenu();
        List<Menu> menuList = buildMenuTree(menus,0L);
        //List<MenuVo> menuVoList = MyBeanUtil.copyBeanList(menuList, MenuVo.class);
        ArrayList<MenuVo> arrayList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(menu.getId());
            menuVo.setLabel(menu.getMenuName());
            menuVo.setParentId(menu.getParentId());
            List<Menu> children = menu.getChildren();
            List<MenuVo> voList = children.stream().map(menu1 -> {
                MenuVo menuVo1 = new MenuVo();
                menuVo1.setId(menu1.getId());
                menuVo1.setLabel(menu1.getMenuName());
                menuVo1.setParentId(menu1.getParentId());
                return menuVo1;
            }).collect(Collectors.toList());
            menuVo.setChildren(voList);
            arrayList.add(menuVo);
        }
        return ResponseResult.ok(arrayList);
    }

    public List<Menu> buildMenuTree(List<Menu> menus,Long i){
        //先查询出来所有的根菜单
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        for (Menu menu:menus){
            if (menu.getParentId().equals(i)){
                //查询根菜单下的子菜单
                List<Menu> menuList = buildMenuTree(menus,menu.getId());
                menu.setChildren(menuList);
                menuArrayList.add(menu);
            }
        }
        return menuArrayList;
    }
    public List<MenuVo> buildMenuTreeVo(List<MenuVo> menus,Long i){
        //先查询出来所有的根菜单
        ArrayList<MenuVo> menuArrayList = new ArrayList<>();
        for (MenuVo menu:menus){
            if (menu.getParentId().equals(i)){
                //查询根菜单下的子菜单
                List<MenuVo> menuList = buildMenuTreeVo(menus,menu.getId());
                menu.setChildren(menuList);
                menuArrayList.add(menu);
            }
        }
        return menuArrayList;
    }
}




