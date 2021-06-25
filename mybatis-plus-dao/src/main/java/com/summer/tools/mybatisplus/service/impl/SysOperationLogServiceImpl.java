package com.summer.tools.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.summer.tools.common.model.OperationLog;
import com.summer.tools.common.services.IOperationLogService;
import com.summer.tools.common.utils.BeanUtil;
import com.summer.tools.mybatisplus.orm.model.SysOperationLog;
import com.summer.tools.mybatisplus.orm.dao.SysOperationLogMapper;
import com.summer.tools.mybatisplus.service.ISysOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统操作日志表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@DS("pgSql")
@Primary
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService, IOperationLogService {

    @Override
    public void saveLog(OperationLog operationLog) {
        SysOperationLog log = new SysOperationLog();
        BeanUtil.copyProperties(operationLog, log, null);
        log.insert();
    }
}
