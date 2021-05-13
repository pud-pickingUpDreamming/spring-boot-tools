package com.summer.tools.flowable.service;

import com.summer.tools.flowable.orm.model.ProcessNode;

import java.util.List;

public interface IProcessNodeService {

    void saveBatch(List<ProcessNode> nodeList);
}
