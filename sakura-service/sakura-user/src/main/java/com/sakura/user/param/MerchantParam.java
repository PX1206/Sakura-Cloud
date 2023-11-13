package com.sakura.user.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 商户表
 *
 * @author Sakura
 * @since 2023-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户信息参数")
public class MerchantParam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户名称")
    @NotBlank(message = "商户名称不能为空")
    private String name;

    @ApiModelProperty("统一信用代码/营业执照号")
    private String unifiedCreditCode;

    @ApiModelProperty("营业执照有效期开始日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectiveStartDt;

    @ApiModelProperty("营业执照有效期结束日期 yyyy-MM-dd20")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectiveEndDt;

    @ApiModelProperty("法人")
    private String legalPerson;

    @ApiModelProperty("法人证件号")
    private String legalPersonNo;

    @ApiModelProperty("经营地址")
    private String businessAddress;

    @ApiModelProperty("注册资本")
    private String registeredCapital;

    @ApiModelProperty("经营范围")
    private String businessScope;

}
