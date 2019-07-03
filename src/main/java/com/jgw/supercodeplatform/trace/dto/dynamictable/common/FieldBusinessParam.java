package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

@ApiModel(value = "字段业务数据")
public class FieldBusinessParam {
	@NotEmpty
    @ApiModelProperty(name = "fieldCode", value = "字段code", example = "FunctionName",required=true)
    private String fieldCode;
    
	@NotEmpty
    @ApiModelProperty(name = "fieldValue", value = "字段值", example = "搜索",required=true)
    private String fieldValue;
    
    @ApiModelProperty(name = "objectType", value = "对象类型，不是对象类型不需要传", example = "1001")
    private String objectType;

    @ApiModelProperty(name = "objectUniqueValue", value = "对象类型中唯一值，不是对象类型不需要传", example = "唯一主键id")
    private String objectUniqueValue;

    public FieldBusinessParam(){}

	public FieldBusinessParam(String fieldCode,String fieldValue){
    	this.fieldCode=fieldCode;
    	this.fieldValue=fieldValue;
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
		Pattern pattern = Pattern.compile("[0-9]*");
		Integer val=0;
		if(StringUtils.isNotEmpty(objectType) && pattern.matcher(objectType).matches()){
			val= Integer.parseInt(objectType);
		}
		return val;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectUniqueValue() {
		return objectUniqueValue;
	}

	public void setObjectUniqueValue(String objectUniqueValue) {
		this.objectUniqueValue = objectUniqueValue;
	}
    
    
}
