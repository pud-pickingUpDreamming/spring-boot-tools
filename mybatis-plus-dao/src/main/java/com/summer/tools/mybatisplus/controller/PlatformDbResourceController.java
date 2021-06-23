package com.summer.tools.mybatisplus.controller;


import com.summer.tools.mybatisplus.orm.model.PlatformDbResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("添加数据源")
    @PostMapping
    public void addDbResource(@RequestBody PlatformDbResource dbResource) {
        dbResource.insert();
    }

    @ApiOperation("逻辑删除数据源")
    @DeleteMapping
    public void deleteDbResource(@RequestParam Integer id) {
        new PlatformDbResource().setId(id).deleteById();
    }

    @ApiOperation("更新数据源")
    @PutMapping
    public void updateDbResource(@RequestBody PlatformDbResource dbResource) {
        dbResource.updateById();
    }
}

