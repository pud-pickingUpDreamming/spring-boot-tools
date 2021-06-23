package com.summer.tools.mybatisplus.service.impl;

import com.summer.tools.mybatisplus.orm.model.PlatformUser;
import com.summer.tools.mybatisplus.orm.dao.PlatformUserMapper;
import com.summer.tools.mybatisplus.service.IPlatformUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台用户，用于登录系统 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@Service
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserMapper, PlatformUser> implements IPlatformUserService {

}
