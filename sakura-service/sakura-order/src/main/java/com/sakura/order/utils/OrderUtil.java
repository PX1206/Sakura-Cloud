package com.sakura.order.utils;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Sakura
 * @date 2023/11/13 10:11
 */
@Component
@Log
public class OrderUtil {

    @Async
    public void Ayn() {
        log.info("异步线程测试++++++++++++++++++++++++++++");
    }
}
