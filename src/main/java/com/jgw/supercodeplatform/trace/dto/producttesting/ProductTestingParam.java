package com.jgw.supercodeplatform.trace.dto.producttesting;

import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItem;

import java.util.Date;
import java.util.List;

public class ProductTestingParam  {
    private Integer id;

    private String productTestingId;

    private String organizationId;

    private String thirdpartyOrganizationId;

    private String productID;

    private String traceBatchInfoId;

    private String testingDate;

    private String testingMan;

    private Date createTime;

    private String createId;

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
        this.createId = createId;
    }

    private List<ProductTestingItem> productTestingItems;



    public List<ProductTestingItem> getProductTestingItems() {
        return productTestingItems;
    }

    public void setProductTestingItems(List<ProductTestingItem> productTestingItems) {
        this.productTestingItems = productTestingItems;
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


}