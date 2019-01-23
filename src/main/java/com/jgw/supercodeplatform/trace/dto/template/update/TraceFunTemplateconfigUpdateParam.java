package com.jgw.supercodeplatform.trace.dto.template.update;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "溯源功能-溯源模板更新参数")
public class TraceFunTemplateconfigUpdateParam {
	
	@NotEmpty
	@ApiModelProperty(value = "溯源模板id",required = true)
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(value = "溯源模板名称")
    private String traceTemplateName;    //溯源模板名称
	
	@ApiModelProperty(value = "修改或新增的模板和节点数据")
	private List<TraceFunTemplateconfigUpdateSubParam> templateList;

	
	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public String getTraceTemplateName() {
		return traceTemplateName;
	}

	public void setTraceTemplateName(String traceTemplateName) {
		this.traceTemplateName = traceTemplateName;
	}

	public List<TraceFunTemplateconfigUpdateSubParam> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<TraceFunTemplateconfigUpdateSubParam> templateList) {
		this.templateList = templateList;
	}
}

