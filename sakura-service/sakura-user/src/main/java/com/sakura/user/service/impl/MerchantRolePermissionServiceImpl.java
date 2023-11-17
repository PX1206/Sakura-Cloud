package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.LoginUtil;
import com.sakura.user.entity.MerchantRole;
import com.sakura.user.entity.MerchantRolePermission;
import com.sakura.user.mapper.MerchantPermissionMapper;
import com.sakura.user.mapper.MerchantRoleMapper;
import com.sakura.user.mapper.MerchantRolePermissionMapper;
import com.sakura.user.param.MerchantRolePermissionParam;
import com.sakura.user.service.MerchantRolePermissionService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商户角色权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class MerchantRolePermissionServiceImpl extends BaseServiceImpl<MerchantRolePermissionMapper, MerchantRolePermission> implements MerchantRolePermissionService {

    @Autowired
    private MerchantRolePermissionMapper merchantRolePermissionMapper;
    @Autowired
    private MerchantRoleMapper merchantRoleMapper;
    @Autowired
    private MerchantPermissionMapper merchantPermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMerchantRolePermission(MerchantRolePermissionParam merchantRolePermissionParam) throws Exception {
        // 商户需要校验角色信息
        MerchantRole merchantRole = merchantRoleMapper.selectOne(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getId, merchantRolePermissionParam.getRoleId())
                        .eq(MerchantRole::getStatus, 1)
        );
        if (merchantRole == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        // 还需要校验权限ID是否为商户所有，防止越级赋权
        // 获取商户所有权限
        Set<Integer> allMerchantPermissionId = merchantPermissionMapper.findAllMerchantPermissionId(LoginUtil.getMerchantNo());
        // 检查是否存在不属于商户的权限ID，存在的话直接抛出异常
        Set<Integer> checkPermissions = merchantRolePermissionParam.getPermissionIds().stream()
                .filter(element -> !allMerchantPermissionId.contains(element))
                .collect(Collectors.toSet());
        if (checkPermissions.size() > 0) {
            throw new BusinessException(500, "权限信息异常");
        }

        // 先获取角色原有权限信息
        Set<Integer> permissionIds = merchantRolePermissionMapper.findPermissionIdByRoleId(merchantRolePermissionParam.getRoleId());
        // 如果角色原先就有权限则先处理原有权限
        if (permissionIds != null && permissionIds.size() > 0) {
            // 找出新增的权限
            Set<Integer> newAddPermissions = merchantRolePermissionParam.getPermissionIds().stream()
                    .filter(element -> !permissionIds.contains(element))
                    .collect(Collectors.toSet());
            if (newAddPermissions.size() > 0) {
                merchantRolePermissionMapper.saveMerchantRolePermission(merchantRolePermissionParam.getRoleId(), newAddPermissions);
            }
            // 找出删除的权限
            Set<Integer> deletePermissions = permissionIds.stream()
                    .filter(element -> !merchantRolePermissionParam.getPermissionIds().contains(element))
                    .collect(Collectors.toSet());
            if (deletePermissions.size() > 0) {
                merchantRolePermissionMapper.deleteByPermissionsId(merchantRolePermissionParam.getRoleId(), deletePermissions);
            }
        } else {
            merchantRolePermissionMapper.saveMerchantRolePermission(merchantRolePermissionParam.getRoleId(), merchantRolePermissionParam.getPermissionIds());
        }

        return true;
    }

    @Override
    public Set<Integer> getMerchantRolePermissionId(Integer roleId) {
        // 商户需要校验角色信息
        MerchantRole merchantRole = merchantRoleMapper.selectOne(
                Wrappers.<MerchantRole>lambdaQuery()
                        .eq(MerchantRole::getMerchantNo, LoginUtil.getMerchantNo())
                        .eq(MerchantRole::getId, roleId)
                        .eq(MerchantRole::getStatus, 1)
        );
        if (merchantRole == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        return merchantRolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

}
