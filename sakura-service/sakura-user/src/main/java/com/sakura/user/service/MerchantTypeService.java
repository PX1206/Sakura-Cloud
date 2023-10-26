package com.sakura.user.service;

import com.sakura.user.entity.MerchantType;
import com.sakura.user.param.MerchantTypePageParam;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;
import com.sakura.user.param.MerchantTypeParam;
import com.sakura.user.vo.MerchantTypeVo;

/**
 * 商户类型表 服务类
 *
 * @author Sakura
 * @since 2023-10-26
 */
public interface MerchantTypeService extends BaseService<MerchantType> {

    /**
     * 保存
     *
     * @param merchantTypeParam
     * @return
     * @throws Exception
     */
    boolean saveMerchantType(MerchantTypeParam merchantTypeParam) throws Exception;

    /**
     * 修改
     *
     * @param merchantTypeParam
     * @return
     * @throws Exception
     */
    boolean updateMerchantType(MerchantTypeParam merchantTypeParam) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteMerchantType(Long id) throws Exception;

    /**
     * 详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    MerchantTypeVo getMerchantType(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param merchantTypePageParam
     * @return
     * @throws Exception
     */
    Paging<MerchantTypeVo> getMerchantTypePageList(MerchantTypePageParam merchantTypePageParam) throws Exception;

}
