package com.jgw.supercodeplatform.trace.dto.dynamictable.fun;

import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "动态表编辑数据model")
public class DynamicUpdateFunParam {

	@ApiModelProperty(name = "lineData", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
	private LineBusinessData lineData;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能", example = "", required = false,hidden=true)
	private String functionId;
	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public LineBusinessData getLineData() {
		return lineData;
	}

	public void setLineData(LineBusinessData lineData) {
		this.lineData = lineData;
	}

	
}
