package com.jgw.supercodeplatform.trace.pojo.tracefun;

import java.io.Serializable;

public class TraceFunComponent implements Serializable {


    private static final long serialVersionUID = -3706822710364692430L;

    private String componentId; //组件Id
    private String componentName; //组件名称
    private String funId;
    private Integer componentType; //组件类型
    private String organizationId;
    private String createDate;
    private String updateDate;
    private String tableName;

    public int getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(int fieldWeight) {
        this.fieldWeight = fieldWeight;
    }

    private int fieldWeight;


    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public Integer getComponentType() {
        return componentType;
    }

    public void setComponentType(Integer componentType) {
        this.componentType = componentType;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
