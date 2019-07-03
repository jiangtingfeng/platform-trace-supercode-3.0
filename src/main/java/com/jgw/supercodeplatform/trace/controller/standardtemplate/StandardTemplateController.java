package com.jgw.supercodeplatform.trace.controller.standardtemplate;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.common.DisableFlagDto;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.StandardTemplateParam;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import com.jgw.supercodeplatform.trace.service.standardtemplate.StandardTemplateService;
import com.jgw.supercodeplatform.trace.service.standardtemplate.TemplateTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trace/standardtemplate/templateinfo")
@Api(tags = "标准模板")
public class StandardTemplateController {

    @Autowired
    private StandardTemplateService standardTemplateService;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取标准模板列表")
    public JsonResult<AbstractPageService.PageResults<StandardTemplateParam>> listSearchViewLike(DaoSearch daoSearch) throws Exception{
        return new JsonResult(200, "success", standardTemplateService.listSearchViewLike(daoSearch));
    }

    @PostMapping
    @ApiOperation(value = "新增标准模板", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody StandardTemplateParam record) throws Exception {

        return new RestResult(200, "success", standardTemplateService.insert(record));
    }

    @DeleteMapping
    @ApiOperation(value = "删除标准模板", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTemplateType(Long standardTemplateId ) throws Exception {
        standardTemplateService.delete(standardTemplateId);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改标准模板", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTemplateType(@RequestBody StandardTemplateParam record) throws Exception {
        standardTemplateService.update(record);
        return new RestResult(200, "success", null);
    }

    @GetMapping("/getById")
    @ApiOperation(value = "获取标准模板详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "standardTemplateId", paramType = "query", defaultValue = "1", value = "检测id")
    })
    public RestResult<StandardTemplateParam> getById(Long standardTemplateId ) throws Exception {
        return new RestResult(200, "success", standardTemplateService.selectByPrimaryKey(standardTemplateId));
    }

    @PutMapping("updateDisableFlag")
    @ApiOperation(value = "设置标准模板启用状态", notes = "修改成功失败标志位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    })
    public RestResult updateDisableFlag(@RequestBody DisableFlagDto disableFlagDto) throws Exception {
        standardTemplateService.updateDisableFlag(disableFlagDto.getDisableFlag(),disableFlagDto.getId());
        return new RestResult(200, "success", null);
    }

    @GetMapping("/getByLevelId")
    @ApiOperation(value = "根据类目id获取标准模板详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "levelId", paramType = "query", defaultValue = "1", value = "类目id")
    })
    public RestResult<StandardTemplateParam> getByLevelId(Integer levelId ) throws Exception {
        return new RestResult(200, "success", standardTemplateService.getByLevelId(levelId));
    }

}
