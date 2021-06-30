package com.summer.tools.mybatisplus.controller;


import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.common.validation.Add;
import com.summer.tools.mybatisplus.orm.model.PlatformTenantDatasource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-06-30
 */
@RestController
@RequestMapping("/platformTenantDatasource")
@Api(tags = "数据源管理")
public class PlatformTenantDatasourceController {

    @BackendOperation(module = "数据源管理", function = "添加数据源和租户模块绑定关系")
    @ApiOperation("添加数据源和租户模块绑定关系")
    @PostMapping
    public ResponseResult<Boolean> bindTenantDatasource(@Validated(Add.class) @RequestBody PlatformTenantDatasource tenantDatasource) {
        return ResponseResult.success(tenantDatasource.insert());
    }

    @BackendOperation(module = "数据源管理", function = "逻辑删除数据源和租户模块绑定关系")
    @ApiOperation("逻辑删除数据源和租户模块绑定关系")
    @DeleteMapping
    public ResponseResult<Boolean> deleteTenantDatasource(@RequestParam Integer id) {
        return ResponseResult.success(new PlatformTenantDatasource().setId(id).deleteById());
    }
}

