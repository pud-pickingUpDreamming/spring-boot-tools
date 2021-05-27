package com.summer.tools.common.services;

import com.summer.tools.common.model.OperationLog;

public interface IOperationLogService {
    void saveLog(OperationLog operationLog);
}
