package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FunComponentDataModel {

    public List<List<FieldBusinessParam>> getFieldRows() {
        return fieldRows;
    }

    public void setFieldRows(List<List<FieldBusinessParam>> fieldRows) {
        this.fieldRows = fieldRows;
    }

    @ApiModelProperty(name = "data", value = "新增或修改表实际字段数据--新增获取修改接口使用", example = "", required = false)
    private List<List<FieldBusinessParam>> fieldRows;

    @ApiModelProperty(value="组件名称")
    private String componentName;

    @ApiModelProperty(value = "组件类型")
    private int componentType;

    @ApiModelProperty(value = "组件id")
    private String componentId;



    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getComponentType() {
        return componentType;
    }

    public void setComponentType(int componentType) {
        this.componentType = componentType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
}
