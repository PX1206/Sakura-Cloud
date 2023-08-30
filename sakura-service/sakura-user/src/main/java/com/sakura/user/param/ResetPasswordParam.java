package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 重置密码
 *
 * @author Sakura
 * @since 2023-08-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "重置密码参数")
public class ResetPasswordParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号，前端从getSalt()接口获取盐使用AES加密传输")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty("短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @ApiModelProperty("getSalt()接口获取的saltKey值")
    @NotBlank(message = "saltKey不能为空")
    private String saltKey;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空，密码必须由字母和数字组成且不得少于8位，前端从getSalt()接口获取盐使用AES加密传输")
    private String password;

}
