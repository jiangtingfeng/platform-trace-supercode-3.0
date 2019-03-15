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
}