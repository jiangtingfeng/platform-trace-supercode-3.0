package com.jgw.supercodeplatform.trace.dto.PlatformFun;

public class MassifBatchInfo  {

    private String traceBatchName;//批次名称
    private String traceBatchId;//批次号
    private String time;
    private String nodeInfo; //

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
