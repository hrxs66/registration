package com.registration.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "返回结果")
public class Result<T> implements Serializable {
    /**
     * 错误代码
     */
    @ApiModelProperty("状态码 200:成功")
    private Integer code;

    /**
     * 错误信息
     */
    @ApiModelProperty("提示消息")
    private String status;

    /**
     * 返回数据
     */
    @ApiModelProperty("返回数据")
    private T data;




}
