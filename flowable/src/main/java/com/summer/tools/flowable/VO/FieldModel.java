package com.summer.tools.flowable.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value="表单字段表")
public class FieldModel {

    @ApiModelProperty(value = "字段id")
    private String id;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段类型")
    private String type;

    @ApiModelProperty(value = "是否必填")
    private Boolean required;

    @ApiModelProperty(value = "占位符")
    private String placeholder;
}
