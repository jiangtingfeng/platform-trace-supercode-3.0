package com.jgw.supercodeplatform.trace.controller.dynamic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicAddFunParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicDeleteFunParam;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/dynamic/fun")
@Api(tags = "定制功能表数据增删改查接口")
public class DynamicFunController {
    @Autowired
    private DynamicTableService service;
    
	@RequestMapping(value="/addFunData",method=RequestMethod.POST)
	@ApiOperation(value = "定制功能数据新增接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> add(@RequestBody DynamicAddFunParam param,@RequestHeader(value="functionId") String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.addFunData(param);
	}	
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能分页查询接口", notes = "该接口字段信息在返回参数other中")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<PageResults<List<Map<String, Object>>>> list(@RequestBody DynamicTableRequestParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.list(param);
	}
	
	@RequestMapping(value="/getById",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能查询单条记录接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "主键id",name="id",required=true),@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<Map<String, Object>> getById(@RequestParam(required=true)Long id ,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		return service.getById(id,functionId);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ApiOperation(value = "动态功能数据修改接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<String> update(@RequestBody LineBusinessData lineData,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		return service.update(functionId,lineData,false,null);
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ApiOperation(value = "**删除接口", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "功能id",name="functionId",required=true),@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<String>> delete(@RequestBody DynamicDeleteFunParam param,@RequestHeader(value="functionId",required=true) String functionId) throws Exception {
		param.setFunctionId(functionId);
		return service.deleteFun(param);
	}
}
