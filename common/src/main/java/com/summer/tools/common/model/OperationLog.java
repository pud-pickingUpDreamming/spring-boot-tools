package com.summer.tools.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OperationLog {
    private String module;
    private String function;
    private String requestArgs;
    private String response;
    private String cost;
    private String userId;
    private String userName;
    private String ipAddr;
    private String location;
    private String time;
}
