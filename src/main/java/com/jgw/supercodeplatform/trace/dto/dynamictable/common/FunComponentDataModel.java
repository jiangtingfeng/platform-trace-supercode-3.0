package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FunComponentDataModel {

    @ApiModelProperty(name = "data", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
    private List<FieldBusinessParam> fields;

    @ApiModelProperty(value="组件名称")
    private String componentName;

    @ApiModelProperty(value = "组件类型")
    private String componentType;

    @ApiModelProperty(value = "组件id")
    private String componentId;

    public List<FieldBusinessParam> getFields() {
        return fields;
    }

    public void setFields(List<FieldBusinessParam> fields) {
        this.fields = fields;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
}
