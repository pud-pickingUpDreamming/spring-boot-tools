package com.summer.tools.common.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "swagger测试接口入参")
public class RequestParams {
    @ApiModelProperty(value = "键")
    private String key;
    @ApiModelProperty(value = "值")
    private String value;
}
