package com.jgw.supercodeplatform.project.zaoyangpeach.controller;

import com.jgw.supercodeplatform.project.zaoyangpeach.service.DynamicFunctionService;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trace/zaoyangpeach/dynamicfun")
public class DynamicFunctionController {

    @Autowired
    private DynamicFunctionService service;


    @RequestMapping(value="/list",method= RequestMethod.POST)
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
    public RestResult<AbstractPageService.PageResults<List<Map<String, Object>>>> list(@RequestBody DynamicTableRequestParam param, @RequestHeader(value="functionId",required=true) String functionId) throws Exception {
        param.setFunctionId(functionId);
        return service.list(param);
    }

}
