package com.jgw.supercodeplatform.project.zaoyangpeach.controller;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.trace.common.model.ObjectUniqueValueResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.zaoyangpeach.StoragePlaceParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.SortingPlace;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import com.jgw.supercodeplatform.trace.service.zaoyangpeach.StoragePlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trace/zaoyangpeach/storageplace")
@Api(tags = "存放地点管理")
public class StoragePlaceController {

    @Autowired
    private StoragePlaceService storagePlaceService;

    @PostMapping
    @ApiOperation(value = "新增存放地点", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertStoragePlace(@RequestBody StoragePlaceParam record) throws Exception {
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(record), Map.class);
        StoragePlace storagePlace = JSONObject.parseObject(JSONObject.toJSONString(map), StoragePlace.class);
        return new RestResult(200, "success", storagePlaceService.insert(storagePlace));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取存放地点", notes = "存放地点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "search", paramType = "query", defaultValue = "0", value = "搜索值")
    })
    public RestResult listStoragePlace(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
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
    public RestResult updateTestingType(@RequestBody StoragePlaceParam record) throws Exception {
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(record), Map.class);
        StoragePlace storagePlace = JSONObject.parseObject(JSONObject.toJSONString(map), StoragePlace.class);
        storagePlaceService.update(storagePlace);
        return new RestResult(200, "success", null);
    }

    @GetMapping("/listSortingPlace")
    @ApiOperation(value = "获取分拣点", notes = "分拣点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "search", paramType = "query", defaultValue = "0", value = "搜索值")
    })
    public RestResult listSortingPlace(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        return new RestResult(200, "success", storagePlaceService.listSortingPlace(map));
    }

    @PostMapping("updateDisableFlag")
    @ApiOperation(value = "设置存放地点启用状态", notes = "修改成功失败标志位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "id"),
            @ApiImplicitParam(name = "disableFlag", paramType = "query", defaultValue = "3", value = "状态标记：启用值为0，禁用值为1"),
    })
    public RestResult updateDisableFlag(Integer id, Integer disableFlag) throws Exception {
        storagePlaceService.updateDisableFlag(id,disableFlag);
        return new RestResult(200, "success", null);
    }

    @GetMapping("/list/field")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "search", paramType = "query", defaultValue = "0", value = "搜索值")
    })
    public RestResult listStoragePlaceField(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception {
        map.put("disableFlag",0);
        Map<String, Object> resultMap= storagePlaceService.listStoragePlace(map);
        List<StoragePlace> list= (List<StoragePlace>)resultMap.get("list");
        List<ObjectUniqueValueResult> uniqueValueResults= list.stream().map(e->new ObjectUniqueValueResult(String.valueOf(e.getPlaceNumber()),e.getPlaceName())).collect(Collectors.toList());
        resultMap.put("list",uniqueValueResults);

        return new RestResult(200, "success", resultMap);
    }
}
