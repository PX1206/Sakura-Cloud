package com.sakura.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sakura.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 文件表
 *
 * @author Sakura
 * @since 2022-08-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_file")
@ApiModel(value = "File对象")
public class LocalFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id,自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文件编码，随机生成")
    private String code;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型 1.图片 2.文档 3.视频 4.音频 5.其它")
    private Integer type;

    @ApiModelProperty("前缀")
    private String domain;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("来源：用户 2商家 3管理")
    private Integer source;

    @ApiModelProperty("文件大小 单位Kb")
    private Integer size;

    @ApiModelProperty("提交人")
    private String userId;

    @ApiModelProperty("创建时间")
    private Date createDt;

    @ApiModelProperty("0.失效 1.有效")
    private Integer status;

}
