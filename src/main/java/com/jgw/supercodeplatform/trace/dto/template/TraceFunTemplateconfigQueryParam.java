package com.jgw.supercodeplatform.trace.dto.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 查询字段属性
 * 1：根据functionId和TypeClass可以确定定制功能字段
 * 2：根据functionId和traceTemplateId可以确定节点属性字段
 * @author czm
 *
 */
@ApiModel(value = "溯源功能--功能及节点字段查询参数类")
public class TraceFunTemplateconfigQueryParam {
	
	@ApiModelProperty(value = "**-溯源模板id，如果businessType为自动节点类型则必传")
	private String traceTemplateId;      //溯源模板号
	
	@ApiModelProperty(value = "功能id",required=true)
    private String functionId;  //功能ID号
	
	@ApiModelProperty(value = "标明是节点属性还是定制功能属性",hidden=true)
	private Integer typeClass;  //节点类型
	
	
	@ApiModelProperty(value = "**-节点类型--用于区分请求的是节点字段还是定制功能字段如果请求的是节点字段属性则必传该值",notes="默认不传则请求的是定制功能节点属性",required=false)
    private Integer businessType;  //功能ID号

	
	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(Integer typeClass) {
		this.typeClass = typeClass;
	}

	
}
