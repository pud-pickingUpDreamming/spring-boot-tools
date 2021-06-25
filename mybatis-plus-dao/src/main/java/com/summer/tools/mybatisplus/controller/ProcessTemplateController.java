package com.summer.tools.mybatisplus.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.model.SimplePage;
import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.mybatisplus.orm.dao.ProcessTemplateMapper;
import com.summer.tools.mybatisplus.orm.model.ProcessTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 流程模板表 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@DS("mysql")
@RestController
@RequestMapping("/processTemplate")
@Api(tags = "流程模板管理")
public class ProcessTemplateController {

    @Resource
    private ProcessTemplateMapper templateMapper;

    @BackendOperation(module = "流程模板管理", function = "添加流程模板")
    @ApiOperation("添加流程模板")
    @PostMapping
    public ResponseResult<Boolean> addTemplate(@RequestBody ProcessTemplate template) {
        return ResponseResult.success(template.insert());
    }

    @BackendOperation(module = "流程模板管理", function = "逻辑删除流程模板")
    @ApiOperation("逻辑删除流程模板")
    @DeleteMapping
    public ResponseResult<Boolean> deleteTemplate(@RequestParam String id) {
        return ResponseResult.success(new ProcessTemplate().setTemplateId(id).deleteById());
    }

    @BackendOperation(module = "流程模板管理", function = "更新流程模板")
    @ApiOperation("更新流程模板")
    @PutMapping
    public ResponseResult<Boolean> updateTemplate(@RequestBody ProcessTemplate template) {
        return ResponseResult.success(template.updateById());
    }

    @ApiOperation("分页查询流程模板")
    @GetMapping("/list")
    public ResponseResult<SimplePage<ProcessTemplate>> list(@ApiParam("流程模板名称") @RequestParam(required = false) String name) {

        IPage<ProcessTemplate> page = this.templateMapper.selectPage(new Page<>(),
                new QueryWrapper<ProcessTemplate>().like(name != null, ProcessTemplate.NAME, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }


}

