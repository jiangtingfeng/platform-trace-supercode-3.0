package com.jgw.supercodeplatform.trace.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ObjectPropertyDto {
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @ApiModelProperty(value = "对象类型id")
    private Integer objectTypeId;

    @ApiModelProperty(value = "对象名称")
    private String objectName;

    @ApiModelProperty(value = "具体的对象id,如产品id,批次id")
    @NotNull(message = "objectId不能为空")
    private String objectId;

    public Integer getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(Integer objectTypeId) {
        this.objectTypeId = objectTypeId;
    }
}