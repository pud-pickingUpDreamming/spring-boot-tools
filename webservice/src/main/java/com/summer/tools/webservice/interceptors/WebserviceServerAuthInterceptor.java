package com.summer.tools.webservice.interceptors;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.utils.SignUtil;
import com.summer.tools.common.utils.Assert;
import com.summer.tools.webservice.properties.WebserviceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class WebserviceServerAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    @Resource
    private WebserviceProperties properties;

    // 接收时处理
    public WebserviceServerAuthInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = message.getHeaders();
        // 检验是否有授权信息
        Assert.notEmpty(headers, HttpStatus.BAD_REQUEST);
        // 解析获取授权元素
        Header firstHeader = headers.get(0);
        Element ele = (Element) firstHeader.getObject();
        Element tokenEle = (Element) ele.getFirstChild();
        // 获取客户端和服务端token
        String token = tokenEle.getTextContent();
        String validToken = SignUtil.signWithMd5(properties.getUsername() + properties.getPasswd());
        // 校验token,用户名、密码约定好
        Assert.equals(token, validToken, ResponseCodeEnum.TOKEN_ERROR);
    }
}

