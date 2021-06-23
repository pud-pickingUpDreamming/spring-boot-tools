package com.summer.tools.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.summer.tools.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MybatisPlusObjectHandler implements MetaObjectHandler {
    /**
     * 正常请求可以取到request信息。但是如果用到了线程池就取不到request信息了,会报错(如果有必要可以弄个TheadLocal解决)
     */
    @Resource
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Integer userId = Integer.parseInt(request.getHeader(CommonConstants.CURRENT_USER_ID));
            String userName = request.getHeader(CommonConstants.CURRENT_USER_NAME);
            this.strictInsertFill(metaObject, CommonConstants.CREATOR_ID, Integer.class, userId);
            this.strictInsertFill(metaObject, CommonConstants.CREATOR, String.class, userName);
        } catch (Exception ex) {
            log.error("获取不到用户信息:", ex);
        }
        this.strictInsertFill(metaObject, CommonConstants.CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, CommonConstants.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Integer userId = Integer.parseInt(request.getHeader(CommonConstants.CURRENT_USER_ID));
            String userName = request.getHeader(CommonConstants.CURRENT_USER_NAME);
            this.strictUpdateFill(metaObject, CommonConstants.UPDATER_ID, Integer.class, userId);
            this.strictUpdateFill(metaObject, CommonConstants.UPDATER, String.class, userName);
        } catch (Exception ex) {
            log.error("获取不到用户信息:", ex);
        }
        this.strictUpdateFill(metaObject, CommonConstants.UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
    }
}
