package com.sakura.user.service;

import com.sakura.user.entity.Permission;
import com.sakura.user.param.PermissionPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

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
     * @param permissions
     * @return
     * @throws Exception
     */
    boolean savePermission(Permission permission) throws Exception;

    /**
     * 修改
     *
     * @param permissions
     * @return
     * @throws Exception
     */
    boolean updatePermission(Permission permission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deletePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param permissionsPageParam
     * @return
     * @throws Exception
     */
    Paging<Permission> getPermissionPageList(PermissionPageParam permissionPageParam) throws Exception;

    Set<String> getCodeByUrl(String strJson) throws Exception;

}
