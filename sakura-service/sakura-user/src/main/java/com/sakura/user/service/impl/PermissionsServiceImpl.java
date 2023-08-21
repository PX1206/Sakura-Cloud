package com.sakura.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sakura.user.entity.Permissions;
import com.sakura.user.mapper.PermissionsMapper;
import com.sakura.user.service.PermissionsService;
import com.sakura.user.param.PermissionsPageParam;
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
public class PermissionsServiceImpl extends BaseServiceImpl<PermissionsMapper, Permissions> implements PermissionsService {

    @Autowired
    private PermissionsMapper permissionsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean savePermissions(Permissions permissions) throws Exception {
        return super.save(permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePermissions(Permissions permissions) throws Exception {
        return super.updateById(permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePermissions(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public Paging<Permissions> getPermissionsPageList(PermissionsPageParam permissionsPageParam) throws Exception {
        Page<Permissions> page = new PageInfo<>(permissionsPageParam, OrderItem.desc(getLambdaColumn(Permissions::getCreateDt)));
        LambdaQueryWrapper<Permissions> wrapper = new LambdaQueryWrapper<>();
        IPage<Permissions> iPage = permissionsMapper.selectPage(page, wrapper);
        return new Paging<Permissions>(iPage);
    }

    @Override
    public Set<String> getCodeByUrl(String strJson) throws Exception {
        JSONObject json = JSONObject.parseObject(strJson);
        Set<String> codes = permissionsMapper.findPermissionCodeByUrl(json.getString("url"));
        return codes;
    }

}
