package com.summer.tools.common.controller;

import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.utils.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@Api(value = "公共模块测试接口", tags = "工具工程-公共模块")
public class TestController {

    @PostMapping("/swaggerTest/body")
    @ApiOperation(value = "swagger json参数测试接口")
    @BackendOperation(module = "公共模块", function = "swagger测试接口")
    public ResponseResult<Result> swaggerTest(@RequestBody RequestParams params) {

        Result result = new Result().setKey(params.getKey()).setValue(params.getValue())
                .setAdditionValue(params.getKey() + ":" + params.getValue());
        return ResponseResult.success(result);
    }

    @GetMapping("/swaggerTest/params")
    @ApiOperation(value = "swagger 普通参数测试接口")
    public ResponseResult<Result> swaggerTest(@ApiParam(value = "键") String key, @ApiParam(value = "值") String value) {

        Result result = new Result().setKey(key).setValue(value)
                .setAdditionValue(key + ":" + value);
        return ResponseResult.success(result);
    }
}
