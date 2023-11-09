package com.sakura.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.base.entity.LocalFile;
import com.sakura.base.param.FilePageParam;
import com.sakura.base.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LocalFileMapper extends BaseMapper<LocalFile> {

	IPage<FileVo> getFileList(@Param("page") Page page, @Param("localFilePath") String localFilePath,
							  @Param("param") FilePageParam param);

}
