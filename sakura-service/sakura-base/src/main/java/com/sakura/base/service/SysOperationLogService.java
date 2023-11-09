package com.sakura.base.service;

import com.sakura.base.entity.SysOperationLog;
import com.sakura.base.param.SysOperationLogPageParam;
import com.sakura.base.vo.SysOperationLogVo;
import com.sakura.common.base.BaseService;
import com.sakura.common.pagination.Paging;

/**
 *  服务类
 *
 * @author Sakura
 * @since 2023-10-24
 */
public interface SysOperationLogService extends BaseService<SysOperationLog> {

    /**
     * 详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysOperationLogVo getSysOperationLog(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sysOperationLogPageParam
     * @return
     * @throws Exception
     */
    Paging<SysOperationLogVo> getSysOperationLogPageList(SysOperationLogPageParam sysOperationLogPageParam) throws Exception;

}
