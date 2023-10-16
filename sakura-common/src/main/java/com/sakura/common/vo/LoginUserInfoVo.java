package com.sakura.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("登录用户详细信息")
public class LoginUserInfoVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("类型：1普通用户 2mch用户 3admin用户 只做后端认证使用")
    //@JsonIgnore // 该属性不返回给前端
    private Integer type;

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
    private String birthday;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("用户头像")
    private String headImg;

    @ApiModelProperty("角色，允许多个")
    private List<RoleVo> roles;

    @ApiModelProperty("权限code")
    private Set<String> permissions;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDt;

    @ApiModelProperty("状态：0注销 1正常 2冻结 3临时冻结")
    private Integer status;

    @ApiModelProperty("登录认证token, 请求时请在Header配置成：Access-Token")
    private String token;

}
