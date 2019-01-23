package com.jgw.supercodeplatform.trace.dto.template.update;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "注意前面带**-字段，溯源功能-溯源模板更新子参数")
public class TraceFunTemplateconfigUpdateSubParam {
	@ApiModelProperty(value = "模板配置主键id")
    private Long id;  //主键id
	
	@NotEmpty
	@ApiModelProperty(value = "节点业务类型 ，1自动节点  2手动节点  3默认节点", required = true)
    private String businessType;        //业务类型 1、自动节点 2、手动节点 3、默认节点
	
	@ApiModelProperty(value = "**-节点功能id",notes="新增节点不传，修改的节点必传")
    private String nodeFunctionId;           //功能ID号
	
	@NotEmpty
	@ApiModelProperty(value = "节点功能名称",required = true,notes="前端手动录入必传参数")
    private String nodeFunctionName;         //功能名称
    
	@NotNull
	@ApiModelProperty(value = "节点操作类型 ，1新增节点 2新增节点字段 3删除节点", required = true)
	private Integer operateType;
	
	@ApiModelProperty(value = "节点显示顺序",required=true)
	private Integer nodeWeight;          //节点顺序
    /**
     * 节点的字段列表
     */
	@NotNull
	@ApiModelProperty(value = "字段列表")
    private List<TraceFunFieldConfigParam> fieldConfigList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessTypes(String businessType) {
		this.businessType = businessType;
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

	public Integer getNodeWeight() {
		return nodeWeight;
	}

	public void setNodeWeight(Integer nodeWeight) {
		this.nodeWeight = nodeWeight;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public List<TraceFunFieldConfigParam> getFieldConfigList() {
		return fieldConfigList;
	}

	public void setFieldConfigList(List<TraceFunFieldConfigParam> fieldConfigList) {
		this.fieldConfigList = fieldConfigList;
	}
}
