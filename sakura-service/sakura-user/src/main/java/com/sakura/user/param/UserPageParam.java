package com.sakura.user.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.sakura.common.pagination.BasePageOrderParam;

/**
 * <pre>
 * 用户表 分页参数对象
 * </pre>
 *
 * @author Sakura
 * @date 2023-08-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户表分页参数")
public class UserPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
