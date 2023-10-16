package com.sakura.user.param;

import com.sakura.common.api.Update;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限表
 *
 * @author Sakura
 * @since 2023-08-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "权限参数")
public class PermissionParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("父ID，顶层为0")
    @NotNull(message = "权限父ID不能为空")
    private Integer parentId;

    @ApiModelProperty("权限code")
    @NotBlank(message = "权限code不能为空")
    private String code;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名称不能为空")
    private String name;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("类型：菜单menu 按钮button")
    private String type;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("权限归类：0公共 1客户 2商户 3admin 默认0")
    @NotNull(message = "权限归类不能为空")
    private Integer classify;

}
