package com.jgw.supercodeplatform.trace.controller.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trace/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "测试接口")
public class TestController {

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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "测试参数非空接口", notes = "")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<List<String>> add(  @RequestBody @Valid TraceFunFieldConfigParam param, HttpServletRequest request) throws IOException, ParseException {
		RestResult<List<String>> result=new RestResult<List<String>>();
		System.out.println(param);
		return result;
	}
}
