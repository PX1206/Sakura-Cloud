package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商户表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "入驻申请参数")
public class ApplySettled extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户名称")
    @NotBlank(message = "商户名称")
    private String merchantNo;

    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("手机号（该手机号为超级管理员账号），前端从getSalt()接口获取盐使用AES加密传输")
    @NotBlank(message = "手机号不能为空")
    //@Pattern(regexp = "^$|^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(16[0-8])|(147))\\d{8}$", message = "手机号码格式错误")
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
