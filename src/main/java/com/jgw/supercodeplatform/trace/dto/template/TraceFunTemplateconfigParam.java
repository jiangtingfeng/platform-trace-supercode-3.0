package com.jgw.supercodeplatform.trace.dto.template;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "溯源功能-新增追溯模板节点配置字段模板类")
public class TraceFunTemplateconfigParam {
	@ApiModelProperty(value = "溯源模板主键id")
    private Long id;    //溯源模板主键id
	
	@ApiModelProperty(value = "溯源模板id")
	private String traceTemplateId;      //溯源模板号
	
	@NotEmpty
	@ApiModelProperty(value = "溯源模板名称",required=true)
    private String traceTemplateName;    //溯源模板名称
	
	@NotEmpty
	@ApiModelProperty(value = "业务类型 ，1自动节点  2手动节点  3默认节点",required=true)
    private String businessType;        //业务类型 1、自动节点 2、手动节点 3、默认节点
	
	@ApiModelProperty(value = "节点对应的功能id--更新时必传")
    private String nodeFunctionId;           //功能ID号
	
	@NotNull
	@ApiModelProperty(value = "节点显示顺序",required=true)
	private Integer nodeWeight;          //节点顺序
	
	@NotEmpty
	@ApiModelProperty(value = "节点功能名称",required=true)
    private String nodeFunctionName;         //功能名称
    
    /**
     *  新增节点的字段列表
     */
	@NotNull
	@ApiModelProperty(value = "字段列表",required=true)
    List<TraceFunFieldConfigParam> fieldConfigList;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTraceTemplateName() {
		return traceTemplateName;
	}

	public void setTraceTemplateName(String traceTemplateName) {
		this.traceTemplateName = traceTemplateName;
	}

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

	public String getNodeFunctionName() {
		return nodeFunctionName;
	}

	public Integer getNodeWeight() {
		return nodeWeight;
	}

	public void setNodeWeight(Integer nodeWeight) {
		this.nodeWeight = nodeWeight;
	}

	public void setNodeFunctionName(String nodeFunctionName) {
		this.nodeFunctionName = nodeFunctionName;
	}

	public List<TraceFunFieldConfigParam> getFieldConfigList() {
		return fieldConfigList;
	}

	public void setFieldConfigList(List<TraceFunFieldConfigParam> fieldConfigList) {
		this.fieldConfigList = fieldConfigList;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
    
    
}
