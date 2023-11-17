package com.sakura.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.common.api.Update;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 权限表
 *
 * @author Sakura
 * @since 2023-08-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "权限信息")
public class PermissionVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {Update.class})
    @ApiModelProperty("权限ID")
    private Integer id;

    @ApiModelProperty("父ID，顶层为0")
    private Integer parentId;

    @ApiModelProperty("权限code")
    private String code;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("类型：菜单menu 按钮button")
    private String type;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("权限归类：0公共 1客户 2商户 3admin 默认0")
    private Integer classify;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDt;

}
