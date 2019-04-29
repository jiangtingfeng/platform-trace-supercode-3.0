package com.jgw.supercodeplatform.trace.dto.PlatformFun;

import io.swagger.annotations.ApiModelProperty;

public class MassifBatchInfo  {

    @ApiModelProperty(value = "批次名称")
    private String traceBatchName;
    @ApiModelProperty(value = "批次号")
    private String traceBatchId;

    @ApiModelProperty(value = "操作时间")
    private String time;

    @ApiModelProperty(value = "操作信息")
    private String nodeInfo; //

    @ApiModelProperty(value = "批次id")
    private String traceBatchInfoId;

    public String getTraceBatchInfoId() {
        return traceBatchInfoId;
    }

    public void setTraceBatchInfoId(String traceBatchInfoId) {
        this.traceBatchInfoId = traceBatchInfoId;
    }

    public MassifBatchInfo(){}

    public MassifBatchInfo(String traceBatchId, String traceBatchName){
        this.traceBatchId=traceBatchId;
        this.traceBatchName=traceBatchName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }
}
