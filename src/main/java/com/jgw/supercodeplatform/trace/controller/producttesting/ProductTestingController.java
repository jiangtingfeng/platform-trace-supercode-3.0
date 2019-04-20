package com.jgw.supercodeplatform.trace.controller.producttesting;


import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.producttesting.ProductTestingParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.service.producttesting.ProductTestingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/trace/producttesting/producttesting")
@Api(tags = "内部检测管理")
public class ProductTestingController {

    @Autowired
    private ProductTestingService productTestingService;

    @PostMapping
    @ApiOperation(value = "新增内部检测", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody ProductTestingParam productTesting) throws Exception {

        return new RestResult(200, "success", productTestingService.insert(productTesting));
    }

    @DeleteMapping
    @ApiOperation(value = "删除内部检测", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTestingType(Integer testingTypeId ) throws Exception {
        productTestingService.delete(testingTypeId);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改内部检测", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTestingType(@RequestBody ProductTestingParam productTesting) throws Exception {
        productTestingService.update(productTesting);
        return new RestResult(200, "success", null);
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取内部检测", notes = "内部检测列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "testingType", paramType = "query", defaultValue = "3", value = "检测类型: 内部检测为1，第三方检测为2")
    })
    public RestResult listTestingType(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        return new RestResult(200, "success", productTestingService.listProductTesting(map));
    }

    @GetMapping("/getById")
    @ApiOperation(value = "获取检测详情", notes = "内部检测列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "3", value = "检测类型: 内部检测为1，第三方检测为2")
    })
    public RestResult getById(Integer productTestingId ) throws Exception {
        return new RestResult(200, "success", productTestingService.getById(productTestingId));
    }


}
