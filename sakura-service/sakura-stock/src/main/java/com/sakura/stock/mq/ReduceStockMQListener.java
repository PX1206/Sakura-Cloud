package com.sakura.stock.mq;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.constant.RocketMqConstant;
import com.sakura.stock.mapper.StockMapper;
import com.sakura.stock.param.ReduceStockParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sakura
 * @date 2023/8/12 16:48
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = RocketMqConstant.REDUCE_STOCK_TOPIC, consumerGroup = RocketMqConstant.REDUCE_STOCK_CONSUMER_GROUP)
public class ReduceStockMQListener implements RocketMQListener<ReduceStockParam> {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public void onMessage(ReduceStockParam message) {
        log.info("MQ收到扣减库存消息：" + JSONObject.toJSONString(message));
        int num = stockMapper.decreaseStock(message.getProductNo(), message.getNum());
        if (num < 1) {
            log.info("MQ扣减库存信息异常：" + JSONObject.toJSONString(message));
        }

    }
}
