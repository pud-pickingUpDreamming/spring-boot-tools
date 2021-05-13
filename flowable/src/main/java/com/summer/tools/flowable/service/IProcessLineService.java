package com.summer.tools.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.tools.flowable.orm.model.ProcessLine;

import java.util.List;

public interface IProcessLineService extends IService<ProcessLine> {

     void saveBatch(List<ProcessLine> lineList);
}
