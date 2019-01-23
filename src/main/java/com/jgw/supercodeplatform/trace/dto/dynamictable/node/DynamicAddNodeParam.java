package com.jgw.supercodeplatform.trace.dto.dynamictable.node;

import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "节点新增数据model")
public class DynamicAddNodeParam {

	@ApiModelProperty(name = "lineData", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
	private LineBusinessData lineData;
	
	@ApiModelProperty(name = "functionId", value = "功能id，表示操作的功能", example = "", required = false,hidden=true)
	private String functionId;
	
	@ApiModelProperty(name = "traceTemplateId", value = "**-溯源模板id,新增或修改溯源节点信息时必传，新增定制功能业务数据时不传", example = "", required = true)
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(name = "traceBatchInfoId", value = "操作批次唯一id，,新增或修改溯源节点信息时必传，新增定制功能业务数据时不传", example = "", required = true)
	private String traceBatchInfoId;

	
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
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

	public LineBusinessData getLineData() {
		return lineData;
	}

	public void setLineData(LineBusinessData lineData) {
		this.lineData = lineData;
	}
	
}
