package com.summer.tools.flowable.service.impl;

import com.summer.tools.flowable.orm.model.ProcessFormField;
import com.summer.tools.flowable.orm.dao.ProcessFormFieldMapper;
import com.summer.tools.flowable.service.IProcessFormFieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 表单字段表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-08-19
 */
@Service
public class ProcessFormFieldServiceImpl extends ServiceImpl<ProcessFormFieldMapper, ProcessFormField> implements IProcessFormFieldService {

}
