package com.jgw.supercodeplatform.trace.dto.tracebatch;

import io.swagger.annotations.ApiModelProperty;

public class TraceBatchNodeDto {

    @ApiModelProperty(name = "批次id")
    private String traceBatchInfoId;//唯一id

    @ApiModelProperty(name = "节点数量")
    private Integer nodeDataCount;

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    public Integer getNodeDataCount() {
        return nodeDataCount;
    }

    public void setNodeDataCount(Integer nodeDataCount) {
        this.nodeDataCount = nodeDataCount;
    }
}
