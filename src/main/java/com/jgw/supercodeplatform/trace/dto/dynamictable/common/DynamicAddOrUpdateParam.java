package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "动态表新增数据model")
public class DynamicAddOrUpdateParam {

	@ApiModelProperty(name = "lineData", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
	private List<LineBusinessData> lineData;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能", example = "", required = false,hidden=true)
	private String functionId;
	
	@ApiModelProperty(name = "traceTemplateId", value = "**-溯源模板id,新增或修改溯源节点信息时必传，新增定制功能业务数据时不传", example = "", required = false)
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(name = "traceBatchInfoId", value = "操作批次唯一id", example = "", required = true)
	private String traceBatchInfoId;
	
	@ApiModelProperty(name = "isNode", value = "判断节点数据新增还是定制功能", example = "", required = false,hidden=true)
	private boolean isNode;      //溯源模板号
	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public List<LineBusinessData> getLineData() {
		return lineData;
	}

	public boolean isNode() {
		return isNode;
	}

	public void setNode(boolean isNode) {
		this.isNode = isNode;
	}

	public void setLineData(List<LineBusinessData> lineData) {
		this.lineData = lineData;
	}

	public String getTraceBatchInfoId() {
		return traceBatchInfoId;
	}

	public void setTraceBatchInfoId(String traceBatchInfoId) {
		this.traceBatchInfoId = traceBatchInfoId;
	}

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	
}
