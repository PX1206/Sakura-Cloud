package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.tool.CommonUtil;
import com.sakura.user.entity.AdminRole;
import com.sakura.user.mapper.AdminRoleMapper;
import com.sakura.user.param.AdminRoleParam;
import com.sakura.user.param.DeleteAdminRoleParam;
import com.sakura.user.service.AdminRoleService;
import com.sakura.user.param.AdminRolePageParam;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.pagination.Paging;
import com.sakura.common.pagination.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.user.vo.AdminRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * admin角色表 服务实现类
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Slf4j
@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveAdminRole(AdminRoleParam adminRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = adminRoleMapper.selectCount(
                Wrappers.<AdminRole>lambdaQuery()
                        .eq(AdminRole::getName, adminRoleParam.getName())
                        .eq(AdminRole::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleParam, adminRole);
        adminRole.setStatus(1);
        adminRoleMapper.insert(adminRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminRole(AdminRoleParam adminRoleParam) throws Exception {
        // 先校验角色名称是否重复
        Integer num = adminRoleMapper.selectCount(
                Wrappers.<AdminRole>lambdaQuery()
                        .eq(AdminRole::getName, adminRoleParam.getName())
                        .eq(AdminRole::getStatus, 1)
                        .notIn(AdminRole::getId, adminRoleParam.getId()));
        if (num > 0) {
            throw new BusinessException(500, "角色名称重复");
        }

        AdminRole adminRole = new AdminRole();
        BeanUtils.copyProperties(adminRoleParam, adminRole);
        adminRole.setUpdateDt(new Date());
        adminRoleMapper.updateById(adminRole);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteAdminRole(DeleteAdminRoleParam deleteAdminRoleParam) throws Exception {
        // 校验图片验证码是否正确，防止出现误操作
        if (!commonUtil.checkCode(deleteAdminRoleParam.getKey(), deleteAdminRoleParam.getPictureCode())) {
            throw new BusinessException(500, "图片验证码错误");
        }

        AdminRole adminRole = adminRoleMapper.selectById(deleteAdminRoleParam.getId());
        if (adminRole == null || adminRole.getStatus() != 1) {
            throw new BusinessException(500, "角色信息异常");
        }
        adminRole.setStatus(0);
        adminRole.setUpdateDt(new Date());
        adminRoleMapper.updateById(adminRole);

        return true;
    }

    @Override
    public AdminRoleVo getAdminRole(Long id) throws Exception {
        AdminRole adminRole = adminRoleMapper.selectById(id);
        if (adminRole == null || adminRole.getStatus() != 1) {
            throw new BusinessException(500, "角色信息异常");
        }

        AdminRoleVo adminRoleVo = new AdminRoleVo();
        BeanUtils.copyProperties(adminRole, adminRoleVo);

        return adminRoleVo;
    }

    @Override
    public Paging<AdminRoleVo> getAdminRolePageList(AdminRolePageParam adminRolePageParam) throws Exception {
        Page<AdminRoleVo> page = new PageInfo<>(adminRolePageParam, OrderItem.desc(getLambdaColumn(AdminRole::getCreateDt)));
        IPage<AdminRoleVo> iPage = adminRoleMapper.findAdminRoles(page, adminRolePageParam);
        return new Paging<>(iPage);
    }

}
