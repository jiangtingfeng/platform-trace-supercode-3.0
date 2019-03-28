package com.jgw.supercodeplatform.trace.enums;

/**
 * 组件类型
 *
 * @author wzq
 * @date: 2019-03-28
 */
public enum  ComponentTypeEnum {

    GroupCompent(1,"分组组件"),DeviceCompent(3,"设备组件"),
    NestCompent(2,"嵌套组件"), MaterielCompent(4,"物料组件");

    private final int key;
    private final String value;

    private ComponentTypeEnum(int key,String value){
        this.key=key;
        this.value=value;
    }

    public static boolean isNestComponent(int val)
    {
        return val%2==0;
    }

    public static ComponentTypeEnum getEnumByKey(int key){

        for(ComponentTypeEnum temp:ComponentTypeEnum.values()){
            if(temp.getKey() == key){
                return temp;
            }
        }
        return null;
    }
    public int getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
