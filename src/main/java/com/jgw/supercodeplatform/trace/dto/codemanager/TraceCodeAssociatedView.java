package com.jgw.supercodeplatform.trace.dto.codemanager;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class TraceCodeAssociatedView {

    @ApiModelProperty(value = "",required = true)
    private String productId;
    @ApiModelProperty(value = "",required = true)
    private String productName;
    @ApiModelProperty(value = "",required = true)
    private Boolean associated;

    private Long startNumber;

    private Long endNumber;

    private Long codeTotal;

    private Date createDate;

    private Date updateDate;

    private String organizationId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Boolean getAssociated() {
        return associated;
    }

    public void setAssociated(Boolean associated) {
        this.associated = associated;
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Long startNumber) {
        this.startNumber = startNumber;
    }

    public Long getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Long endNumber) {
        this.endNumber = endNumber;
    }

    public Long getCodeTotal() {
        return codeTotal;
    }

    public void setCodeTotal(Long codeTotal) {
        this.codeTotal = codeTotal;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }
}