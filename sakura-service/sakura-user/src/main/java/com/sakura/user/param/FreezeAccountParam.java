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
 * @since 2023-08-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "账号冻结参数")
public class FreezeAccountParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("key")
    @NotBlank(message = "图片验证码key不能为空")
    private String key;

    @ApiModelProperty("图片验证码")
    @NotBlank(message = "图片验证码不能为空")
    private String pictureCode;

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户ID不能为空")
    private String userId;

}
