package com.sakura.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakura.common.api.Update;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商户表
 *
 * @author Sakura
 * @since 2023-09-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_merchant")
@ApiModel(value = "商户信息")
public class MerchantVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户号")
    private String merchantNo;

    @ApiModelProperty("父商户号，顶层商户为0")
    private String parentNo;

    @ApiModelProperty("商户类型id")
    private Integer typeId;

    @ApiModelProperty("商户名称")
    private String name;

    @ApiModelProperty("统一信用代码/营业执照号")
    private String unifiedCreditCode;

    @ApiModelProperty("营业执照有效期开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectiveStartDt;

    @ApiModelProperty("营业执照有效期结束日期")
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

    @ApiModelProperty("审核状态：1待提交 2待审核 3审核通过 4审核不通过")
    private Integer checkStatus;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDt;

}
