package com.buka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buka.config.LoginUserDetails;
import com.buka.dto.UserDto;
import com.buka.dto.UserUpdateDto;
import com.buka.enums.AppHttpCodeEnum;
import com.buka.mapper.UserRoleMapper;
import com.buka.pojo.Role;
import com.buka.pojo.User;
import com.buka.pojo.UserRole;
import com.buka.service.RoleService;
import com.buka.service.UserRoleService;
import com.buka.service.UserService;
import com.buka.mapper.UserMapper;
import com.buka.util.MyBeanUtil;
import com.buka.util.ResponseResult;
import com.buka.vo.UserStatusVo;
import com.buka.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
* @author SPONGE
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-06-12 13:50:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public ResponseResult userInfo() {
        LoginUserDetails loginUserDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUserDetails.getUser();
        Long userId = user.getId();
        User loginUser = getById(userId);
        UserVo userVo = MyBeanUtil.copyBean(loginUser, UserVo.class);
        return ResponseResult.ok(userVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new RuntimeException("用户名不能为空");
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new RuntimeException("密码不能为空");
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new RuntimeException("昵称不能为空");
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new RuntimeException("邮箱不能为空");
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new RuntimeException("用户名不为空");
        }
        if (nickNameExist(user.getNickName())){
            throw new RuntimeException("昵称不为空");
        }
        //对密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult searchUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userName != null){
            queryWrapper.like(User::getUserName,userName);
        }
        if (phonenumber !=null){
            queryWrapper.eq(User::getPhonenumber,phonenumber);
        }
        if (status!=null){
            queryWrapper.eq(User::getStatus,status);
        }
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",page.getRecords());
        map.put("total",page.getTotal());
        return ResponseResult.ok(map);
    }

    @Override
    public ResponseResult addUser(UserDto userDto) {
        if (userDto.getUserName() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if(userDto.getEmail()!=null){
            queryWrapper.eq(User::getEmail,userDto.getEmail());
            int count = count(queryWrapper);
            if (count>0){
                return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
            }
        }
        if(userDto.getUserName()!=null){
            queryWrapper.eq(User::getUserName,userDto.getUserName());
            int count = count(queryWrapper);
            if (count>0){
                return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
            }
        }
        if(userDto.getPhonenumber()!=null){
            queryWrapper.eq(User::getPhonenumber,userDto.getPhonenumber());
            int count = count(queryWrapper);
            if (count>0){
                return ResponseResult.errorResult(AppHttpCodeEnum.PHONENUMBER_EXIST);
            }
        }
        User user = MyBeanUtil.copyBean(userDto, User.class);
        //给密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //保存用户
        save(user);
        //保存用户权限
        List<String> roleIds = userDto.getRoleIds();
//        for (String roleId : roleIds) {
//            UserRole userRole = new UserRole();
//            userRole.setUserId(user.getId());
//            userRole.setRoleId(Long.valueOf(roleId));
//            userRoleService.save(userRole);
//        }
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        for (String roleId : roleIds) {
            wrapper.eq(UserRole::getRoleId,roleId);
            wrapper.eq(UserRole::getUserId,user.getId());
            userRoleService.update(wrapper);
        }
        return ResponseResult.ok();
    }


    @Override
    public ResponseResult deleteUserById(Long id) {
        removeById(id);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult searchUserInfo(Long id) {
        //1.获取用户所关联角色id列表
        List<String> userRoleList = userRoleMapper.getRoleListById(id);
        //2.所有角色的列表
        List<Role> roleList = roleService.list();
        //3.用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = getOne(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("roleIds",userRoleList);
        map.put("roles",roleList);
        map.put("user",user);
        return ResponseResult.ok(map);
    }

    @Override
    public ResponseResult updateInfo(UserUpdateDto userUpdateDto) {
        User user = MyBeanUtil.copyBean(userUpdateDto, User.class);
        List<String> roleIds = userUpdateDto.getRoleIds();
        boolean b = updateById(user);
        if (b){
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.valueOf(roleId));
                userRoleService.updateById(userRole);
            }
            return ResponseResult.ok();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @Override
    public ResponseResult changeUserStatus(UserStatusVo userStatusVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User user = new User();
        user.setId(userStatusVo.getUserId());
        user.setStatus(userStatusVo.getStatus());
        updateById(user);
        return ResponseResult.ok();
    }

    private boolean nickNameExist(String nickName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }
    private boolean userNameExist(String userName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
}




