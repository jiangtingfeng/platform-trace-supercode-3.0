package com.jgw.supercodeplatform.trace.pojo.tracefun;

public class BaseBatchInfo {


    private String traceBatchInfoId;//唯一id
    private String traceBatchName;//批次名称

    private String massifName;

    public String getMassifName() {
        return massifName;
    }

    public void setMassifName(String massifName) {
        this.massifName = massifName;
    }

    private String productId=null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public BaseBatchInfo(){

    }

    public BaseBatchInfo(String traceBatchInfoId){
        this.traceBatchInfoId=traceBatchInfoId;
    }

    public BaseBatchInfo(String productName,String productId){
        this.productName=productName;
        this.productId=productId;
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
