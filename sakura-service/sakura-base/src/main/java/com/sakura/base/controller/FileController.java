package com.sakura.base.controller;

import com.sakura.base.param.FilePageParam;
import com.sakura.base.service.FileService;
import com.sakura.base.vo.FileVo;
import com.sakura.common.api.ApiResult;
import com.sakura.common.log.Module;
import com.sakura.common.pagination.Paging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件表 控制器
 *
 * @author Sakura
 * @since 2022-08-22
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Module("base")
@Api(value = "文件管理", tags = {"文件管理"})
public class FileController {

    @Autowired
    private FileService fileService;


    /**
     * 删除文件表
     */
    @PostMapping("/delete/{code}")
    @ApiOperation(value = "删除文件", response = ApiResult.class)
    public ApiResult<Boolean> deleteFile(@PathVariable("code") String code) throws Exception {
        boolean flag = fileService.deleteFile(code);
        return ApiResult.result(flag);
    }

    /**
     * 文件表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "文件分页列表", response = FileVo.class)
    public ApiResult<Paging<FileVo>> getFilePageList(@Validated @RequestBody FilePageParam filePageParam) throws Exception {
        Paging<FileVo> paging = fileService.getFilePageList(filePageParam);
        return ApiResult.ok(paging);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", response = ApiResult.class)
    public ApiResult<String> upload(@RequestPart("file") MultipartFile file) throws Exception {
        String path = fileService.upload(file);
        return ApiResult.ok(path);
    }

    /**
     * 下载文件
     */
    @GetMapping("/{code}")
    @ApiOperation(value = "下载")
    public void download(HttpServletResponse response, @PathVariable("code") String code) throws Exception {
        fileService.download(response, code);
    }

}

