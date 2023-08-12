package com.sakura.stock.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.sakura.common.pagination.BasePageOrderParam;

/**
 * <pre>
 * 库存表 分页参数对象
 * </pre>
 *
 * @author Sakura
 * @date 2023-08-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "库存表分页参数")
public class StockPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
