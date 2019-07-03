package com.jgw.supercodeplatform.trace.controller.standardtemplate;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.common.pojo.common.JsonResult;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.TemplateTypeParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import com.jgw.supercodeplatform.trace.service.standardtemplate.TemplateTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trace/standardtemplate/templatetype")
@Api(tags = "模板类别")
public class TemplateTypeController {

    @Autowired
    private TemplateTypeService templateTypeService;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header"),
            @ApiImplicitParam(name = "search", paramType = "query",value = "搜索值", defaultValue = "", required = false),
            @ApiImplicitParam(name = "categoryId", paramType = "query",value = "类别id", defaultValue = "", required = false),
            @ApiImplicitParam(name = "parentId", paramType = "query",value = "一级类目id", defaultValue = "", required = false),
            @ApiImplicitParam(name = "enable", paramType = "query",value = "启用状态", defaultValue = "1", required = false),
            @ApiImplicitParam(name = "levelId", paramType = "query",value = "分类级别id：一级类目为1，二级类目为2", defaultValue = "", required = false)
    })
    @ApiOperation(value = "获取模板类别列表")
    public JsonResult<AbstractPageService.PageResults<TemplateTypeParam>> listSearchViewLike(DaoSearch daoSearch,
            @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer parentId,@RequestParam(required = false) Integer levelId
            ,@RequestParam(required = false) Integer enable) throws Exception{
        daoSearch.setParams(null);
        Map params= daoSearch.getParams();
        params.put("categoryId",categoryId);
        params.put("parentId",parentId);
        params.put("levelId",levelId);
        params.put("enable",enable);
        return new JsonResult(200, "success", templateTypeService.listSearchViewLike(daoSearch));
    }

    @PostMapping
    @ApiOperation(value = "新增模板类别", notes = "新增成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult insertTestingType(@RequestBody TemplateTypeParam record) throws Exception {

        return new RestResult(200, "success", templateTypeService.insert(record));
    }

    @DeleteMapping
    @ApiOperation(value = "删除模板类别", notes = "删除成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult deleteTemplateType(Long templateTypeId ) throws Exception {
        templateTypeService.delete(templateTypeId);
        return new RestResult(200, "success", null);
    }

    @PutMapping
    @ApiOperation(value = "修改模板类别", notes = "修改成功失败标志位")
    @ApiImplicitParam(name = "super-token", value = "token", defaultValue = "cd8716732ef8476b9894dbe1ba2dd7b1", required = true, paramType = "header")
    public RestResult updateTemplateType(@RequestBody TemplateType record) throws Exception {
        templateTypeService.update(record);
        return new RestResult(200, "success", null);
    }



}
