package com.sakura.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sakura.user.entity.Permission;
import com.sakura.user.mapper.PermissionMapper;
import com.sakura.user.service.PermissionService;
import com.sakura.user.param.PermissionPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 权限表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean savePermission(Permission permission) throws Exception {
        return super.save(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePermission(Permission permission) throws Exception {
        return super.updateById(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePermission(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Permission> getPermissionPageList(PermissionPageParam permissionPageParam) throws Exception {
        Page<Permission> page = new PageInfo<>(permissionPageParam, OrderItem.desc(getLambdaColumn(Permission::getCreateDt)));
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        IPage<Permission> iPage = permissionMapper.selectPage(page, wrapper);
        return new Paging<Permission>(iPage);
    }

    @Override
    public Set<String> getCodeByUrl(String strJson) throws Exception {
        JSONObject json = JSONObject.parseObject(strJson);
        Set<String> codes = permissionMapper.findPermissionCodeByUrl(json.getString("url"));
        return codes;
    }

}
