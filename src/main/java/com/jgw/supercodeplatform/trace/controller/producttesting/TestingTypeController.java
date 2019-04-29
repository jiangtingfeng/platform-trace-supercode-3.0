package com.jgw.supercodeplatform.trace.controller.producttesting;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.trace.common.model.RestResult;

import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;

import com.jgw.supercodeplatform.trace.service.producttesting.TestingTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/trace/producttesting/testingtype")
@Api(tags = "检测项管理")
public class TestingTypeController {

    @Autowired
    private TestingTypeService testingTypeService;

    @PostMapping
    @ApiOperation(value = "新增检测项", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody TestingType testingType) throws Exception {

        return new RestResult(200, "success", testingTypeService.insert(testingType));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取检测项", notes = "检测类型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需")
    })
    public RestResult listTestingType(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        return new RestResult(200, "success", testingTypeService.listTestingType(map));
    }

    @DeleteMapping
    @ApiOperation(value = "删除检测项", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTestingType(Integer testingTypeId ) throws Exception {
        testingTypeService.delete(testingTypeId);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改检测项", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTestingType(@RequestBody TestingType testingType) throws Exception {
        testingTypeService.update(testingType);
        return new RestResult(200, "success", null);
    }

}
