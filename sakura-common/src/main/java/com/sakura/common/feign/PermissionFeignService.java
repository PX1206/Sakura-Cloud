package com.sakura.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * @description: Permission服务接口
 * @author: Sakura
 * @date: 2023/8/19 14:38
 */
@FeignClient(name = "user-service", path = "/feign/permission")
public interface PermissionFeignService {

    @PostMapping("/getCode")
    Set<String> getCodeByUrl(@RequestBody String strJson);
}
