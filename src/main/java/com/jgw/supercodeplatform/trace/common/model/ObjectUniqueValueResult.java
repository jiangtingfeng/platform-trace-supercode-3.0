package com.jgw.supercodeplatform.trace.common.model;

import java.util.Map;

public class ObjectUniqueValueResult {

    private String objectUniqueValue;
    private String field;
    private Map objectMap;

    public String getObjectUniqueValue() {
        return objectUniqueValue;
    }

    public void setObjectUniqueValue(String objectUniqueValue) {
        this.objectUniqueValue = objectUniqueValue;
    }

    public String getField() {
        return field;
    }

    public Map getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map objectMap) {
        this.objectMap = objectMap;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ObjectUniqueValueResult(String objectUniqueValue, String field) {
        this.objectUniqueValue = objectUniqueValue;
        this.field = field;
    }

    public ObjectUniqueValueResult(String objectUniqueValue, String field,Map objectMap) {
        this.objectUniqueValue = objectUniqueValue;
        this.field = field;
        this.objectMap=objectMap;
    }

    public ObjectUniqueValueResult() {
    }
}
