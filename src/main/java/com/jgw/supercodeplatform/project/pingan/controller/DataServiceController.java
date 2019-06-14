package com.jgw.supercodeplatform.project.pingan.controller;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.service.supercode.DataService;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/trace/supercode")
public class DataServiceController {

    @Autowired
    private DataService dataService;

    @Autowired
    private TraceBatchInfoService traceBatchInfoService;

    @RequestMapping("/dataservice/{functionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    })
    public String execute(HttpServletRequest request,@PathVariable("functionId") String functionId) throws Exception {
        String queryString=request.getQueryString();
        String responseText= dataService.getForObject(functionId,queryString);
        return responseText;
    }

    @GetMapping("/redirectTracePage")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",value = "批次号",name="batchId",required=true)
    })
    public RestResult redirectTracePage(String batchId, HttpServletResponse response) throws Exception{
        String url= traceBatchInfoService.getH5PageUrlByTraceBatchId(batchId);
        response.sendRedirect(url);
        return new RestResult(200, "success", null);
    }

}
