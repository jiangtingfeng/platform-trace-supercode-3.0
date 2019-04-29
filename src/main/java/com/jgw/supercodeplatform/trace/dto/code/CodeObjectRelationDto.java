package com.jgw.supercodeplatform.trace.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CodeObjectRelationDto {

    @ApiModelProperty(value = "对象配置id")
    @NotNull
    private Integer objectConfigId;

    @ApiModelProperty(value = "关联类型 1、按照号码段 2、单码 3、生码批次号")
    private Integer relationType;

    @ApiModelProperty(value = "单码值")
    private String singleCodes;

    @ApiModelProperty(value = "批次值")
    private Long globalBatchId;

    @ApiModelProperty(value = "开始码的值")
    private String startCode;

    @ApiModelProperty(value = "结束码的值")
    private String endCode;

    public List<ObjectPropertyDto> getObjectPropertyDtoList() {
        return objectPropertyDtoList;
    }

    public void setObjectPropertyDtoList(List<ObjectPropertyDto> objectPropertyDtoList) {
        this.objectPropertyDtoList = objectPropertyDtoList;
    }

    @ApiModelProperty(name = "objectPropertyDtoList",value = "对象属性值")
    @Valid
    @Size(min = 1)
    private List<ObjectPropertyDto> objectPropertyDtoList;

}
