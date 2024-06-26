package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.dto.RoleDto;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.mapper.MenuMapper;
import com.buka.mapper.RoleMenuMapper;
import com.buka.pojo.Menu;
import com.buka.pojo.Role;
import com.buka.pojo.RoleMenu;
import com.buka.pojo.UserRole;
import com.buka.service.RoleMenuService;
import com.buka.service.RoleService;
import com.buka.mapper.RoleMapper;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.MenuVo;
import com.buka.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author SPONGE
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-06-19 20:56:58
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是则返回集合中只需要有admin
        if (id == 1L){
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //负责查询用户所具有的权限信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult searchRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isAllBlank(roleName,status)){
            if (StringUtils.isNotBlank(roleName)){
                queryWrapper.like(Role::getRoleName,roleName);
            }
            if (StringUtils.isNotBlank(status)){
                queryWrapper.eq(Role::getStatus,status);
            }
            return getResult(pageNum, pageSize, queryWrapper);
        }else {
            return getResult(pageNum, pageSize, queryWrapper);
        }
    }

    @Override
    public ResponseResult changeStatus(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getRoleId());
        role.setStatus(roleDto.getStatus());
        updateById(role);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,0);
        List<Role> list = list(queryWrapper);
        return ResponseResult.ok(list);
    }

    @Override
    public ResponseResult addUserRole(RoleVo roleVo) {
        Role role = MyBeanUtil.copyBean(roleVo, Role.class);
        boolean save = save(role);
        if (save){
            //更新用户的权限菜单
            List<String> menuIds = roleVo.getMenuIds();
            for (String menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(Long.valueOf(menuId));
                roleMenu.setRoleId(role.getId());
                roleMenuService.save(roleMenu);
            }
        }
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult searchRoleInfo(Long id) {
        Role role = getById(id);
        return ResponseResult.ok(role);

    }

    @Override
    public ResponseResult delRole(Long id) {
        boolean b = removeById(id);
        if (b){
            return ResponseResult.ok();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
    @Override
    public ResponseResult searchTreeById(Long id) {
        //角色id
        List<Menu> menus = menuMapper.selectAllRouterMenu();
        List<Menu> menuList = buildMenuTree(menus,0L);
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
        //根据角色id查询对应的菜单权限id
//        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(RoleMenu::getRoleId,id);
//        roleMenuService.g
        List<Long> MenuidList = roleMenuMapper.getMenuIds(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("menus",arrayList);
        map.put("checkedKeys",MenuidList);
        return ResponseResult.ok(map);
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
//
    @Override
    public ResponseResult updateRole(RoleVo roleVo) {
        Role role = MyBeanUtil.copyBean(roleVo, Role.class);
        boolean b = updateById(role);
        if (b){
            //角色更新成功，则修改对应的角色-菜单关联表
            List<String> menuIds = roleVo.getMenuIds();
            for (String menuId : menuIds) {
                LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(RoleMenu::getRoleId,role.getId());
                queryWrapper.eq(RoleMenu::getMenuId,menuId);
                if (roleMenuService.count(queryWrapper) == 0){
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(Long.valueOf(menuId));
                    roleMenuService.save(roleMenu);
                }
            }
        }
        return ResponseResult.ok();
    }

    @NotNull
    private ResponseResult getResult(Integer pageNum, Integer pageSize, LambdaQueryWrapper<Role> queryWrapper) {
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",page.getRecords());
        map.put("total",page.getTotal());
        return ResponseResult.ok(map);
    }
}




