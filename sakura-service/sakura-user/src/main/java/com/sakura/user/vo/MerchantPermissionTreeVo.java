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
import java.util.List;

/**
 * 权限表
 *
 * @author Sakura
 * @since 2023-11-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户权限树信息")
public class MerchantPermissionTreeVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限ID")
    private Integer id;

    @ApiModelProperty("父ID，顶层为0")
    private Integer parentId;

    @ApiModelProperty("权限code")
    private String code;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("子权限")
    private List<MerchantPermissionTreeVo> childList;

}
