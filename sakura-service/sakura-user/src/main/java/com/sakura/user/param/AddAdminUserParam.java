package com.sakura.user.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * admin用户表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增admin用户参数")
public class AddAdminUserParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("性别：1男 2女")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("生日 格式yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @ApiModelProperty("手机号，前端从getSalt()接口获取盐使用AES加密传输")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("getSalt()接口获取的saltKey值")
    @NotBlank(message = "saltKey不能为空")
    private String saltKey;

    @ApiModelProperty("用户头像，调用文件上传接口")
    private String headImg;

}
