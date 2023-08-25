package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 用户表
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改手机号参数")
public class UpdateMobileParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("原手机号短信验证码")
    @NotBlank(message = "原手机号短信验证码不能为空")
    private String oldSmsCode;

    @ApiModelProperty("新手机号，前端从getSalt()接口获取盐使用AES加密传输")
    @NotBlank(message = "新手机号不能为空")
    private String newMobile;

    @ApiModelProperty("新手机号短信验证码")
    @NotBlank(message = "新手机号短信验证码不能为空")
    private String newSmsCode;

    @ApiModelProperty("getSalt()接口获取的saltKey值")
    @NotBlank(message = "saltKey不能为空")
    private String saltKey;

}
