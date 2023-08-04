package com.sakura.order.param;

import com.sakura.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单详情表
 *
 * @author Sakura
 * @since 2022-08-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AddOrderParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商品编号
     */
    @NotBlank(message = "商品编号不能为空")
    private String productNo;

    /**
     * 数量
     */
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量不能小于1")
    private Integer num;

}
