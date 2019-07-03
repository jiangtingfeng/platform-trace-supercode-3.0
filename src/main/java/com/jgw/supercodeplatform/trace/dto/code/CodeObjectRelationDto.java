package com.jgw.supercodeplatform.trace.dto.code;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CodeObjectRelationDto {

    @NotNull
    @ApiModelProperty(value = "关联类型 1、按照号码段 2、单码 3、生码批次号")
    private String relationType;

    @ApiModelProperty(value = "单码值")
    private String singleCodes;

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getSingleCodes() {
        return singleCodes;
    }

    public void setSingleCodes(String singleCodes) {
        this.singleCodes = singleCodes;
    }

    public Long getGlobalBatchId() {
        return globalBatchId;
    }

    public void setGlobalBatchId(Long globalBatchId) {
        this.globalBatchId = globalBatchId;
    }

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBatchId() {
        return productBatchId;
    }

    public void setProductBatchId(String productBatchId) {
        this.productBatchId = productBatchId;
    }

    public String getProductBatchName() {
        return productBatchName;
    }

    public void setProductBatchName(String productBatchName) {
        this.productBatchName = productBatchName;
    }

    @ApiModelProperty(value = "批次值")
    private Long globalBatchId;

    @ApiModelProperty(value = "开始码的值")
    private String startCode;

    @ApiModelProperty(value = "结束码的值")
    private String endCode;

    @NotNull
    @ApiModelProperty(value = "产品id")
    private String productId;

    @NotNull
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品批次id")
    private String productBatchId;

    @ApiModelProperty(value = "产品批次名称")
    private String productBatchName;


}
