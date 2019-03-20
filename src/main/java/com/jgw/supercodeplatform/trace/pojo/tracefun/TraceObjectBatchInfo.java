package com.jgw.supercodeplatform.trace.pojo.tracefun;

import java.io.Serializable;

public class TraceObjectBatchInfo implements Serializable {

    private static final long serialVersionUID = -6894942493146216744L;

    private String traceBatchInfoId;//唯一id
    private String organizationId;//组织id

    private String traceBatchName;//批次名称
    private String traceBatchId;//批次号

    private String traceTemplateId;//溯源模板号
    private String traceTemplateName;//溯源模板名称
    private String h5TrancePageId;//H5溯源页模板Id号
    private String h5TempleteName;//H5溯源页模板名称
    private String createId;//创建人id
    private String createMan;//创建人
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private Integer nodeDataCount;
    private String traceBatchPlatformId;

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getTraceBatchName() {
        return traceBatchName;
    }

    public void setTraceBatchName(String traceBatchName) {
        this.traceBatchName = traceBatchName;
    }

    public String getTraceBatchId() {
        return traceBatchId;
    }

    public void setTraceBatchId(String traceBatchId) {
        this.traceBatchId = traceBatchId;
    }

    public String getTraceTemplateId() {
        return traceTemplateId;
    }

    public void setTraceTemplateId(String traceTemplateId) {
        this.traceTemplateId = traceTemplateId;
    }

    public String getTraceTemplateName() {
        return traceTemplateName;
    }

    public void setTraceTemplateName(String traceTemplateName) {
        this.traceTemplateName = traceTemplateName;
    }

    public String getH5TrancePageId() {
        return h5TrancePageId;
    }

    public void setH5TrancePageId(String h5TrancePageId) {
        this.h5TrancePageId = h5TrancePageId;
    }

    public String getH5TempleteName() {
        return h5TempleteName;
    }

    public void setH5TempleteName(String h5TempleteName) {
        this.h5TempleteName = h5TempleteName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNodeDataCount() {
        return nodeDataCount;
    }

    public void setNodeDataCount(Integer nodeDataCount) {
        this.nodeDataCount = nodeDataCount;
    }

    public String getTraceBatchPlatformId() {
        return traceBatchPlatformId;
    }

    public void setTraceBatchPlatformId(String traceBatchPlatformId) {
        this.traceBatchPlatformId = traceBatchPlatformId;
    }
}
