package com.jgw.supercodeplatform.project.zaoyangpeach.controller;


import com.jgw.supercodeplatform.project.zaoyangpeach.service.BatchInfoService;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trace/zaoyangpeach/batchinfo")
public class BatchInfoController {

    @Autowired
    private BatchInfoService batchInfoService;

    @GetMapping("/selectByName")
    public RestResult selectByName(String batchName,Integer functionType) throws Exception {
        return new RestResult(200, "success", batchInfoService.getBatchInfo(batchName,functionType));
    }


}
