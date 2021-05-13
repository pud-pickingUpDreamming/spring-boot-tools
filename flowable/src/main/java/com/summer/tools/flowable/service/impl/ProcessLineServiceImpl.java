package com.summer.tools.flowable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.tools.flowable.orm.dao.IProcessLineMapper;
import com.summer.tools.flowable.orm.model.ProcessLine;
import com.summer.tools.flowable.service.IProcessLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProcessLineServiceImpl extends ServiceImpl<IProcessLineMapper, ProcessLine> implements IProcessLineService {

    @Override
    public void saveBatch(List<ProcessLine> lineList) {
        super.saveBatch(lineList);
    }
}
