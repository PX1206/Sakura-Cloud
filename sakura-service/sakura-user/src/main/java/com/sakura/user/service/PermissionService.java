package com.sakura.user.service;

import com.sakura.user.entity.Permission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.PermissionParam;
import com.sakura.user.vo.PermissionTreeVo;
import com.sakura.user.vo.PermissionVo;

import java.util.List;
import java.util.Set;

/**
 * 权限表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 保存
     *
     * @param permissionParam
     * @return
     * @throws Exception
     */
    boolean savePermission(PermissionParam permissionParam) throws Exception;

    /**
     * 修改
     *
     * @param permissionParam
     * @return
     * @throws Exception
     */
    boolean updatePermission(PermissionParam permissionParam) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deletePermission(Long id) throws Exception;

    /**
     * 权限表详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    PermissionVo getPermission(Long id) throws Exception;


    /**
     * 获取权限树
     *
     * @param parentId
     * @return
     * @throws Exception
     */
    List<PermissionTreeVo> getPermissionTree(Integer parentId) throws Exception;

    Set<String> getCodeByUrl(String strJson) throws Exception;

}
