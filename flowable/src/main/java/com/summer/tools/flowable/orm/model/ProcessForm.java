package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单表
 * </p>
 *
 * @author john.wang
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProcessForm对象", description="表单表")
public class ProcessForm extends AbstractModel<ProcessForm> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表单id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "表单key")
    private String key;

    @ApiModelProperty(value = "表单名称")
    private String name;

    @ApiModelProperty(value = "表单内容")
    private String content;


    public static final String ID = "id";

    public static final String KEY = "key";

    public static final String NAME = "name";

    public static final String CONTENT = "content";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
