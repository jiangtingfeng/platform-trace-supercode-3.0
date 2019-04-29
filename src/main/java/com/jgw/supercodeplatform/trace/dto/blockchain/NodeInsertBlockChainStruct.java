package com.jgw.supercodeplatform.trace.dto.blockchain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "插入区块链数据结构")
public class NodeInsertBlockChainStruct {
    @ApiModelProperty(name = "fieldCode", value = "字段code", example = "FunctionName",required=true)
    private String fieldCode;
    
    @ApiModelProperty(name = "fieldName", value = "字段名称，不需要前端传", example = "批次名称",required=false,hidden=true)
    private String fieldName;
	
    @ApiModelProperty(name = "fieldValue", value = "字段值", example = "搜索",required=true)
    private String fieldValue;
    
    @ApiModelProperty(name = "objectType", value = "对象类型", example = "1001")
    private Integer objectType;

    @ApiModelProperty(name = "objectUniqueValue", value = "对象类型中唯一值，不是对象类型不需要传", example = "唯一主键id")
    private String objectUniqueValue;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	private String fieldType;
    
    private Integer extraCreate;  //是否是后台额外新增的字段而不是页面创建的 1表示是
    
	public Integer getExtraCreate() {
		return extraCreate;
	}

	public void setExtraCreate(Integer extraCreate) {
		this.extraCreate = extraCreate;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public String getObjectUniqueValue() {
		return objectUniqueValue;
	}

	public void setObjectUniqueValue(String objectUniqueValue) {
		this.objectUniqueValue = objectUniqueValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
    
}
