package com.summer.tools.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summer.tools.common.model.SimplePage;
import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.mybatisplus.orm.dao.SysOperationLogMapper;
import com.summer.tools.mybatisplus.orm.model.SysOperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 系统操作日志表 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/sysOperationLog")
@Api(tags = "系统操作日志")
public class SysOperationLogController {

    @Resource
    private SysOperationLogMapper logMapper;

    @ApiOperation("分页查询操作日志")
    @GetMapping("/list")
    public ResponseResult<SimplePage<SysOperationLog>> list(@ApiParam("模块名称") @RequestParam(required = false) String name,
                                                            @ApiParam("当前页数") @RequestParam(required = false) Long currentPage,
                                                            @ApiParam("每页大小") @RequestParam(required = false) Long size) {

        IPage<SysOperationLog> page = this.logMapper.selectPage(new Page<>(currentPage, size),
                new QueryWrapper<SysOperationLog>().like(name != null, SysOperationLog.MODULE, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }

}

