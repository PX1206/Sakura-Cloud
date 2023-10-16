package com.sakura.order.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.sakura.common.pagination.BasePageOrderParam;

/**
 * <pre>
 * 订单表 分页参数对象
 * </pre>
 *
 * @author Sakura
 * @date 2023-08-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单表分页参数")
public class OrderPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
