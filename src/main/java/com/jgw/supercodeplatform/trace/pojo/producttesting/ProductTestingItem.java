package com.jgw.supercodeplatform.trace.pojo.producttesting;

import java.util.Date;

public class ProductTestingItem {
    private Integer id;

    private String productTestingItemId;

    private String productTestingId;

    private String testingTypeId;

    private Integer testingStandard;

    private Integer testingResult;

    private Date testingDate;

    private String testingAccording;

    private Integer quantity;

    private String testingMethod;

    private String testingDevice;

    private String excel;

    private String imgs;

    private String pdfs;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductTestingItemId() {
        return productTestingItemId;
    }

    public void setProductTestingItemId(String productTestingItemId) {
        this.productTestingItemId = productTestingItemId == null ? null : productTestingItemId.trim();
    }

    public String getProductTestingId() {
        return productTestingId;
    }

    public void setProductTestingId(String productTestingId) {
        this.productTestingId = productTestingId == null ? null : productTestingId.trim();
    }

    public String getTestingTypeId() {
        return testingTypeId;
    }

    public void setTestingTypeId(String testingTypeId) {
        this.testingTypeId = testingTypeId == null ? null : testingTypeId.trim();
    }

    public Integer getTestingStandard() {
        return testingStandard;
    }

    public void setTestingStandard(Integer testingStandard) {
        this.testingStandard = testingStandard;
    }

    public Integer getTestingResult() {
        return testingResult;
    }

    public void setTestingResult(Integer testingResult) {
        this.testingResult = testingResult;
    }

    public Date getTestingDate() {
        return testingDate;
    }

    public void setTestingDate(Date testingDate) {
        this.testingDate = testingDate;
    }

    public String getTestingAccording() {
        return testingAccording;
    }

    public void setTestingAccording(String testingAccording) {
        this.testingAccording = testingAccording == null ? null : testingAccording.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(String testingMethod) {
        this.testingMethod = testingMethod == null ? null : testingMethod.trim();
    }

    public String getTestingDevice() {
        return testingDevice;
    }

    public void setTestingDevice(String testingDevice) {
        this.testingDevice = testingDevice == null ? null : testingDevice.trim();
    }

    public String getExcel() {
        return excel;
    }

    public void setExcel(String excel) {
        this.excel = excel == null ? null : excel.trim();
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    public String getPdfs() {
        return pdfs;
    }

    public void setPdfs(String pdfs) {
        this.pdfs = pdfs == null ? null : pdfs.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}