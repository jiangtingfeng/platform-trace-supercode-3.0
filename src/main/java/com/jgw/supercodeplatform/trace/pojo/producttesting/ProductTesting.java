package com.jgw.supercodeplatform.trace.pojo.producttesting;

import java.util.Date;

public class ProductTesting {
    private Integer id;

    private String productTestingId;

    private String organizationId;

    private String thirdpartyOrganizationId;

    private String productID;

    private String traceBatchInfoId;

    private String testingDate;

    private String testingMan;

    private String createMan;

    private Date createTime;

    private String createId;

    public Integer getTestingType() {
        return testingType;
    }

    public void setTestingType(Integer testingType) {
        this.testingType = testingType;
    }

    private Integer testingType;

    private String certifyNumber;

    private String excel;

    private String traceBatchInfoName;

    private  String productName;

    private String organizationName;

    private String organizeId;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getTestingManName() {
        return testingManName;
    }

    public void setTestingManName(String testingManName) {
        this.testingManName = testingManName;
    }

    private String testingManName;

    public String getTraceBatchInfoName() {
        return traceBatchInfoName;
    }

    public void setTraceBatchInfoName(String traceBatchInfoName) {
        this.traceBatchInfoName = traceBatchInfoName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCertifyNumber() {
        return certifyNumber;
    }

    public void setCertifyNumber(String certifyNumber) {
        this.certifyNumber = certifyNumber;
    }

    public String getExcel() {
        return excel;
    }

    public void setExcel(String excel) {
        this.excel = excel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductTestingId() {
        return productTestingId;
    }

    public void setProductTestingId(String productTestingId) {
        this.productTestingId = productTestingId == null ? null : productTestingId.trim();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public String getThirdpartyOrganizationId() {
        return thirdpartyOrganizationId;
    }

    public void setThirdpartyOrganizationId(String thirdpartyOrganizationId) {
        this.thirdpartyOrganizationId = thirdpartyOrganizationId == null ? null : thirdpartyOrganizationId.trim();
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID == null ? null : productID.trim();
    }

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId == null ? null : traceBatchInfoId.trim();
    }

    public String getTestingDate() {
        return testingDate;
    }

    public void setTestingDate(String testingDate) {
        this.testingDate = testingDate == null ? null : testingDate.trim();
    }

    public String getTestingMan() {
        return testingMan;
    }

    public void setTestingMan(String testingMan) {
        this.testingMan = testingMan == null ? null : testingMan.trim();
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }
}