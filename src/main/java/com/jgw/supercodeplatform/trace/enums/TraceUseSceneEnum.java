package com.jgw.supercodeplatform.trace.enums;

public enum TraceUseSceneEnum {

    CreateBatch(1,"创建关联对象批次"),
    CreateBatchInheritNodeData(2,"批次继承，创建新批次并继承溯源信息"),
    BatchMixWithSameObject(3,"批次混合，多个相同关联对象批次混合并创建新对象批次"),
    BatchMixWithDistinctObject(4,"批次混合，多个不同的关联对象批次混合并创建新对象批次"),
    BatchDivide(5,"批次分裂，由关联对象分裂成为多个嵌套对象的批次");

    private final int key;
    private final String value;

    private TraceUseSceneEnum(int key,String value){
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
