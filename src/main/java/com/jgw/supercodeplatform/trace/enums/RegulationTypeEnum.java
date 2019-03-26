package com.jgw.supercodeplatform.trace.enums;

public enum RegulationTypeEnum {

    ProcedureNode(1,"过程节点"),
    ControlNode(2,"控制节点");


    private final int key;
    private final String value;

    private RegulationTypeEnum(int key,String value){
        this.key=key;
        this.value=value;
    }


    public int getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
