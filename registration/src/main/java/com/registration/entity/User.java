package com.registration.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ljx
 * @since 2022-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "学号（教工为空）")
    private String number;

    @ApiModelProperty(value = "密码（备用）")
    private String password;

    @ApiModelProperty(value = "类型（0是学生，1是老师）")
    private Integer type;

    @ApiModelProperty(value = "性别（微信提供）")
    private String gender;

    @ApiModelProperty(value = "头像")
    private String portrait;

    @ApiModelProperty(value = "背景")
    private String background;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    private String openId;

    private String wxUnionId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
