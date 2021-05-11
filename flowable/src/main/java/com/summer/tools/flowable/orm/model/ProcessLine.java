package com.summer.tools.flowable.orm.model;

import lombok.Data;

import java.util.List;

@Data
public class ProcessLine {

    /**
     *  流程线条ID
     */
    private String lineId;

    /**
     *  流程线名称
     */
    private String name;

    /**
     *  上一个节点ID
     */
    private String fromId;

    /**
     *  下一个节点ID
     */
    private String toId;

    /**
     *  线上的公式，流转标识
     */
    private String conditionExpression;

    List<ProcessListener> listeners;

}
