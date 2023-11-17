package com.sakura.user.service;

import com.sakura.user.entity.MerchantPermission;
import com.sakura.common.base.BaseService;
import com.sakura.user.param.MerchantPermissionParam;
import com.sakura.user.vo.MerchantPermissionTreeVo;
import com.sakura.user.vo.MerchantPermissionVo;

import java.util.List;

/**
 * 商户权限表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantPermissionService extends BaseService<MerchantPermission> {

    /**
     * 保存
     *
     * @param merchantPermissionParam
     * @return
     * @throws Exception
     */
    boolean addMerchantPermission(MerchantPermissionParam merchantPermissionParam) throws Exception;

    /**
     * 获取权限
     *
     * @param merchantNo
     * @return
     * @throws Exception
     */
    List<MerchantPermissionVo> getMerchantPermission(String merchantNo);

    /**
     * 获取商户权限树
     *
     * @return
     * @throws Exception
     */
    List<MerchantPermissionTreeVo> getMerchantPermissionTree() throws Exception;

}
