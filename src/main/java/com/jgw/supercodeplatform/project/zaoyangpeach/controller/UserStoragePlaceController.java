package com.jgw.supercodeplatform.project.zaoyangpeach.controller;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.trace.common.model.ObjectUniqueValueResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import com.jgw.supercodeplatform.trace.service.zaoyangpeach.StoragePlaceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "存放地点模块")
@RequestMapping("/storage-place")
public class UserStoragePlaceController {

    @Autowired
    private StoragePlaceService storagePlaceService;

    @ApiOperation("获取地点根据地点类型")
    @GetMapping("/type")
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
