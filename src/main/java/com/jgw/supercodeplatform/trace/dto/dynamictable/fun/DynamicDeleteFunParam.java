package com.jgw.supercodeplatform.trace.dto.dynamictable.fun;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "定制功能表删除model")
public class DynamicDeleteFunParam {
	@NotEmpty
	@ApiModelProperty(name = "ids", value = "删除的id集合", example = "", required = false)
	private List<Long> ids;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能，由请求头传", example = "", required = false,hidden=true)
	private String functionId;
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
}
