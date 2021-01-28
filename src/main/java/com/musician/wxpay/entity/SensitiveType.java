package com.musician.wxpay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: LXR
 * @since: 2021/1/28 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "dic_sensitive_type")
public class SensitiveType implements Serializable {

    private static final long serialVersionUID = -1620286801340532700L;

    @TableField
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @TableField
    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @TableField
    @ApiModelProperty(value = "机构id")
    private Integer orgId;

    @TableField
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @TableField
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField
    @ApiModelProperty(value = "最新修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}