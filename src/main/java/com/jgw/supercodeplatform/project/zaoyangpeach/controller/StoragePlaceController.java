package com.jgw.supercodeplatform.project.zaoyangpeach.controller;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import com.jgw.supercodeplatform.trace.service.zaoyangpeach.StoragePlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/trace/zaoyangpeach/storageplace")
@Api(tags = "存放地点管理")
public class StoragePlaceController {

    @Autowired
    private StoragePlaceService storagePlaceService;

    @PostMapping
    @ApiOperation(value = "新增存放地点", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertStoragePlace(@RequestBody StoragePlace record) throws Exception {

        return new RestResult(200, "success", storagePlaceService.insert(record));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取存放地点", notes = "存放地点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "disableFlag", paramType = "query", defaultValue = "0", value = "禁用标记")
    })
    public RestResult listTestingType(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        return new RestResult(200, "success", storagePlaceService.listStoragePlace(map));
    }

    @DeleteMapping
    @ApiOperation(value = "删除存放地点", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteStoragePlace(Integer id ) throws Exception {
        storagePlaceService.delete(id);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改存放地点", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTestingType(@RequestBody StoragePlace record) throws Exception {
        storagePlaceService.update(record);
        return new RestResult(200, "success", null);
    }

}
