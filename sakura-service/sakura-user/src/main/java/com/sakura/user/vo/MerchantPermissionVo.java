package com.sakura.user.vo;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户权限表
 *
 * @author Sakura
 * @since 2023-11-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户权限信息")
public class MerchantPermissionVo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("类型为1的是基础权限，基础权限无法调整")
    private Integer type;

    @ApiModelProperty("权限id")
    private Integer permissionId;

}
