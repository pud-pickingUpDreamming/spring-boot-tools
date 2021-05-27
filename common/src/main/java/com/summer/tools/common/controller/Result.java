package com.summer.tools.common.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Result {
    @ApiModelProperty(value = "键")
    private String key;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "组合信息")
    private String additionValue;
}
