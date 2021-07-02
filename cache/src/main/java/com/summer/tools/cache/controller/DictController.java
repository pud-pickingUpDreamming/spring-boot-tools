package com.summer.tools.cache.controller;

import com.summer.tools.cache.orm.model.Dict;
import com.summer.tools.cache.service.IDictService;
import com.summer.tools.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/dict")
@Api(tags = "字典模块")
public class DictController {

    @Resource
    private IDictService dictService;

    @PostMapping
    @ApiOperation("添加字典")
    public ResponseResult<?> saveData (@RequestBody Dict dict){
        dict.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
        dictService.saveData(dict);
        return ResponseResult.SUCCESS;
    }
    @GetMapping("/{id}")
    @ApiOperation("查询字典")
    public ResponseResult<Dict> selectById (@PathVariable Integer id) {
        return ResponseResult.success(dictService.selectById(id));
    }

    @GetMapping("/list")
    @ApiOperation("字典列表")
    public ResponseResult<List<Dict>> selectList (String type) {
        return ResponseResult.success(dictService.selectList(type));
    }
}
