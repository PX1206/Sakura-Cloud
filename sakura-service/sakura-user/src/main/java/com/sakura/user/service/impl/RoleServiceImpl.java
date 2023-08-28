package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.StringUtil;
import com.sakura.user.entity.Role;
import com.sakura.user.mapper.RoleMapper;
import com.sakura.user.param.DeleteRoleParam;
import com.sakura.user.param.RoleParam;
import com.sakura.user.service.RoleService;
import com.sakura.user.param.RolePageParam;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.tool.CommonUtil;
import com.sakura.user.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    CommonUtil commonUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(RoleParam roleParam) throws Exception {
        // 先校验角色名称和code是否重复
        Integer num = roleMapper.selectCount(
                Wrappers.<Role>lambdaQuery()
                        .and(w -> w.eq(Role::getName, roleParam.getName())
                                .or().eq(Role::getCode, roleParam.getCode()))
                        .eq(Role::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "角色名称或code重复");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleParam, role);
        role.setStatus(1);
        roleMapper.insert(role);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRole(RoleParam roleParam) throws Exception {
        // 先校验角色名称和code是否重复
        Integer num = roleMapper.selectCount(
                Wrappers.<Role>lambdaQuery()
                        .and(w -> w.eq(Role::getName, roleParam.getName())
                                .or().eq(Role::getCode, roleParam.getCode()))
                        .eq(Role::getStatus, 1)
                        .notIn(Role::getId, roleParam.getId()));
        if (num > 0) {
            throw new BusinessException(500, "角色名称或code重复");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleParam, role);
        roleMapper.updateById(role);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRole(DeleteRoleParam deleteRoleParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteRoleParam.getKey(), deleteRoleParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        Role role = roleMapper.selectById(deleteRoleParam.getId());
        role.setStatus(0);
        role.setUpdateDt(new Date());
        roleMapper.updateById(role);

        return true;
    }

    @Override
    public RoleVo getRole(Long id) throws Exception {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(500, "角色信息异常");
        }

        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);

        return roleVo;
    }

    @Override
    public Paging<RoleVo> getRolePageList(RolePageParam rolePageParam) throws Exception {
        Page<RoleVo> page = new PageInfo<>(rolePageParam, OrderItem.desc(getLambdaColumn(Role::getCreateDt)));
        IPage<RoleVo> iPage = roleMapper.findRoles(page, rolePageParam);
        return new Paging<RoleVo>(iPage);
    }

}
