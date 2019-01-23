package com.jgw.supercodeplatform.trace.vo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "溯源功能-新增追溯模板配置字段表")
public class TraceFunTemplateconfigVO {
	@ApiModelProperty(value = "溯源模板主键id")
    private Long id;    //溯源模板主键id
	
	@ApiModelProperty(value = "溯源模板id")
	private String traceTemplateId;      //溯源模板号
	
	@NotNull
	@ApiModelProperty(value = "溯源模板名称")
    private String traceTemplateName;    //溯源模板名称
	
	@NotNull
	@ApiModelProperty(value = "业务类型 ，1自动节点  2手动节点  3默认节点")
    private String businessType;        //业务类型 1、自动节点 2、手动节点 3、默认节点
	
	@ApiModelProperty(value = "节点对应的功能id--更新时必传")
    private String nodeFunctionId;           //功能ID号
	
	@NotNull
	@ApiModelProperty(value = "节点显示顺序")
	private Integer nodeWeight;          //节点顺序
	
	@NotNull
	@ApiModelProperty(value = "节点功能名称")
    private String nodeFunctionName;         //功能名称

	@ApiModelProperty(value = "动态生成的表名")
	private String enTableName;   //动态生成的表名
	
    private String createTime;//创建时间
	private String updateTime;//修改时间
	
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public String getEnTableName() {
		return enTableName;
	}

	public void setEnTableName(String enTableName) {
		this.enTableName = enTableName;
	}

	public Integer getNodeWeight() {
		return nodeWeight;
	}

	public void setNodeWeight(Integer nodeWeight) {
		this.nodeWeight = nodeWeight;
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

	public void setNodeFunctionName(String nodeFunctionName) {
		this.nodeFunctionName = nodeFunctionName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


}
