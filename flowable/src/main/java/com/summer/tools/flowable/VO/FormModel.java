package com.summer.tools.flowable.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value="表单模型")
public class FormModel {

    @ApiModelProperty(value = "formKey")
    private String key;

    @ApiModelProperty(value = "表单名称")
    private String name;

    @ApiModelProperty(value = "字段类型")
    private FieldModel[] fields;
}
