package com.sakura.user.param;

import com.sakura.common.api.Update;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色表
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("角色ID，修改时必传")
    private Integer id;

    @ApiModelProperty("角色code")
    @NotBlank(message = "角色code不能为空")
    private String code;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("描述")
    private String description;

}
