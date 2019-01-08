package com.jgw.supercodeplatform.trace.controller.dynamic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicAddOrUpdateParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicDeleteParam;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/dynamic")
@Api(tags = "动态功能表数据增删改查接口")
public class DynamicTableController {
    @Autowired
    private DynamicTableService service;
    
	@RequestMapping(value="/addFunData",method=RequestMethod.POST)
	@ApiOperation(value = "定制功能数据新增接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> add(@RequestBody DynamicAddOrUpdateParam param,@RequestHeader(value="functionId") String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.addFunData(param);
	}
	
	@RequestMapping(value="/addNodeData",method=RequestMethod.POST)
	@ApiOperation(value = "节点数据新增接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> addNodeData(@RequestBody DynamicAddOrUpdateParam param,@RequestHeader(value="functionId") String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.addNodeData(param);
	}
	
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能分页查询接口", notes = "该接口字段信息在返回参数other中")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<PageResults<List<Map<String, Object>>>> list(@RequestBody DynamicTableRequestParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.list(param);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能数据修改接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> update(@RequestBody DynamicAddOrUpdateParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.update(param);
	}
	
	@RequestMapping(value="/hideOrDelete",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能数据隐藏或删除(逻辑删除)接口接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> hideOrDelete(@RequestBody DynamicDeleteParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.hideOrDelete(param);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ApiOperation(value = "**-动态功能数据删(物理删除)除接口--暂不启用，请使用hideOrDelete方法", notes = "",hidden=true)
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> delete(@RequestBody DynamicDeleteParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.delete(param);
	}

}
