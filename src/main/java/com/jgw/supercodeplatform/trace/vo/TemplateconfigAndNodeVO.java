package com.jgw.supercodeplatform.trace.vo;

import java.util.List;
import java.util.Map;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "溯源功能-节点业务数据")
public class TemplateconfigAndNodeVO {
	
	@ApiModelProperty(value = "节点功能名称")
    private String nodeFunctionName;         //功能名称

	@ApiModelProperty(value = "业务类型 ，1自动节点  2手动节点  3默认节点")
    private String businessType;        //业务类型 1、自动节点 2、手动节点 3、默认节点
	
	@ApiModelProperty(value = "节点对应的功能id")
    private String nodeFunctionId;           //功能ID号
	
	@ApiModelProperty(value = "节点业务数据")
	private List<Map<String, Object>> nodeData;
	
	@ApiModelProperty(value = "节点字段名称集合")
	private List<TraceFunFieldConfig> fieldNames;
	
	
	public String getNodeFunctionName() {
		return nodeFunctionName;
	}

	public void setNodeFunctionName(String nodeFunctionName) {
		this.nodeFunctionName = nodeFunctionName;
	}

	public List<Map<String, Object>> getNodeData() {
		return nodeData;
	}

	public List<TraceFunFieldConfig> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<TraceFunFieldConfig> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public void setNodeData(List<Map<String, Object>> nodeData) {
		this.nodeData = nodeData;
	}

	public String getNodeFunctionId() {
		return nodeFunctionId;
	}

	public void setNodeFunctionId(String nodeFunctionId) {
		this.nodeFunctionId = nodeFunctionId;
	}
	
}
