package com.jgw.supercodeplatform.trace.dto.zaoyangpeach;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "存放地点")
public class StoragePlaceParam {
    private Integer id;

    private String placeId;

    @ApiModelProperty(value = "存放点名称")
    private String placeName;

    private String currentBatchId;

    @ApiModelProperty(value = "所属分拣点")
    private String sortingPlaceName;

    @ApiModelProperty(value = "所属分拣点id")
    private String sortingPlaceId;

    private Date createTime;

    @ApiModelProperty(value = "负责人")
    private String placeStaff;

    @ApiModelProperty(value = "状态")
    private Integer disableFlag;

    @ApiModelProperty(value = "存放点编号")
    private String placeNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId == null ? null : placeId.trim();
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName == null ? null : placeName.trim();
    }

    public String getCurrentBatchId() {
        return currentBatchId;
    }

    public void setCurrentBatchId(String currentBatchId) {
        this.currentBatchId = currentBatchId == null ? null : currentBatchId.trim();
    }

    public String getSortingPlaceName() {
        return sortingPlaceName;
    }

    public void setSortingPlaceName(String sortingPlaceName) {
        this.sortingPlaceName = sortingPlaceName == null ? null : sortingPlaceName.trim();
    }

    public String getSortingPlaceId() {
        return sortingPlaceId;
    }

    public void setSortingPlaceId(String sortingPlaceId) {
        this.sortingPlaceId = sortingPlaceId == null ? null : sortingPlaceId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPlaceStaff() {
        return placeStaff;
    }

    public void setPlaceStaff(String placeStaff) {
        this.placeStaff = placeStaff == null ? null : placeStaff.trim();
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber == null ? null : placeNumber.trim();
    }
}