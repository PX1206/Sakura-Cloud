package com.sakura.user.controller.feign;

import com.sakura.common.base.BaseController;
import com.sakura.common.enums.OperationLogType;
import com.sakura.common.log.OperationLog;
import com.sakura.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 权限表 控制器
 *
 * @author Sakura
 * @since 2023-08-24
 */
@Slf4j
@RestController
@RequestMapping("/feign/permission")
public class PermissionFeignController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取当前url所需权限
     */
    @PostMapping("/getCode")
    @OperationLog(name = "获取请求链接权限code", type = OperationLogType.query)
    public Set<String> getCodeByUrl(@RequestBody String strJson) throws Exception {
        Set<String> codes = permissionService.getCodeByUrl(strJson);
        return codes;
    }

}

