package com.sakura.code.vo;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Sakura
 * @date 2023/8/16 14:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "加密盐")
public class SaltVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("盐key值，需要传给后端")
    private String saltKey;

    @ApiModelProperty("加密盐，使用一次后即失效")
    private String salt;
}
