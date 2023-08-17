package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户表
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "短信验证码参数")
public class SMSCodeParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号，前端从getSalt()接口获取盐使用AES加密传输")
    //@Pattern(regexp = "^$|^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(16[0-8])|(147))\\d{8}$", message = "手机号码格式错误")
    private String mobile;

    @ApiModelProperty("key")
    @NotBlank(message = "key不能为空")
    private String key;

    @ApiModelProperty("图片验证码")
    @NotBlank(message = "图片验证码不能为空")
    private String pictureCode;

    @ApiModelProperty("盐key值，前端从getSalt()接口获取")
    @NotBlank(message = "saltKey不能为空")
    private String saltKey;


}
