package com.registration.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value="Record对象", description="")
public class RecordVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "签到记录id")
    private Integer id;

    @ApiModelProperty(value = "学生学号")
    private String studentNumber;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "第几次点名")
    private Integer time;

    @ApiModelProperty(value = "类别（0是未签到，1是已签到，2是缺课，3是请假）")
    private Integer type;

    @ApiModelProperty(value = "位置信息")
    private String location;



    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
