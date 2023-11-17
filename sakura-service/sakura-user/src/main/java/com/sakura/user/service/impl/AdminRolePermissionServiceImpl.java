package com.sakura.user.service.impl;

import com.sakura.user.entity.AdminRolePermission;
import com.sakura.user.mapper.AdminRolePermissionMapper;
import com.sakura.user.param.AdminRolePermissionParam;
import com.sakura.user.service.AdminRolePermissionService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * admin角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminRolePermissionServiceImpl extends BaseServiceImpl<AdminRolePermissionMapper, AdminRolePermission> implements AdminRolePermissionService {

    @Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAdminRolePermission(AdminRolePermissionParam adminRolePermissionParam) throws Exception {
        // 先获取角色原有权限信息
        Set<Integer> permissionIds = adminRolePermissionMapper.findPermissionIdByRoleId(adminRolePermissionParam.getRoleId());
        // 如果角色原先就有权限则先处理原有权限
        if (permissionIds != null && permissionIds.size() > 0) {
            // 找出新增的权限
            Set<Integer> newAddPermissions = adminRolePermissionParam.getPermissionIds().stream()
                    .filter(element -> !permissionIds.contains(element))
                    .collect(Collectors.toSet());
            if (newAddPermissions.size() > 0) {
                adminRolePermissionMapper.saveAdminRolePermission(adminRolePermissionParam.getRoleId(), newAddPermissions);
            }
            // 找出删除的权限
            Set<Integer> deletePermissions = permissionIds.stream()
                    .filter(element -> !adminRolePermissionParam.getPermissionIds().contains(element))
                    .collect(Collectors.toSet());
            if (deletePermissions.size() > 0) {
                adminRolePermissionMapper.deleteByPermissionsId(adminRolePermissionParam.getRoleId(), deletePermissions);
            }
        } else {
            adminRolePermissionMapper.saveAdminRolePermission(adminRolePermissionParam.getRoleId(), adminRolePermissionParam.getPermissionIds());
        }

        return true;
    }

    @Override
    public Set<Integer> getAdminRolePermissionId(Integer roleId) {
        return adminRolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

}
