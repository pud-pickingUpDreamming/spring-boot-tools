package com.summer.tools.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.model.SimplePage;
import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.mybatisplus.orm.dao.PlatformTenantMapper;
import com.summer.tools.mybatisplus.orm.model.PlatformDbResource;
import com.summer.tools.mybatisplus.orm.model.PlatformTenant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 平台租户信息表 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@RestController
@RequestMapping("/platformTenant")
@Api(tags = "租户管理")
public class PlatformTenantController {

    @Resource
    private PlatformTenantMapper tenantMapper;

    @BackendOperation(module = "租户管理", function = "添加租户管理")
    @ApiOperation("添加租户管理")
    @PostMapping
    public ResponseResult<Boolean> addDbResource(@RequestBody PlatformTenant tenant) {
        return ResponseResult.success(tenant.insert());
    }


    @ApiOperation("分页查询租户")
    @GetMapping("/list")
    public ResponseResult<SimplePage<PlatformTenant>> list(@ApiParam("租户名称,英文") @RequestParam(required = false) String name,
                                                           @ApiParam("当前页数") @RequestParam(required = false) Long currentPage,
                                                           @ApiParam("每页大小") @RequestParam(required = false) Long size) {

        IPage<PlatformTenant> page = this.tenantMapper.selectPage(new Page<>(currentPage, size),
                new QueryWrapper<PlatformTenant>().like(name != null, PlatformTenant.NAME, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }

}

