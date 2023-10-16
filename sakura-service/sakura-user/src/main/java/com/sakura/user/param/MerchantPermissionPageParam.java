package com.sakura.user.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.sakura.common.pagination.BasePageOrderParam;

/**
 * <pre>
 * 商户权限表 分页参数对象
 * </pre>
 *
 * @author Sakura
 * @date 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户权限表分页参数")
public class MerchantPermissionPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
