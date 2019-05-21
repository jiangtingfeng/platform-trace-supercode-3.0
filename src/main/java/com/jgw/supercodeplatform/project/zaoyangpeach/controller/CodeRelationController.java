package com.jgw.supercodeplatform.project.zaoyangpeach.controller;


import com.jgw.supercodeplatform.project.zaoyangpeach.service.CodeService;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/trace/zaoyangpeach/coderelation")
public class CodeRelationController {

    @Autowired
    private CodeService codeService;

    @GetMapping(value = "/record")
    public RestResult listRelationRecord(@ApiIgnore @RequestParam Map<String, Object> paramsMap) throws Exception{
        return new RestResult(200, "success", codeService.listRelationRecord(paramsMap));
    }

}
