package com.jgw.supercodeplatform.trace.common.model;

public class ObjectUniqueValueResult {

    private String objectUniqueValue;
    private String field;

    public String getObjectUniqueValue() {
        return objectUniqueValue;
    }

    public void setObjectUniqueValue(String objectUniqueValue) {
        this.objectUniqueValue = objectUniqueValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ObjectUniqueValueResult(String objectUniqueValue, String field) {
        this.objectUniqueValue = objectUniqueValue;
        this.field = field;
    }

    public ObjectUniqueValueResult() {
    }
}
