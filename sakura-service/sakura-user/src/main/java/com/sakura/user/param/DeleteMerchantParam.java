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
 * @since 2023-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "删除商户参数")
public class DeleteMerchantParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("key")
    @NotBlank(message = "图片验证码key不能为空")
    private String key;

    @ApiModelProperty("图片验证码")
    @NotBlank(message = "图片验证码不能为空")
    private String pictureCode;


    @ApiModelProperty("商户号 删除后不可恢复谨慎操作")
    @NotBlank(message = "商户号不能为空")
    private String merchantNo;

}
