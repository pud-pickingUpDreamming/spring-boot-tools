package com.summer.tools.flowable.orm.model;

import com.summer.tools.flowable.constants.ProcessConstants;
import lombok.Data;

@Data
public class ProcessListener<T> {

    /**
     *  任务监听类型
     */
    public ProcessConstants.ProcessListenerTypeEnum type;

    /**
     *  监听器实现类
     */
    public String implementationType = "class";

    /**
     *  监听器默认类型
     */
    public  String implementation ;

}
