package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.entity.User;
import com.sakura.user.param.UserPageParam;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 用户表 Mapper 接口
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
