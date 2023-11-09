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
 * @since 2023-11-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "admin角色权限参数")
public class AdminRolePermissionParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty("权限id")
    @NotEmpty(message = "权限ID不能为空")
    private Set<Integer> permissionIds;

}
