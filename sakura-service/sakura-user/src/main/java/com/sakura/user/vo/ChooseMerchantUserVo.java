package com.sakura.user.vo;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * admin用户表
 *
 * @author Sakura
 * @since 2023-11-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "登录商户用户信息")
public class ChooseMerchantUserVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("登录key")
    private String key;

    @ApiModelProperty("商户名称（用户选择要登录的商户，只有一个时直接默认自动选择登录无需操作）")
    private String merchantName;


}
