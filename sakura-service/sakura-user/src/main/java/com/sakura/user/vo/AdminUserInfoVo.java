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
import java.util.List;

/**
 * admin用户表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AdminUser对象")
public class AdminUserInfoVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别：1男 2女")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("角色")
    private List<AdminUserRoleVo> roles;

    @ApiModelProperty("创建日期")
    private Date createDt;

    @ApiModelProperty("状态：0注销 1正常  2冻结 3临时冻结")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer status;

}
