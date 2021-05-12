package com.summer.tools.flowable.orm.model;

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
public class DeployModel {
    /**
     * 流程模板id
     */
    private String processId;
    private String processName;
    private List<ProcessNode> processNodes;
    private List<ProcessLine> processLines;
}
