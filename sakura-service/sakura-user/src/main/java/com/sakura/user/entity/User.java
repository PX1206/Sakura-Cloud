package com.sakura.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.sakura.common.base.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.sakura.common.api.Update;

/**
 * 用户表
 *
 * @author Sakura
 * @since 2023-08-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@ApiModel(value = "User对象")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "自增ID不能为空")
    @ApiModelProperty("自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别：1男 2女")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("创建日期")
    private Date createDt;

    @ApiModelProperty("修改日期")
    private Date updateDt;

    @ApiModelProperty("状态：1正常 0删除 2冻结")
    private Integer status;

}
