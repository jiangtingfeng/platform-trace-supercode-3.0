package com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo;


import io.swagger.annotations.ApiModelProperty;

public class PlantingBatchDto {

    @ApiModelProperty(value = "种植批次")
    private String traceBatchName;
    private String traceBatchInfoId;//唯一id

    @ApiModelProperty(value = "种植产品")
    private String productName;//产品名称

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "种植大棚")
    private String massIfName;

    @ApiModelProperty(value = "种植面积")
    private String massArea;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "共计（天）")
    private Integer totalDays;

    public String getMassId() {
        return massId;
    }

    public void setMassId(String massId) {
        this.massId = massId;
    }

    @ApiModelProperty(value = "地块id")
    private String massId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @ApiModelProperty(value = "产品id")
    private String productId;


    public PlantingBatchDto(String traceBatchName, String traceBatchInfoId, String productName){
        this.traceBatchName=traceBatchName;
        this.traceBatchInfoId=traceBatchInfoId;
        this.productName=productName;
    }

    public PlantingBatchDto(String traceBatchName, String traceBatchInfoId, String productName,String pickingBatchId){
        this(traceBatchName,traceBatchInfoId,productName);
        this.pickingBatchId=pickingBatchId;
    }

    public String getPickingBatchId() {
        return pickingBatchId;
    }

    public void setPickingBatchId(String pickingBatchId) {
        this.pickingBatchId = pickingBatchId;
    }

    private String pickingBatchId;


    public PlantingBatchDto(){ }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMassIfName() {
        return massIfName;
    }

    public void setMassIfName(String massIfName) {
        this.massIfName = massIfName;
    }

    public String getMassArea() {
        return massArea;
    }

    public void setMassArea(String massArea) {
        this.massArea = massArea;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getTraceBatchName() {
        return traceBatchName;
    }

    public void setTraceBatchName(String traceBatchName) {
        this.traceBatchName = traceBatchName;
    }

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }




}
