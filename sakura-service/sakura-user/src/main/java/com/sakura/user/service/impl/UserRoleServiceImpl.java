package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.user.entity.Role;
import com.sakura.user.entity.User;
import com.sakura.user.entity.UserRole;
import com.sakura.user.mapper.RoleMapper;
import com.sakura.user.mapper.UserMapper;
import com.sakura.user.mapper.UserRoleMapper;
import com.sakura.user.param.UserRoleParam;
import com.sakura.user.service.UserRoleService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 用户角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUserRole(UserRoleParam userRoleParam) throws Exception {
        // 先校验用户信息是否正确
        User user = userMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserId, userRoleParam.getUserId())
                        .eq(User::getStatus, 1));
        if (user == null) {
            throw new BusinessException(500, "用户信息异常");
        }
        // 校验角色信息是否正确
        Role role = roleMapper.selectOne(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, userRoleParam.getRoleCode())
                        .eq(Role::getStatus, 1));
        if (role == null) {
            throw new BusinessException(500, "角色信息异常");
        }
        // 校验是否已存在该角色信息
        UserRole userRole = userRoleMapper.selectOne(
                Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUserId, userRoleParam.getUserId())
                        .eq(UserRole::getRoleId, role.getId())
                        .eq(UserRole::getStatus, 1));
        if (userRole != null) {
            throw new BusinessException(500, "当前角色已存在");
        }

        userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(role.getId());
        userRole.setStatus(1);

        return super.save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUserRole(UserRoleParam userRoleParam) throws Exception {
        // 校验角色信息是否正确
        Role role = roleMapper.selectOne(
                Wrappers.<Role>lambdaQuery()
                        .eq(Role::getCode, userRoleParam.getRoleCode())
                        .eq(Role::getStatus, 1));
        if (role == null) {
            throw new BusinessException(500, "角色信息异常");
        }
        // 校验是否已存在该角色信息
        UserRole userRole = userRoleMapper.selectOne(
                Wrappers.<UserRole>lambdaQuery()
                        .eq(UserRole::getUserId, userRoleParam.getUserId())
                        .eq(UserRole::getRoleId, role.getId())
                        .eq(UserRole::getStatus, 1));
        if (userRole == null) {
            throw new BusinessException(500, "用户角色信息异常");
        }

        // 将数据置为不可用
        userRole.setStatus(0);
        userRole.setUpdateDt(new Date());
        userRoleMapper.updateById(userRole);

        return true;
    }

}
