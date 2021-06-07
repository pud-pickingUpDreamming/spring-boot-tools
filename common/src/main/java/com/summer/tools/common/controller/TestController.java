package com.summer.tools.common.controller;

import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.utils.IPUtil;
import com.summer.tools.common.utils.LocationUtil;
import com.summer.tools.common.utils.ResponseResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "公共模块测试接口", tags = "工具工程-公共模块")
public class TestController {

    @Resource
    private HttpServletRequest request;

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

    @GetMapping("/ipLocation")
    @ApiOperation(value = "查询请求ip及归属")
    public ResponseResult<String> locationTest(@ApiParam(value = "ip地址:公网IP地址才能解析到位置") @RequestParam String ip) {
        ip = StringUtils.isNoneBlank(ip) ? ip : IPUtil.getIpAddr(request);
        String location = LocationUtil.getLocationByIP(ip);
        String result = MessageFormat.format("ip地址[{0}], 归属地[{1}]", ip, location);
        log.info(result);
        return ResponseResult.success(result);
    }
}
