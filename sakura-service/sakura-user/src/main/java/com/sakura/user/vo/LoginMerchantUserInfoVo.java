package com.sakura.user.vo;

import com.sakura.common.vo.LoginUserInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("登录用户详细信息")
public class LoginMerchantUserInfoVo extends LoginUserInfoVo {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("商户号")
    private String merchantNo;

}
