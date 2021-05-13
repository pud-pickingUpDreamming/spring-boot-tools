package com.summer.tools.flowable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.tools.flowable.constants.ProcessConstants;
import com.summer.tools.flowable.orm.dao.IProcessNodeMapper;
import com.summer.tools.flowable.orm.model.ProcessLine;
import com.summer.tools.flowable.orm.model.ProcessNode;
import com.summer.tools.flowable.service.IProcessLineService;
import com.summer.tools.flowable.service.IProcessNodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ProcessNodeServiceImpl extends ServiceImpl<IProcessNodeMapper, ProcessNode> implements IProcessNodeService {

    @Resource
    private IProcessLineService lineService;

    @Override
    public void saveBatch(List<ProcessNode> nodeList) {
        super.saveBatch(nodeList);

        this.fillExtraTableField(nodeList);
    }

    private void fillExtraTableField(List<ProcessNode> nodeList) {
        nodeList.forEach(node -> {
            String listeners = node.getListeners();
            if (StringUtils.isNotBlank(listeners)) {
                List<ProcessConstants.ProcessListenerTypeEnum> listenerList =
                        ProcessConstants.ProcessListenerTypeEnum.getTypeEnums(listeners.split(","));
                node.setListenerList(listenerList);
            }

            String lines = node.getLines();
            List<ProcessLine> lineList = this.lineService.list(new QueryWrapper<ProcessLine>()
                    .in(StringUtils.isNotBlank(lines), "id", Arrays.asList(lines.split(","))));

            node.setTypeEnum(ProcessConstants.ProcessNodeTypeEnum.getTypeEnum(node.getType()))
                    .setLineList(lineList);
        });
    }


}
