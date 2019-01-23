package com.jgw.supercodeplatform.trace.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "测试模板")
public class TraceFunFieldConfigParamListP {
	@NotEmpty
	@ApiModelProperty(value = "*测试", required = true)
	private List<TraceFunFieldConfigParam> list;

	public List<TraceFunFieldConfigParam> getList() {
		return list;
	}

	public void setList(List<TraceFunFieldConfigParam> list) {
		this.list = list;
	}

}
