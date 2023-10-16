package com.sakura.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.constant.CommonConstant;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.redis.RedisUtil;
import com.sakura.user.entity.Permission;
import com.sakura.user.mapper.PermissionMapper;
import com.sakura.user.param.PermissionParam;
import com.sakura.user.service.PermissionService;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.user.vo.PermissionTreeVo;
import com.sakura.user.vo.PermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private RedisUtil redisUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean savePermission(PermissionParam permissionParam) throws Exception {
        // 校验code是否重复
        Integer num = permissionMapper.selectCount(
                Wrappers.<Permission>lambdaQuery()
                        .eq(Permission::getCode, permissionParam.getCode())
                        .eq(Permission::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "权限code重复");
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionParam, permission);
        permission.setStatus(1);

        return super.save(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePermission(PermissionParam permissionParam) throws Exception {
        // 校验code是否重复
        Integer num = permissionMapper.selectCount(
                Wrappers.<Permission>lambdaQuery()
                        .eq(Permission::getCode, permissionParam.getCode())
                        .notIn(Permission::getId, permissionParam.getId())
                        .eq(Permission::getStatus, 1));
        if (num > 0) {
            throw new BusinessException(500, "权限code重复");
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionParam, permission);
        permission.setUpdateDt(new Date());
        permissionMapper.updateById(permission);

        // 将数据存入Redis，防止因修改了权限导致认证异常
        if (permission.getUrl() != null) {
            redisUtil.sSetAndTime(CommonConstant.PERMISSION_URL + permission.getUrl(), 72*60*60, permission.getCode());
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePermission(Long id) throws Exception {
        // 获取权限信息
        Permission permission = permissionMapper.selectById(id);
        if (permission == null || permission.getStatus() != 1) {
            throw new BusinessException(500, "权限信息异常");
        }

        permission.setStatus(0);
        permission.setUpdateDt(new Date());
        permissionMapper.updateById(permission);

        return true;
    }

    @Override
    public PermissionVo getPermission(Long id) throws Exception {
        // 获取权限信息
        Permission permission = permissionMapper.selectById(id);
        if (permission == null || permission.getStatus() != 1) {
            throw new BusinessException(500, "权限信息异常");
        }

        PermissionVo permissionVo = new PermissionVo();
        BeanUtils.copyProperties(permission, permissionVo);

        return permissionVo;
    }

    @Override
    public List<PermissionTreeVo> getPermissionTree(Integer parentId) throws Exception {
        // 先获取所有的权限
        List<PermissionVo> permissionVos = permissionMapper.findAllPermission();
        // 递归遍历所有权限信息
        int num = 0; // 添加一个遍历层数，防止数据异常导致递归死循环
        List<PermissionTreeVo> permissionTreeVos = getChildPermissions(parentId, permissionVos, num);
        return permissionTreeVos;
    }

   /**
    * @description: 递归获取子权限
    * @param parentId 父ID
    * @param permissionVos 所有权限集合
    * @param num 最大遍历层数
    * @author: Sakura
    * @date: 2023/8/28 16:18
    */
    private List<PermissionTreeVo> getChildPermissions(Integer parentId, List<PermissionVo> permissionVos, int num) throws Exception {
        num++;// 控制遍历次数，防止因数据问题导致无限循环内存溢出，目前最大支持5层
        if (num > 5) {
            log.error("权限信息异常parentId：" + parentId);
            throw new BusinessException(500, "权限数据异常，请联系管理人员");
        }

        List<PermissionTreeVo> resultList = new ArrayList<>();

        for (PermissionVo permissionVo : permissionVos) {
            if (parentId.equals(permissionVo.getParentId())) {
                PermissionTreeVo permissionTreeVo = new PermissionTreeVo();
                BeanUtils.copyProperties(permissionVo, permissionTreeVo);
                // 通过当前ID获取子权限
                List<PermissionTreeVo> childPermissionTreeVos = getChildPermissions(permissionVo.getId(), permissionVos, num);
                permissionTreeVo.setChildList(childPermissionTreeVos);

                resultList.add(permissionTreeVo);
            }
        }

        return resultList;
    }

    @Override
    public Set<String> getCodeByUrl(String strJson) throws Exception {
        JSONObject json = JSONObject.parseObject(strJson);
        Set<String> codes = permissionMapper.findPermissionCodeByUrl(json.getString("url"));
        return codes;
    }

}
