package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * admin用户表
 *
 * @author Sakura
 * @since 2023-10-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增商户用户参数")
public class AddMerchantUserParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("手机号，前端从getSalt()接口获取盐使用AES加密传输")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty("getSalt()接口获取的saltKey值")
    @NotBlank(message = "saltKey不能为空")
    private String saltKey;

}
