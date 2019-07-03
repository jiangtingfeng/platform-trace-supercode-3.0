package com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class BaseAreaDto {
    @ApiModelProperty("区域id")
    private String areaId;
    @ApiModelProperty("区域名称")
    private String areaName;
    @ApiModelProperty("结果类型:0-分区；1-大棚")
    private Integer resultType;//0-分区；1-大棚
    @ApiModelProperty("面积大小")
    private BigDecimal massArea;
    @ApiModelProperty("面积单位")
    private String areaUnit;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public BigDecimal getMassArea() {
        return massArea;
    }

    public void setMassArea(BigDecimal massArea) {
        this.massArea = massArea;
    }

    public String getAreaUnit() {
        return areaUnit;
    }

    public void setAreaUnit(String areaUnit) {
        this.areaUnit = areaUnit;
    }

    public String getAreaUnitName() {
        return areaUnitName;
    }

    public void setAreaUnitName(String areaUnitName) {
        this.areaUnitName = areaUnitName;
    }

    public List<BaseAreaDto> getChild() {
        return child;
    }

    public void setChild(List<BaseAreaDto> child) {
        this.child = child;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ApiModelProperty("面积单位名称")
    private String areaUnitName;
    private List<BaseAreaDto> child;

    @ApiModelProperty(value = "种植产品")
    private String productName;//产品名称
}
