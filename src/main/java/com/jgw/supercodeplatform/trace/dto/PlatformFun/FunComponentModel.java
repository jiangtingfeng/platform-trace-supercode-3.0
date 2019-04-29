package com.jgw.supercodeplatform.trace.dto.PlatformFun;

import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FunComponentModel {


    @ApiModelProperty(value="组件名称")
    private String componentName;

    @ApiModelProperty(value = "组件类型")
    private Integer componentType;

    @ApiModelProperty(value = "组件id")
    private String componentId;

    private int fieldWeight;

    public int getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(int fieldWeight) {
        this.fieldWeight = fieldWeight;
    }

    @ApiModelProperty(name = "traceFunFieldConfigModel", value = "功能溯源对象模型数组")
    List<TraceFunFieldConfig> traceFunFieldConfigs;


    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Integer getComponentType() {
        return componentType;
    }

    public void setComponentType(Integer componentType) {
        this.componentType = componentType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public List<TraceFunFieldConfig> getTraceFunFieldConfigs() {
        return traceFunFieldConfigs;
    }

    public void setTraceFunFieldConfigs(List<TraceFunFieldConfig> traceFunFieldConfigs) {
        this.traceFunFieldConfigs = traceFunFieldConfigs;
    }
}
