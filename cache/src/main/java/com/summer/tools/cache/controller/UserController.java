package com.summer.tools.cache.controller;

import com.summer.tools.cache.orm.model.UserInfo;
import com.summer.tools.cache.service.IUserInfoService;
import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    @Resource
    private IUserInfoService userInfoService ;
    @PostMapping
    @ApiOperation("添加用户")
    public ResponseResult<?> saveData (@RequestBody UserInfo userInfo){
        userInfo.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
        userInfoService.saveData(userInfo);
        return ResponseResult.SUCCESS;
    }
    @GetMapping("/{id}")
    @ApiOperation("查询用户")
    @BackendOperation(module = "缓存模块", function = "查询用户")
    public ResponseResult<UserInfo> selectById (@PathVariable Integer id) {
        return ResponseResult.success(userInfoService.selectById(id));
    }

    @GetMapping("/list")
    @ApiOperation("用户列表")
    public ResponseResult<List<UserInfo>> selectList () {
        return ResponseResult.success(userInfoService.selectList());
    }
}
