package com.jgw.supercodeplatform.trace.pojo.tracefun;

import java.io.Serializable;

public class TraceBatchNamed implements Serializable {

    private static final long serialVersionUID = -1358934963927254074L;

    private Long id;
    private String fieldId; //命名规则字段Id
    private String fieldName; //命名规则字段名称
    private String fieldCode; //命名规则字段代码
    private String createDate;
    private String updateDate;
    private String funId;
    private String fieldFormat;

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    private boolean disableFlag;

    public TraceBatchNamed(){}

    public TraceBatchNamed(String fieldName,String fieldCode,String funId,String fieldFormat,boolean disableFlag){
        this.fieldName=fieldName;
        this.fieldCode=fieldCode;
        this.funId=funId;
        this.fieldFormat=fieldFormat;
        this.disableFlag=disableFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
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

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }
}
