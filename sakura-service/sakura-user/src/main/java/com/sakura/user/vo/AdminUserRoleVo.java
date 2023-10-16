package com.sakura.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 角色表
 *
 * @author Sakura
 * @since 2023-08-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "admin角色信息")
public class AdminUserRoleVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDt;

}
