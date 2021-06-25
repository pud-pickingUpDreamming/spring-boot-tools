package com.summer.tools.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class OperationLog {
    private String module;
    private String function;
    private String url;
    private String params;
    private String result;
    private String cost;
    private String creator;
    private Integer creatorId;
    private String ipAddr;
    private String location;
    private LocalDateTime createTime;
}
