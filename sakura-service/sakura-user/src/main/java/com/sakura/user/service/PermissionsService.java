package com.sakura.user.service;

import com.sakura.user.entity.Permissions;
import com.sakura.user.param.PermissionsPageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 * 权限表 服务类
 *
 * @author Sakura
 * @since 2023-08-17
 */
public interface PermissionsService extends BaseService<Permissions> {

    /**
     * 保存
     *
     * @param permissions
     * @return
     * @throws Exception
     */
    boolean savePermissions(Permissions permissions) throws Exception;

    /**
     * 修改
     *
     * @param permissions
     * @return
     * @throws Exception
     */
    boolean updatePermissions(Permissions permissions) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deletePermissions(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param permissionsPageParam
     * @return
     * @throws Exception
     */
    Paging<Permissions> getPermissionsPageList(PermissionsPageParam permissionsPageParam) throws Exception;

}
