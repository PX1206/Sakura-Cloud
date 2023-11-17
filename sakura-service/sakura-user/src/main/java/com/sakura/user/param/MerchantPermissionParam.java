package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 角色权限表
 *
 * @author Sakura
 * @since 2023-11-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户权限参数")
public class MerchantPermissionParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户号")
    @NotNull(message = "商户号不能为空")
    private String merchantNo;

    @ApiModelProperty("权限id")
    @NotEmpty(message = "权限ID不能为空")
    private Set<Integer> permissionIds;

}
