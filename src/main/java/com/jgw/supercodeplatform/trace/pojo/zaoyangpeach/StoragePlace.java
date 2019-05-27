package com.jgw.supercodeplatform.trace.pojo.zaoyangpeach;

import java.util.Date;

public class StoragePlace {
    private Integer id;

    private String placeId;

    private String placeName;

    private String currentBatchId;

    private String sortingPlaceName;

    private String sortingPlaceId;

    private Date createTime;

    private String placeStaff;

    private Integer disableFlag;

    private String placeNumber;

    private String placeStaffId;

    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

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

    public String getPlaceStaffId() {
        return placeStaffId;
    }

    public void setPlaceStaffId(String placeStaffId) {
        this.placeStaffId = placeStaffId == null ? null : placeStaffId.trim();
    }
}