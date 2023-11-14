package com.sakura.user.param;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Sakura
 * @since 2023-11-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "选择登录商户参数")
public class ChooseMerchantParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("key，登录接口返回")
    private String key;

}
