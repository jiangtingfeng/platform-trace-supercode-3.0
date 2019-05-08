package com.jgw.supercodeplatform.trace.pojo.producttesting;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ProductTestingItem {
    private Integer id;

    private String productTestingItemId;

    private String productTestingId;

    @ApiModelProperty(value = "检测项id")
    private String testingTypeId;

    @ApiModelProperty(value = "检测标准")
    private String testingStandard;

    @ApiModelProperty(value = "检测结果")
    private Integer testingResult;

    @ApiModelProperty(value = "检测日期")
    private Date testingDate;

    @ApiModelProperty(value = "检测依据")
    private String testingAccording;

    @ApiModelProperty(value = "检测数量")
    private Integer quantity;

    @ApiModelProperty(value = "检测方法")
    private String testingMethod;

    @ApiModelProperty(value = "检测设备")
    private String testingDevice;

    @ApiModelProperty(value = "检测数据excel")
    private String excel;

    @ApiModelProperty(value = "图片")
    private String imgs;

    @ApiModelProperty(value = "pdf")
    private String pdfs;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检测设备名称")
    private String testingDeviceName;

    public String getTestingDeviceName() {
        return testingDeviceName;
    }

    public void setTestingDeviceName(String testingDeviceName) {
        this.testingDeviceName = testingDeviceName;
    }

    public String getPdfImgs() {
        return pdfImgs;
    }

    public void setPdfImgs(String pdfImgs) {
        this.pdfImgs = pdfImgs;
    }

    private String pdfImgs;

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

    public String getTestingStandard() {
        return testingStandard;
    }

    public void setTestingStandard(String testingStandard) {
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