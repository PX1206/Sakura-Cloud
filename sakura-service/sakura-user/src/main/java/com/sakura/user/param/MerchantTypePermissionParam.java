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
 * 商户类型权限
 *
 * @author Sakura
 * @since 2023-11-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户类型权限参数")
public class MerchantTypePermissionParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户类型id")
    @NotNull(message = "商户类型ID不能为空")
    private Integer typeId;

    @ApiModelProperty("权限id，为空则表示清空权限")
    //@NotEmpty(message = "权限ID不能为空")
    private Set<Integer> permissionIds;

}
