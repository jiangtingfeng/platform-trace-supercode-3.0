package com.jgw.supercodeplatform.trace.enums;

/**
 * @Author corbett
 * @Description //TODO 码关联类型枚举
 * @Date 17:01 2018/12/19
 * @Param
 * @return
 **/
public enum AssociatedEnum {
    NUMBER_SEGMENT(1, "按号段"), SINGLE_CODE(2, "单码"), SBATCH(3, "生成码批次");
    private Integer associated;
    private String associatedName;

    AssociatedEnum(Integer associated, String associatedName) {
        this.associated = associated;
        this.associatedName = associatedName;
    }

    public Integer getAssociated() {
        return associated;
    }

    public String getAssociatedName() {
        return associatedName;
    }
}
