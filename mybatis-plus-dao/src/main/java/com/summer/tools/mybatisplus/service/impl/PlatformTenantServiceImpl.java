package com.summer.tools.mybatisplus.service.impl;

import com.summer.tools.mybatisplus.orm.model.PlatformTenant;
import com.summer.tools.mybatisplus.orm.dao.PlatformTenantMapper;
import com.summer.tools.mybatisplus.service.IPlatformTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台租户信息表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@Service
public class PlatformTenantServiceImpl extends ServiceImpl<PlatformTenantMapper, PlatformTenant> implements IPlatformTenantService {

}
