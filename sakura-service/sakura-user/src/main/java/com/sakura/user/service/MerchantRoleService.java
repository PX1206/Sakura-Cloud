package com.sakura.user.service;

import com.sakura.user.entity.MerchantRole;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.param.DeleteMerchantRoleParam;
import com.sakura.user.param.MerchantRolePageParam;
import com.sakura.user.param.MerchantRoleParam;
import com.sakura.user.vo.MerchantRoleVo;

/**
 * 商户角色表 服务类
 *
 * @author Sakura
 * @since 2023-09-26
 */
public interface MerchantRoleService extends BaseService<MerchantRole> {

    /**
     * 保存
     *
     * @param merchantRoleParam
     * @return
     * @throws Exception
     */
    boolean saveMerchantRole(MerchantRoleParam merchantRoleParam) throws Exception;

    /**
     * 修改
     *
     * @param merchantRoleParam
     * @return
     * @throws Exception
     */
    boolean updateMerchantRole(MerchantRoleParam merchantRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param deleteMerchantRoleParam
     * @return
     * @throws Exception
     */
    boolean deleteMerchantRole(DeleteMerchantRoleParam deleteMerchantRoleParam) throws Exception;

    /**
     * 获取角色详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    MerchantRoleVo getMerchantRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param merchantRolePageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantRoleVo> getMerchantRolePageList(MerchantRolePageParam merchantRolePageParam) throws Exception;

}
