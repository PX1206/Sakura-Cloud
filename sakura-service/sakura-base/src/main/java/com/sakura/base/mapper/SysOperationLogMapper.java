package com.sakura.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.base.entity.SysOperationLog;
import com.sakura.base.param.SysOperationLogPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author Sakura
 * @since 2023-10-24
 */
@Mapper
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {


}
