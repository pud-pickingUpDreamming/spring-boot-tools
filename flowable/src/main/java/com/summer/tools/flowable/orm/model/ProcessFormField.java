package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单字段表
 * </p>
 *
 * @author john.wang
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProcessFormField对象", description="表单字段表")
public class ProcessFormField extends AbstractModel<ProcessFormField> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "字段key")
    private String key;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段类型")
    private String type;

    @ApiModelProperty(value = "是否必填")
    private Boolean required;

    @ApiModelProperty(value = "表单id")
    @TableField("form_id")
    private String formId;

    @ApiModelProperty(value = "占位符")
    private String placeholder;


    public static final String ID = "id";

    public static final String KEY = "key";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String REQUIRED = "required";

    public static final String FORM_ID = "form_id";

    public static final String PLACEHOLDER = "placeholder";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
