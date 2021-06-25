package com.summer.tools.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.model.SimplePage;
import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.mybatisplus.orm.dao.PlatformDbResourceMapper;
import com.summer.tools.mybatisplus.orm.model.PlatformDbResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 数据库资源表 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@RestController
@RequestMapping("/platformDbResource")
@Api(tags = "数据源管理")
public class PlatformDbResourceController {

    @Resource
    private PlatformDbResourceMapper resourceMapper;

    @BackendOperation(module = "数据源管理", function = "添加数据源")
    @ApiOperation("添加数据源")
    @PostMapping
    public ResponseResult<Boolean> addDbResource(@RequestBody PlatformDbResource dbResource) {
        return ResponseResult.success(dbResource.insert());
    }

    @BackendOperation(module = "数据源管理", function = "逻辑删除数据源")
    @ApiOperation("逻辑删除数据源")
    @DeleteMapping
    public ResponseResult<Boolean> deleteDbResource(@RequestParam Integer id) {
        return ResponseResult.success(new PlatformDbResource().setId(id).deleteById());
    }

    @BackendOperation(module = "数据源管理", function = "更新数据源")
    @ApiOperation("更新数据源")
    @PutMapping
    public ResponseResult<Boolean> updateDbResource(@RequestBody PlatformDbResource dbResource) {
        return ResponseResult.success(dbResource.updateById());
    }

    @ApiOperation("分页查询数据源")
    @GetMapping("/list")
    public ResponseResult<SimplePage<PlatformDbResource>> list(@ApiParam("数据源名称") @RequestParam(required = false) String name) {

        IPage<PlatformDbResource> page = this.resourceMapper.selectPage(new Page<>(),
                new QueryWrapper<PlatformDbResource>().like(name != null, PlatformDbResource.SOURCE_NAME, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }
}

