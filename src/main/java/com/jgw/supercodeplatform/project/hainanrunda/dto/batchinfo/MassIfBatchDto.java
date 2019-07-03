package com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo;

import io.swagger.annotations.ApiModelProperty;

public class MassIfBatchDto {
    @ApiModelProperty(value = "种植产品")
    private String productName;//产品名称

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoragePlaceId() {
        return storagePlaceId;
    }

    public void setStoragePlaceId(String storagePlaceId) {
        this.storagePlaceId = storagePlaceId;
    }

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    private String storagePlaceId;

    private String traceBatchInfoId;


}
