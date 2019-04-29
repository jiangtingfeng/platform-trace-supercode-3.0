package com.jgw.supercodeplatform.trace.enums;

public enum  PublicObjectEnum {

    MassifInfo("13012","地块"),
    MassifBatch("13013","地块批次");

    private final String key;
    private final String value;

    private PublicObjectEnum(String key,String value){
        this.key=key;
        this.value=value;
    }


    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

}
