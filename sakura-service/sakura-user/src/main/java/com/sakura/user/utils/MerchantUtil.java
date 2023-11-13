package com.sakura.user.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sakura.common.tool.LoginUtil;
import com.sakura.user.entity.Merchant;
import com.sakura.user.mapper.MerchantMapper;
import com.sakura.user.mapper.MerchantUserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sakura
 * @date 2023/11/10 17:19
 */
@Component
@Log
public class MerchantUtil {

    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private MerchantUserMapper merchantUserMapper;


    // 生成商户号
    public String createMerchantNo() {
        // 88 + 年月日 + 随机数生成
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String merchantNo = "88" + simpleDateFormat.format(new Date()) + (int)((Math.random() * 9 + 1) * 10000000);

        // 判断数据库是否存在该商户号
        // 也可以不加这个，毕竟随机数重复的概率极小
        while (true) {
            Merchant merchant = merchantMapper.selectOne(
                    Wrappers.<Merchant>lambdaQuery()
                            .eq(Merchant::getMerchantNo, merchantNo)
            );
            if (merchant == null) {
                break;
            }
        }
        return merchantNo;

    }

    @Async
    public void deleteMerchant(String merchantNo) {
        log.info("开始删除商户数据+++++++++++++" + merchantNo);
        try {
            // 获取所有的有效员工userID
            List<String> userIds = merchantUserMapper.findMerchantUserId(merchantNo);
            // 删除所有的员工账号
            merchantUserMapper.deleteMerchantUserByMerchantNo(merchantNo);
            // 退出所有员工账号登录
            userIds.forEach(LoginUtil::logoutAll);
            log.info("删除商户数据成功+++++++++++++" + merchantNo);
        } catch (Exception e) {
            log.info("删除商户出现异常+++++++++++++" + merchantNo);
            e.printStackTrace();
        }
        log.info("删除商户数据完成+++++++++++++" + merchantNo);
    }

}
