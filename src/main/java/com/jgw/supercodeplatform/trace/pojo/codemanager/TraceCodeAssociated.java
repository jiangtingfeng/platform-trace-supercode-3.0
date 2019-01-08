package com.jgw.supercodeplatform.trace.pojo.codemanager;

import com.jgw.supercodeplatform.trace.dto.codemanager.TraceCodeAssociatedModel;

import java.util.Date;

public class TraceCodeAssociated {
    private Long id;

    private String productId;

    private String productName;

    private Integer associated;

    private Long startNumber;

    private Long endNumber;

    private Long codeTotal;

    private Date createTime;

    private Date updateTime;

    private String organizationId;

    private Long SBatchId;

    private String traceBatchInfoId;

    private String traceBatchName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getAssociated() {
        return associated;
    }

    public void setAssociated(Integer associated) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public Long getSBatchId() {
        return SBatchId;
    }

    public void setSBatchId(Long SBatchId) {
        this.SBatchId = SBatchId;
    }

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId == null ? null : traceBatchInfoId.trim();
    }

    public String getTraceBatchName() {
        return traceBatchName;
    }

    public void setTraceBatchName(String traceBatchName) {
        this.traceBatchName = traceBatchName == null ? null : traceBatchName.trim();
    }

    public TraceCodeAssociated() {
    }

    public TraceCodeAssociated(TraceCodeAssociatedModel t) {
        this.productId = t.getProductId();
        this.productName = t.getProductName();
        this.associated = t.getAssociated().getAssociated();
        this.startNumber = t.getStartNumber();
        this.endNumber = t.getEndNumber();
        this.codeTotal = t.getCodeTotal();
        this.organizationId = t.getOrganizationId();
        this.SBatchId = t.getsBatchId();
        this.traceBatchInfoId = t.getTraceBatchInfoId();
        this.traceBatchName = t.getTraceBatchName();
    }

    public TraceCodeAssociated(String productId, String productName, Integer associated, Long startNumber, Long endNumber, Long codeTotal, Date createTime, Date updateTime, String organizationId, Long SBatchId, String traceBatchInfoId, String traceBatchName) {
        this.productId = productId;
        this.productName = productName;
        this.associated = associated;
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.codeTotal = codeTotal;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.organizationId = organizationId;
        this.SBatchId = SBatchId;
        this.traceBatchInfoId = traceBatchInfoId;
        this.traceBatchName = traceBatchName;
    }
}