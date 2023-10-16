package com.sakura.user.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.sakura.common.pagination.BasePageOrderParam;

/**
 * <pre>
 * admin角色表 分页参数对象
 * </pre>
 *
 * @author Sakura
 * @date 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "admin角色表分页参数")
public class AdminRolePageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
