package com.buka.controller;

import com.buka.dto.RoleDto;
import com.buka.service.RoleService;
import com.buka.util.ResponseResult;
import com.buka.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 获取角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/system/role/list")
    public ResponseResult searchRoleList(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.searchRoleList(pageNum,pageSize,roleName,status);
    }

    /**
     * 修改角色状态
     * @param roleDto
     * @return
     */
    @PutMapping("/system/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleDto roleDto){
        return roleService.changeStatus(roleDto);
    }

    /**
     * 获取所有权限
     * @return
     */
    @GetMapping("/system/role/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    @PostMapping("/system/role")
    public ResponseResult addUserRole(@RequestBody RoleVo roleVo){
        return roleService.addUserRole(roleVo);
    }

    /**
     * 根据id获取角色信息
     * @param id
     * @return
     */
    @GetMapping("/system/role/{id}")
    public ResponseResult searchRoleInfo(@PathVariable("id") Long id){
        return roleService.searchRoleInfo(id);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/system/role/{id}")
    public ResponseResult delRole(@PathVariable("id") Long id){
        return roleService.delRole(id);
    }

    /**
     * 修改角色信息
     * @param roleVo
     * @return
     */
    @PutMapping("/system/role")
    public ResponseResult updateRole(@RequestBody RoleVo roleVo){
        return roleService.updateRole(roleVo);
    }

    /**
     * 根据id查询对应角色的菜单列表树接口
     * @param id
     * @return
     */
    //http://localhost:81/dev-api/system/menu/roleMenuTreeselect/3
    @GetMapping("/system/menu/roleMenuTreeselect/{id}")
    public ResponseResult searchTreeById(@PathVariable("id") Long id){
        return roleService.searchTreeById(id);
    }
}
