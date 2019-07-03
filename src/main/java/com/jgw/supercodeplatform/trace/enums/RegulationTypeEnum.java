package com.jgw.supercodeplatform.trace.enums;

/**
 * 功能类型
 *
 * @author wzq
 * @date: 2019-03-28
 */
public enum RegulationTypeEnum {

    ProcedureNode(1,"过程节点"),
    ControlNode(2,"控制节点"),
    SingleForm(3,"单一表单");


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
