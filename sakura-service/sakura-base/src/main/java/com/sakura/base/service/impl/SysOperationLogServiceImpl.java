package com.sakura.base.service.impl;

import com.sakura.base.entity.SysOperationLog;
import com.sakura.base.mapper.SysOperationLogMapper;
import com.sakura.base.service.SysOperationLogService;
import com.sakura.base.param.SysOperationLogPageParam;
import com.sakura.base.vo.SysOperationLogVo;
import com.sakura.common.base.BaseServiceImpl;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.pagination.Paging;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author Sakura
 * @since 2023-10-24
 */
@Slf4j
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {

    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;


    @Override
    public SysOperationLogVo getSysOperationLog(Long id) throws Exception {
        SysOperationLog sysOperationLog = sysOperationLogMapper.selectById(id);
        if (sysOperationLog == null) {
            throw new BusinessException(500, "数据异常");
        }
        SysOperationLogVo sysOperationLogVo = new SysOperationLogVo();
        BeanUtils.copyProperties(sysOperationLog, sysOperationLogVo);

        return sysOperationLogVo;
    }

    @Override
    public Paging<SysOperationLogVo> getSysOperationLogPageList(SysOperationLogPageParam sysOperationLogPageParam) throws Exception {
        Page<SysOperationLogVo> page = new Page<>(sysOperationLogPageParam.getPageIndex(), sysOperationLogPageParam.getPageSize());
        IPage<SysOperationLogVo> iPage = sysOperationLogMapper.getSysOperationLogList(page, sysOperationLogPageParam);
        return new Paging<SysOperationLogVo>(iPage);
    }

}
