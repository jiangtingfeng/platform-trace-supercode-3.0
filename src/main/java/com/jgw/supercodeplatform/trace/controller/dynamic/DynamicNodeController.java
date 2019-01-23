package com.jgw.supercodeplatform.trace.controller.dynamic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicHideParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicAddNodeParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicDeleteNodeParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicUpdateNodeParam;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/dynamic/node")
@Api(tags = "溯源模板节点数据增删改查接口")
public class DynamicNodeController {
    @Autowired
    private DynamicTableService service;
	
	@RequestMapping(value="/addNodeData",method=RequestMethod.POST)
	@ApiOperation(value = "节点数据新增接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> addNodeData(@RequestBody DynamicAddNodeParam param,@RequestHeader(value="functionId") String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.addNodeData(param);
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能数据修改接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> update(@RequestBody DynamicUpdateNodeParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		return service.update(functionId,param.getLineData(),true,param.getTraceTemplateId());
	}
	
	@RequestMapping(value="/hide",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能数据隐藏(逻辑删除)接口接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> hideOrDelete(@RequestBody DynamicHideParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.hide(param);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ApiOperation(value = "**-自动节点数据删(物理删除)除接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> delete(@RequestBody DynamicDeleteNodeParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.delete(param);
	}

}
