package com.summer.tools.mybatisplus.orm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.summer.tools.mybatisplus.orm.model.AbstractModel;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流程模板表
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProcessTemplate对象", description="流程模板表")
public class ProcessTemplate extends AbstractModel<ProcessTemplate> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程模板id")
    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    private String templateId;

    @ApiModelProperty(value = "流程模板名称")
    private String name;

    @ApiModelProperty(value = "流程模板类型 1:审批流程")
    private Integer type;

    @ApiModelProperty(value = "流程描述")
    private String description;

    @ApiModelProperty(value = "流程xml内容")
    private String xmlData;

    @ApiModelProperty(value = "1: 草稿 2: 发布 3: 删除")
    private Integer status;


    public static final String TEMPLATE_ID = "template_id";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String DESCRIPTION = "description";

    public static final String XML_DATA = "xml_data";

    public static final String STATUS = "status";

    @Override
    protected Serializable pkVal() {
        return this.templateId;
    }

}
