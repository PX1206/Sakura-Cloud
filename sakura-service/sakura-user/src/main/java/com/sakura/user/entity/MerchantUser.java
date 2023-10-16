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
 * 商户用户表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_merchant_user")
@ApiModel(value = "MerchantUser对象")
public class MerchantUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户号")
    private String merchantNo;

    @NotBlank(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别：1男 2女")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("超级管理员：1是 0否")
    private Integer superuser;

    @ApiModelProperty("创建日期")
    private Date createDt;

    @ApiModelProperty("修改日期")
    private Date updateDt;

    @ApiModelProperty("状态：0注销 1正常  2冻结 3临时冻结")
    private Integer status;

}
