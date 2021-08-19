package com.summer.tools.flowable.service.impl;

import com.summer.tools.flowable.orm.model.ProcessForm;
import com.summer.tools.flowable.orm.dao.ProcessFormMapper;
import com.summer.tools.flowable.service.IProcessFormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 表单表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-08-19
 */
@Service
public class ProcessFormServiceImpl extends ServiceImpl<ProcessFormMapper, ProcessForm> implements IProcessFormService {

}
