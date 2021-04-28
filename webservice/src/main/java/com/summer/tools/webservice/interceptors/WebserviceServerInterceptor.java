package com.summer.tools.webservice.interceptors;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;

@Slf4j
@Component
public class WebserviceServerInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    // 接收时处理
    public WebserviceServerInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Header header = message.getHeader(new QName("authority"));
        if(null == header || null == header.getObject()){
            throw new BusinessException(ResponseCodeEnum.TOKEN_ERROR);
        }
    }
}

