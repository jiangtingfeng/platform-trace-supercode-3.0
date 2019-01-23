package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "节点业务数据隐藏model")
public class DynamicHideParam {
	@NotEmpty
	@ApiModelProperty(name = "ids", value = "删除的id集合", example = "", required = false)
	private List<Long> ids;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能，由请求头传", example = "", required = false,hidden=true)
	private String functionId;

	@ApiModelProperty(value = "溯源模板id，隐藏的是节点业务数据必传",required=true)
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(value = "1隐藏 0展示",required=true,notes="")
	private Integer DeleteStatus;      //删除状态
	
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

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public Integer getDeleteStatus() {
		return DeleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		DeleteStatus = deleteStatus;
	}
	
}
