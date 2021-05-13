package com.summer.tools.flowable.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.summer.tools.common.constants.CommonConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class SelfObjectHandler implements MetaObjectHandler {
    @Resource
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        String userName = request.getHeader(CommonConstants.CURRENT_USER_NAME);
        this.strictInsertFill(metaObject, CommonConstants.CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonConstants.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonConstants.CREATOR, String.class, userName);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userName = request.getHeader(CommonConstants.CURRENT_USER_NAME);
        this.strictUpdateFill(metaObject, CommonConstants.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonConstants.UPDATER, String.class, userName);
    }
}
