package com.jgw.supercodeplatform.trace.dto.codemanager;

import com.jgw.supercodeplatform.trace.enums.AssociatedEnum;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import io.swagger.annotations.ApiModelProperty;

public class TraceCodeAssociatedModel {

    @ApiModelProperty(value = "产品id", required = true)
    private String productId;
    @ApiModelProperty(value = "产品名", required = true)
    private String productName;
    @ApiModelProperty(value = "关联类型：NUMBER_SEGMENT -- 按号段, SINGLE_CODE -- 单码 , SBATCH -- 生成码批次 ;", required = true)
    private AssociatedEnum associated;
    @ApiModelProperty(value = "开始码")
    private Long startNumber;
    @ApiModelProperty(value = "结束码")
    private Long endNumber;
    @ApiModelProperty(value = "开始码")
    private Long codeTotal;
    @ApiModelProperty(value = "组织id",required = true)
    private String organizationId;
    @ApiModelProperty(value = "批次id",required = true)
    private String batchId;
    @ApiModelProperty(value = "批次名",required = true)
    private String batchName;
    @ApiModelProperty(value = "生码批次id")
    private long sBatchId;
    @ApiModelProperty(value = "批次id")
    private String traceBatchInfoId;
    @ApiModelProperty(value = "批次名")
    private String traceBatchName;

    public long getsBatchId() {
        return sBatchId;
    }

    public void setsBatchId(long sBatchId) {
        this.sBatchId = sBatchId;
    }

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    public String getTraceBatchName() {
        return traceBatchName;
    }

    public void setTraceBatchName(String traceBatchName) {
        this.traceBatchName = traceBatchName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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

    public AssociatedEnum getAssociated() {
        return associated;
    }

    public void setAssociated(String associated) throws SuperCodeTraceException {
        if (associated == null || "".equals(associated)) {
            throw new SuperCodeTraceException("码关联类型 is null", 500);
        }
        try {
            this.associated = AssociatedEnum.valueOf(associated.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new SuperCodeTraceException("码关联类型传入错误" + associated, 500);
        }
        if (this.associated == null) {
            throw new SuperCodeTraceException("码关联类型传入错误" + associated, 500);
        }
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }
}