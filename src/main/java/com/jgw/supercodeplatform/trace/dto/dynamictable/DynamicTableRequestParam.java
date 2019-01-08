package com.jgw.supercodeplatform.trace.dto.dynamictable;

import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "动态表参数model", parent = DaoSearch.class)
public class DynamicTableRequestParam extends DaoSearch {
	@ApiModelProperty(name = "groupBy", value = "分组参数-暂未使用", example = "Name", required = false)
	private String groupBy;
	
	@ApiModelProperty(name = "orderBy", value = "排序参数-暂未使用", example = "Id", required = false)
	private String orderBy;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能用来确定查找的表", example = "", required = false,hidden=true)
	private String functionId;
	
	
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

}
