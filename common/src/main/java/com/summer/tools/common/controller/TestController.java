package com.summer.tools.common.controller;

import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.model.HutoolExcelTestModel;
import com.summer.tools.common.utils.ExcelUtil;
import com.summer.tools.common.utils.IPUtil;
import com.summer.tools.common.utils.LocationUtil;
import com.summer.tools.common.utils.ResponseResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "公共模块测试接口", tags = "工具工程-公共模块")
public class TestController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

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

    @PostMapping("/import")
    @ApiOperation(value = "导入excel文件")
    public ResponseResult<List<HutoolExcelTestModel>> importTest(@ApiParam(value = "源文件地址") @RequestParam MultipartFile file) throws IOException {

        List<HutoolExcelTestModel> list = ExcelUtil.importExcel(file.getInputStream(), 0, HutoolExcelTestModel.class);
        // todo 入库
        return ResponseResult.success(list);
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载excel文件")
    public ResponseResult<?> downloadTest(@ApiParam(value = "文件名") @RequestParam String fileName,
                                          @ApiParam(value = "文件路径") @RequestParam(required = false) String targetPath) throws IOException {

        List<HutoolExcelTestModel> list = new ArrayList<>(1);
        HutoolExcelTestModel testModel = new HutoolExcelTestModel().setDeptName("财务").setRoleId("12345")
                .setRoleName("财务经理").setRemark("导出excel测试model");
        list.add(testModel);
        if (StringUtils.isNotBlank(targetPath)) {
            String file = targetPath + fileName + ".xlsx";
            ExcelUtil.exportExcel(list, file, fileName, HutoolExcelTestModel.class);
        } else {
            ExcelUtil.exportExcel(list, response, fileName, fileName, HutoolExcelTestModel.class);
        }
        return ResponseResult.SUCCESS;
    }
}
