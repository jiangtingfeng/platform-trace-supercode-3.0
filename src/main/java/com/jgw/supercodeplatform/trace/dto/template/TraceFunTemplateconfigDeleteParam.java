package com.jgw.supercodeplatform.trace.dto.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "注意前面带**-字段，溯源功能-溯源节点删除")
public class TraceFunTemplateconfigDeleteParam {
	
	@ApiModelProperty(value = "溯源模板id",required=true)
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(value = "节点对应的功能id",required=true)
    private String nodeFunctionId;           //功能ID号
	
	@ApiModelProperty(value = "节点对应的功能id",hidden=true)
    private String orgnazitionId;           //功能ID号

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public String getNodeFunctionId() {
		return nodeFunctionId;
	}

	public void setNodeFunctionId(String nodeFunctionId) {
		this.nodeFunctionId = nodeFunctionId;
	}

	public String getOrgnazitionId() {
		return orgnazitionId;
	}

	public void setOrgnazitionId(String orgnazitionId) {
		this.orgnazitionId = orgnazitionId;
	}
	
}
