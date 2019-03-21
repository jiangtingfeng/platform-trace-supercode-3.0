package com.jgw.supercodeplatform.trace.dto.PlatformFun;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public  class BatchNamedRuleField
{
    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段Code")
    private String fieldCode;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
}