package com.summer.tools.flowable.VO;

import com.summer.tools.flowable.orm.model.ProcessForm;
import com.summer.tools.flowable.orm.model.ProcessLine;
import com.summer.tools.flowable.orm.model.ProcessNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部署对象
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("部署模型")
public class DeployModel {
    @ApiModelProperty("模板id")
    private String templateId;
    @ApiModelProperty("流程模板名称")
    private String processName;
    @ApiModelProperty("表单信息")
    private FormModel form;
    @ApiModelProperty("流程模板节点信息")
    private List<ProcessNode> processNodes;
    @ApiModelProperty("流程节点线段信息")
    private List<ProcessLine> processLines;
}
