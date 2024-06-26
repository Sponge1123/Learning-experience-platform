package com.buka.service;

import com.buka.dto.RoleDto;
import com.buka.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buka.util.ResponseResult;
import com.buka.vo.RoleVo;

import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2024-06-19 20:56:58
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult searchRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleDto roleDto);

    ResponseResult listAllRole();

    ResponseResult addUserRole(RoleVo roleVo);

    ResponseResult searchRoleInfo(Long id);

    ResponseResult delRole(Long id);


    ResponseResult updateRole(RoleVo roleVo);

    ResponseResult searchTreeById(Long id);
}
