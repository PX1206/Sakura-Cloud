package com.sakura.code.vo;

import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Sakura
 * @date 2023/8/14 14:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "图片验证码")
public class PictureCodeVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("图片验证码 Base64格式")
    private String image;
}
