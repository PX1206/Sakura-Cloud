package com.sakura.common.constant;

/**
 * @author Sakura
 * @date 2023/8/12 16:40
 */
public interface RocketMqConstant {

    /**
     * 扣减库存主体
     */
    String REDUCE_STOCK_TOPIC = "reduce_stock_topic";

    /**
     * 扣减库存消费组
     */
    String REDUCE_STOCK_CONSUMER_GROUP = "reduce_stock_consumer_group";

    /**
     * 扣减库存主体
     */
    String SAVE_OPERATION_LOG_TOPIC = "save_operation_log_topic";

    /**
     * 扣减库存消费组
     */
    String SAVE_OPERATION_LOG_CONSUMER_GROUP = "save_operation_log_consumer_group";

}
