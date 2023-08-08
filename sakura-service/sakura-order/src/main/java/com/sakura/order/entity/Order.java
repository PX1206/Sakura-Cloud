package com.sakura.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.sakura.common.base.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.sakura.common.api.Update;

/**
 * 订单表
 *
 * @author Sakura
 * @since 2023-08-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
@ApiModel(value = "Order对象")
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("id,自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("商品编号")
    private String productNo;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("总价格，单位为分")
    private Integer totalPrice;

    @ApiModelProperty("创建日期")
    private Date createDt;

    @ApiModelProperty("修改日期")
    private Date updateDt;

    @ApiModelProperty("状态：1正常 0删除")
    private Integer status;

}
