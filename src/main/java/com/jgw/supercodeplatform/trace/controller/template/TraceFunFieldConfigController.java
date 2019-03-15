package com.jgw.supercodeplatform.trace.controller.template;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jgw.supercodeplatform.trace.dto.PlatformFun.CustomizeFun;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldSortCaram;
import com.jgw.supercodeplatform.trace.dto.template.query.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigDelegate;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/traceFieldFunConfig")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "功能字段配置管理")
public class TraceFunFieldConfigController {

	@Autowired
	private TraceFunFieldConfigService service;
	
	/**
	 * 新增功能
	 * 
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 * @throws SuperCodeTraceException 
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "新增功能字段接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<List<String>> add(@RequestBody @Valid CustomizeFun param, HttpServletRequest request) throws IOException, ParseException, SuperCodeTraceException {
		RestResult<List<String>> result=null;
		try {
			result =service.add(param);
		} catch (Exception e) {
			e.printStackTrace();
			result=new RestResult<List<String>>();
			result.setMsg(e.getMessage());
			result.setState(500);
		}
		return result;
	}


	/**
	 * 动态修改功能字段
	 * 
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "修改功能字段接口", notes = "传的字段只能是新增的字段，功能原字段不传")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<String> update(@Valid  @RequestBody List<TraceFunFieldConfigParam> param, HttpServletRequest request) throws IOException, ParseException {
		RestResult<String> result=null;
		try {
			result =service.update(param);
		} catch (Exception e) {
			e.printStackTrace();
			result=new RestResult<>();
			result.setMsg(e.getMessage());
			result.setState(500);
		}
		return result;
	}
	
	/**
	 * 动态修改功能字段
	 * 
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/fielSort", method = RequestMethod.POST)
	@ApiOperation(value = "字段排序接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<String> fielSort(@Valid  @RequestBody List<TraceFunFieldSortCaram> param, HttpServletRequest request) throws IOException, ParseException {
		RestResult<String> result=null;
		try {
			result =service.fielSort(param);
		} catch (Exception e) {
			e.printStackTrace();
			result=new RestResult<>();
			result.setMsg(e.getMessage());
			result.setState(500);
		}
		return result;
	}
	
	/**
	 * 新增功能
	 * 
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ApiOperation(value = "功能字段查询接口",consumes="application/json;charset=UTF-8")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")})
	public RestResult<List<TraceFunFieldConfig>> query(@RequestBody TraceFunTemplateconfigQueryParam param, HttpServletRequest request) throws IOException, ParseException {
		RestResult<List<TraceFunFieldConfig>> result=new RestResult<List<TraceFunFieldConfig>>();
		try {
			if (StringUtils.isBlank(param.getFunctionId())) {
				result.setState(500);
				result.setMsg("functionId不能为空");
				return result;
			}
			//如果是查询节点字段属性则判断是否为自动节点 是的话则模板id不能为空
			if (null!=param.getBusinessType()) {
				param.setTypeClass(2);
				if (param.getBusinessType().equals(1) && StringUtils.isBlank(param.getTraceTemplateId())) {
					result.setState(500);
					result.setMsg("自动节点则traceTemplateId不能为空");
					return result;
				}
			}else {
				param.setTypeClass(1);	
			}
			List<TraceFunFieldConfig> set =service.query(param);
			result.setResults(set);
			result.setState(200);
		} catch (Exception e) {
			e.printStackTrace();
			result=new RestResult<>();
			result.setMsg(e.getMessage());
			result.setState(500);
		}
		return result;
	}
}
