package com.jgw.supercodeplatform.trace.vo;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "溯源功能-节点业务数据")
public class TemplateconfigAndNodeVO {
	
	@ApiModelProperty(value = "节点功能名称")
    private String nodeFunctionName;         //功能名称

	@ApiModelProperty(value = "业务类型 ，1自动节点  2手动节点  3默认节点")
    private String businessTypes;        //业务类型 1、自动节点 2、手动节点 3、默认节点
	
	@ApiModelProperty(value = "节点对应的功能id")
    private String nodeFunctionId;           //功能ID号
	
	@ApiModelProperty(value = "节点业务数据")
	private List<Map<String, Object>> nodeData;

	public String getNodeFunctionName() {
		return nodeFunctionName;
	}

	public void setNodeFunctionName(String nodeFunctionName) {
		this.nodeFunctionName = nodeFunctionName;
	}

	public List<Map<String, Object>> getNodeData() {
		return nodeData;
	}

	public void setNodeData(List<Map<String, Object>> nodeData) {
		this.nodeData = nodeData;
	}

	public String getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getNodeFunctionId() {
		return nodeFunctionId;
	}

	public void setNodeFunctionId(String nodeFunctionId) {
		this.nodeFunctionId = nodeFunctionId;
	}
	
}
