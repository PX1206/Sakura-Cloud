package com.sakura.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.sakura.common.base.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.sakura.common.api.Update;

/**
 * 客户角色权限表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_customer_role_permission")
@ApiModel(value = "CustomerRolePermission对象")
public class CustomerRolePermission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("权限id")
    private Integer permissionId;

    @ApiModelProperty("创建日期")
    private Date createDt;

    @ApiModelProperty("修改日期")
    private Date updateDt;

    @ApiModelProperty("状态：1正常 0删除")
    private Integer status;

}
