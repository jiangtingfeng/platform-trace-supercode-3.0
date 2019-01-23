package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "数据库一行记录业务数据，请求参数model")
public class LineBusinessData {
	
	@NotEmpty
	@ApiModelProperty(name = "data", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
	private List<FieldBusinessParam> fields;

	public List<FieldBusinessParam> getFields() {
		return fields;
	}

	public void setFields(List<FieldBusinessParam> fields) {
		this.fields = fields;
	}
	
}
