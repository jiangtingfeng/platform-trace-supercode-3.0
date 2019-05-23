package com.jgw.supercodeplatform.project.zaoyangpeach.controller;


import com.jgw.supercodeplatform.project.zaoyangpeach.service.BatchInfoService;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
