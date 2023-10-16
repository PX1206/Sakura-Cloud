package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 账号注销
 *
 * @author Sakura
 * @since 2023-10-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "账号注销参数")
public class AccountCancellationParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("短信验证码（当前登录用户手机号）")
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

}
