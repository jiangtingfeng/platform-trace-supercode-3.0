package com.jgw.supercodeplatform.trace.enums;

import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;

/**
 * 批次表类型
 *
 * @author wzq
 * @date: 2019-03-28
 */
public enum  BatchTableType {

    ProductBatch(1,"trace_batchinfo"),
    ObjectBatch(2,"trace_objectbatchinfo");

    private final int key;
    private final String value;

    private BatchTableType(int key,String value){
        this.key=key;
        this.value=value;
    }


    public int getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    public static BatchTableType getBatchTableType(ObjectTypeEnum objectTypeEnum){
        BatchTableType batchTableType=ProductBatch;
        if(objectTypeEnum!=null){
            switch (objectTypeEnum){
                case MassifBatch:
                    batchTableType=ObjectBatch;
                default:
                    break;
            }
        }
        return batchTableType;
    }
}
