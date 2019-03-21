package com.jgw.supercodeplatform.trace.pojo.tracefun;

import java.io.Serializable;

public class TraceBatchRelation implements Serializable {


    private static final long serialVersionUID = -951599081019898457L;
    private Long id;
    private String batchRelationId;
    private String currentBatchId;
    private String parentBatchId;
    private String createDate;
    private String updateDate;
    private String businessTableName;
    private String parentBusinessTableName;
    private String currentBatchType;
    private String parentBatchType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchRelationId() {
        return batchRelationId;
    }

    public void setBatchRelationId(String batchRelationId) {
        this.batchRelationId = batchRelationId;
    }

    public String getCurrentBatchId() {
        return currentBatchId;
    }

    public void setCurrentBatchId(String currentBatchId) {
        this.currentBatchId = currentBatchId;
    }

    public String getParentBatchId() {
        return parentBatchId;
    }

    public void setParentBatchId(String parentBatchId) {
        this.parentBatchId = parentBatchId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getBusinessTableName() {
        return businessTableName;
    }

    public void setBusinessTableName(String businessTableName) {
        this.businessTableName = businessTableName;
    }

    public String getParentBusinessTableName() {
        return parentBusinessTableName;
    }

    public void setParentBusinessTableName(String parentBusinessTableName) {
        this.parentBusinessTableName = parentBusinessTableName;
    }

    public String getCurrentBatchType() {
        return currentBatchType;
    }

    public void setCurrentBatchType(String currentBatchType) {
        this.currentBatchType = currentBatchType;
    }

    public String getParentBatchType() {
        return parentBatchType;
    }

    public void setParentBatchType(String parentBatchType) {
        this.parentBatchType = parentBatchType;
    }
}
