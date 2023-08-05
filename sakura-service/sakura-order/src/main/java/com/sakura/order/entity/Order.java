package com.sakura.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Sakura
 * @date 2023/7/28 15:13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品编号
     */
    private String productNo;

    /**
     * 数量
     */
    private Integer num;


    /**
     * 总价格
     */
    private Integer totalPrice;

    /**
     * 创建日期
     */
    private Date createDt;

    /**
     * 修改日期
     */
    private Date updateDt;

    /**
     * 状态：1正常 0删除
     */
    private Integer status;

}
