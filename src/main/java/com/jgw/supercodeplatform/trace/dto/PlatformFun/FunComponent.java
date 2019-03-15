package com.jgw.supercodeplatform.trace.dto.PlatformFun;


import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public  class FunComponent
{
    @ApiModelProperty(value="组件名称")
    private String componentName;

    @ApiModelProperty(value = "组件类型")
    private int componentType;

    @ApiModelProperty(name = "traceFunFieldConfigModel", value = "功能溯源对象模型数组")
    List<TraceFunFieldConfigParam> traceFunFieldConfigModel;
}