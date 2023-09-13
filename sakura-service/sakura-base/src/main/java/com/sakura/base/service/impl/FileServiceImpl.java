package com.sakura.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.base.entity.LocalFile;
import com.sakura.base.mapper.LocalFileMapper;
import com.sakura.base.param.FilePageParam;
import com.sakura.base.service.FileService;
import com.sakura.base.vo.FileVo;
import com.sakura.common.exception.BusinessException;
import com.sakura.common.pagination.Paging;
import com.sakura.common.tool.DateUtil;
import com.sakura.common.tool.FileTypeTool;
import com.sakura.common.tool.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件表 服务实现类
 *
 * 这里有个问题：
 * 当文件服务部署在不同的地方导致资源存放位置不一致
 * 请求http://localhost:8000/api-base/file/6FWBHNJhFyttppRPxsLruwH4rPZ5YcAS时会出提示不到文件
 * 需要自己处理跨服务器文件共享问题
 *
 * @author Sakura
 * @since 2022-08-22
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${local.host}")
    String LOCAL_HOST;

    // 文件存放路径跟jar包同目录下/resources/files/
    private static final String LOCAL_FILE_PATH = "./resources/files/";

    @Autowired
    private LocalFileMapper localFileMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteFile(String code) throws Exception {
        LambdaQueryWrapper<LocalFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LocalFile::getCode, code);
        wrapper.eq(LocalFile::getStatus, 1);
        LocalFile localFile = localFileMapper.selectOne(wrapper);
        if (localFile == null || (!LoginUtil.getUserId().equals(localFile.getUserId()) && LoginUtil.getUserType() != 3)) {
            throw new BusinessException(500, "文件不存在");
        }

        localFile.setStatus(0);
        localFileMapper.updateById(localFile);

        // 删除文件信息 目前不做物理删除
        // ZipUtil.deleteFileDirectory(new File(LOCAL_FILE_PATH + localFile.getPath() + "/" + localFile.getName() + localFile.getSuffix()));

        return true;
    }

    @Override
    public Paging<FileVo> getFilePageList(FilePageParam filePageParam) throws Exception {
        Page<FileVo> page = new Page<>(filePageParam.getPageIndex(), filePageParam.getPageSize());
        IPage<FileVo> iPage = localFileMapper.getFileList(page, filePageParam);
        // 组装URL
        iPage.getRecords().stream().forEach(fileVo -> fileVo.setUrl(LOCAL_HOST + "file/" + fileVo.getCode()));
        return new Paging<FileVo>(iPage);
    }

    @Override
    public String upload(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(500, "上传文件为空");
        }
        LocalFile localFile = new LocalFile();
        // 保存原文件名称，文件列表展示需要用到
        localFile.setName(multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
        // 生成唯一编码code
        String code = RandomStringUtils.randomAlphanumeric(32);
        localFile.setCode(code);
        // 保存原文件后缀
        localFile.setSuffix(multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase());

        // 判断是否是图片，如果是图片则添加meta，这样点击链接就会预览而不是下载
        int fileType = FileTypeTool.fileType(multipartFile.getOriginalFilename());
        localFile.setType(fileType);
        localFile.setDomain(LOCAL_HOST);
        localFile.setPath(DateUtil.today());
        localFile.setSize((int) multipartFile.getSize());
        localFile.setSource(LoginUtil.getUserType());
        localFile.setUserId(LoginUtil.getUserId());
        localFile.setStatus(1);
        localFileMapper.insert(localFile);

        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
        File targetFile = new File(LOCAL_FILE_PATH + localFile.getPath()
                + "/" + localFile.getCode() + localFile.getSuffix());
        // 保证这个文件的父文件夹必须要存在
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        targetFile.createNewFile();

        // 将文件内容写入到这个文件中
        InputStream is = multipartFile.getInputStream();
        FileOutputStream fos = new FileOutputStream(targetFile);
        try {
            int len;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        } finally {
            // 关流顺序，先打开的后关闭
            fos.close();
            is.close();
        }

        return LOCAL_HOST + "file/" + localFile.getCode();
    }

    @Override
    public void download(HttpServletResponse response, String code) throws Exception {
        LambdaQueryWrapper<LocalFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LocalFile::getCode, code);
        wrapper.eq(LocalFile::getStatus, 1);
        LocalFile localFile = localFileMapper.selectOne(wrapper);
        if (localFile == null) {
            throw new BusinessException(500, "文件不存在");
        }

        File downloadFile = new File(LOCAL_FILE_PATH + localFile.getPath() + "/" + localFile.getCode() + localFile.getSuffix());
        if (!downloadFile.exists() || downloadFile.length() == 0) {
            throw new BusinessException(500, "文件不存在");
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            if (localFile.getType() == 1) {
                response.setContentType("image/jpeg");
            }
            response.addHeader("Content-Length", "" + downloadFile.length());
            is = new FileInputStream(downloadFile);
            os = response.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {
            log.error("下载图片发生异常", e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("关闭流发生异常", e);
            }
        }
    }

}
