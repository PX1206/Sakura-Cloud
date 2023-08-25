package com.sakura.user.scheduled;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.user.entity.User;
import com.sakura.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Sakura
 * @date 2023/8/25 11:24
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {

//    每隔1分钟执行一次：0 */1 * * * ?
//    每天22点执行一次：0 0 22 * * ?
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//    每月最后一天23点执行一次：0 0 23 L * ?
//    每周周六凌晨3点实行一次：0 0 3 ? * L
//    在24分、30分执行一次：0 24,30 * * * ?

    @Autowired
    private UserMapper userMapper;

    /**
     * @description: 每天凌晨解冻临时冻结账号
     * @author: Sakura
     * @date: 2023/8/25 11:27
     */
    @Scheduled(cron = "0 0 0 * * ?") // 0 0 0 * * ? 每天0点启动
    public void unfreezeAccount() {
        log.info("定时解冻临时冻结账号程序启动——————————————————————————————————————————");
        try {
            userMapper.update(new User().setStatus(1), Wrappers.<User>lambdaUpdate().eq(User::getStatus, 3));
        } catch (Exception e) {
            log.error("定时解冻临时冻结账号异常", e);
        }
    }
}
