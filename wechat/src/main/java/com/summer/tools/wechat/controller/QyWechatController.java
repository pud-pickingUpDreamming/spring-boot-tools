package com.summer.tools.wechat.controller;

import com.summer.tools.common.utils.ResponseResult;
import com.summer.tools.wechat.service.QyWechatSignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/qyWechat")
@Api(tags = "企业微信相关接口")
public class QyWechatController {

    @Resource
    private QyWechatSignService signService;

    @GetMapping("/sign")
    @ApiOperation("获取sign签名")
    public ResponseResult<Map<String, String>> getSign(@ApiParam(example = "http://www.vxzsk.com/xx/x.do") @RequestParam String url) {

        Map<String, String> signResult = this.signService.sign(url);

        return ResponseResult.success(signResult);
    }
}
