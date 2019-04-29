package com.jgw.supercodeplatform.trace.controller.massifbatch;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.MassifBatchInfo;
import com.jgw.supercodeplatform.trace.service.tracefun.TraceObjectBatchInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trace/massif/info")
@Api(tags = "地块生产记录")
public class MassifBatchInfoController  extends CommonUtil {


    @Autowired
    private TraceObjectBatchInfoService traceObjectBatchInfoService;

    @GetMapping("/page")
    @ApiOperation(value = "获取地块生产记录", notes = "地块批次列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", defaultValue = "30", value = "每页记录数,不传默认10条,非必需"),
            @ApiImplicitParam(name = "current", paramType = "query", defaultValue = "3", value = "当前页,不传默认第一页,非必需"),
            @ApiImplicitParam(name = "massifId", paramType = "query", defaultValue = "1", value = "地块id",required = true)
    })
    public RestResult<AbstractPageService.PageResults<List<MassifBatchInfo>>> listTraceBatchInfoByMassifId(@RequestParam @ApiIgnore Map<String, Object> map) throws Exception
    {
        return new RestResult(200, "success", traceObjectBatchInfoService.listMassifTraceBatchInfo(map));
    }
}
