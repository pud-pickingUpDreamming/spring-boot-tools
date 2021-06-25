package com.summer.tools.mybatisplus.service.impl;

import com.summer.tools.mybatisplus.orm.model.ProcessTemplate;
import com.summer.tools.mybatisplus.orm.dao.ProcessTemplateMapper;
import com.summer.tools.mybatisplus.service.IProcessTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程模板表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements IProcessTemplateService {

}
