package com.jgw.supercodeplatform.trace.pojo.tracefun;

import java.io.Serializable;

public class TraceFunRegulation  implements Serializable {

    private static final long serialVersionUID = -3169212712471824749L;

    private String regulationId;
    private String funId;
    private int objectAssociatedType;
    private int regulationType;
    private boolean multipleInput;
    private int useSceneType;
    private String batchNamingLinkCharacter;
    private String batchNamingRule;
    private int batchTimeControl;
    private int createBatchType;
    private String splittingRule;
    private String createDate;
    private String updateDate;
    private String functionName;


    public String getRegulationId() {
        return regulationId;
    }

    public void setRegulationId(String regulationId) {
        this.regulationId = regulationId;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public int getObjectAssociatedType() {
        return objectAssociatedType;
    }

    public void setObjectAssociatedType(int objectAssociatedType) {
        this.objectAssociatedType = objectAssociatedType;
    }

    public int getRegulationType() {
        return regulationType;
    }

    public void setRegulationType(int regulationType) {
        this.regulationType = regulationType;
    }

    public boolean isMultipleInput() {
        return multipleInput;
    }

    public void setMultipleInput(boolean multipleInput) {
        this.multipleInput = multipleInput;
    }

    public int getUseSceneType() {
        return useSceneType;
    }

    public void setUseSceneType(int useSceneType) {
        this.useSceneType = useSceneType;
    }

    public String getBatchNamingLinkCharacter() {
        return batchNamingLinkCharacter;
    }

    public void setBatchNamingLinkCharacter(String batchNamingLinkCharacter) {
        this.batchNamingLinkCharacter = batchNamingLinkCharacter;
    }

    public String getBatchNamingRule() {
        return batchNamingRule;
    }

    public void setBatchNamingRule(String batchNamingRule) {
        this.batchNamingRule = batchNamingRule;
    }

    public int getBatchTimeControl() {
        return batchTimeControl;
    }

    public void setBatchTimeControl(int batchTimeControl) {
        this.batchTimeControl = batchTimeControl;
    }

    public int getCreateBatchType() {
        return createBatchType;
    }

    public void setCreateBatchType(int createBatchType) {
        this.createBatchType = createBatchType;
    }

    public String getSplittingRule() {
        return splittingRule;
    }

    public void setSplittingRule(String splittingRule) {
        this.splittingRule = splittingRule;
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

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
