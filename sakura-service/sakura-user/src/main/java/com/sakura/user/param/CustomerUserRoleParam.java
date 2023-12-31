package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户角色表
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户用户角色参数")
public class CustomerUserRoleParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

}
