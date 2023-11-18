package com.sakura.user.service.impl;

import com.sakura.user.entity.MerchantTypePermission;
import com.sakura.user.mapper.MerchantTypePermissionMapper;
import com.sakura.user.param.MerchantTypePermissionParam;
import com.sakura.user.service.MerchantTypePermissionService;
import com.sakura.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商户类型权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-10-26
 */
@Slf4j
@Service
public class MerchantTypePermissionServiceImpl extends BaseServiceImpl<MerchantTypePermissionMapper, MerchantTypePermission> implements MerchantTypePermissionService {

    @Autowired
    private MerchantTypePermissionMapper merchantTypePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMerchantTypePermission(MerchantTypePermissionParam merchantTypePermissionParam) throws Exception {
        // 如果权限ID为空则表示清空类型权限
        if (merchantTypePermissionParam.getPermissionIds() == null || merchantTypePermissionParam.getPermissionIds().size() < 1) {
            // 清空权限直接返回
            merchantTypePermissionMapper.deleteByTypeId(merchantTypePermissionParam.getTypeId());
            return true;
        }
        // 先获取类型原有权限信息
        Set<Integer> permissionIds = merchantTypePermissionMapper.findPermissionIdByTypeId(merchantTypePermissionParam.getTypeId());
        // 如果类型原先就有权限则先处理原有权限
        if (permissionIds != null && permissionIds.size() > 0) {
            // 找出新增的权限
            Set<Integer> newAddPermissions = merchantTypePermissionParam.getPermissionIds().stream()
                    .filter(element -> !permissionIds.contains(element))
                    .collect(Collectors.toSet());
            if (newAddPermissions.size() > 0) {
                merchantTypePermissionMapper.saveMerchantTypePermission(merchantTypePermissionParam.getTypeId(), newAddPermissions);
            }
            // 找出删除的权限
            Set<Integer> deletePermissions = permissionIds.stream()
                    .filter(element -> !merchantTypePermissionParam.getPermissionIds().contains(element))
                    .collect(Collectors.toSet());
            if (deletePermissions.size() > 0) {
                merchantTypePermissionMapper.deleteByPermissionsId(merchantTypePermissionParam.getTypeId(), deletePermissions);
            }
        } else {
            merchantTypePermissionMapper.saveMerchantTypePermission(merchantTypePermissionParam.getTypeId(), merchantTypePermissionParam.getPermissionIds());
        }

        return true;
    }

    @Override
    public Set<Integer> getMerchantTypePermissionId(Integer typeId) {
        return merchantTypePermissionMapper.findPermissionIdByTypeId(typeId);
    }

}
