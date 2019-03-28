package com.jgw.supercodeplatform.trace.pojo.tracefun;

public class BaseBatchInfo {


    private String traceBatchInfoId;//唯一id
    private String traceBatchName;//批次名称

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    private long serialNumber;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public BaseBatchInfo(){}

    public BaseBatchInfo(String traceBatchInfoId,String traceBatchName){
        this.traceBatchInfoId=traceBatchInfoId;
        this.traceBatchName=traceBatchName;
    }
    public BaseBatchInfo(String productName){

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
}
