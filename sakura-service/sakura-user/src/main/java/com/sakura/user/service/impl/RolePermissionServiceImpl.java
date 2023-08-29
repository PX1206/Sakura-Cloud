package com.sakura.user.service.impl;

import com.sakura.user.entity.RolePermission;
import com.sakura.user.mapper.RolePermissionMapper;
import com.sakura.user.param.RolePermissionParam;
import com.sakura.user.service.RolePermissionService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRolePermission(RolePermissionParam rolePermissionParam) throws Exception {
        // 先获取角色原有权限信息
        Set<Integer> permissionIds = rolePermissionMapper.findPermissionIdByRoleId(rolePermissionParam.getRoleId());
        // 如果角色原先就有权限则先处理原有权限
        if (permissionIds != null && permissionIds.size() > 0) {
            // 找出新增的权限
            Set<Integer> newAddPermissions = rolePermissionParam.getPermissionIds().stream()
                    .filter(element -> !permissionIds.contains(element))
                    .collect(Collectors.toSet());
            if (newAddPermissions != null && newAddPermissions.size() > 0) {
                rolePermissionMapper.saveRolePermission(rolePermissionParam.getRoleId(), newAddPermissions);
            }
            // 找出删除的权限
            Set<Integer> deletePermissions = permissionIds.stream()
                    .filter(element -> !rolePermissionParam.getPermissionIds().contains(element))
                    .collect(Collectors.toSet());
            if (deletePermissions != null && deletePermissions.size() > 0) {
                rolePermissionMapper.deleteByPermissionsId(rolePermissionParam.getRoleId(), deletePermissions);
            }
        } else {
            rolePermissionMapper.saveRolePermission(rolePermissionParam.getRoleId(), rolePermissionParam.getPermissionIds());
        }

        return true;
    }

    @Override
    public Set<Integer> getRolePermissionId(Integer roleId) {
        Set<Integer> permissionIds = rolePermissionMapper.findPermissionIdByRoleId(roleId);
        return permissionIds;
    }

}
