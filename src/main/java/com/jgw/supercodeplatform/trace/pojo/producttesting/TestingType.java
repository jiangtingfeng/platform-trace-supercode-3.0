package com.jgw.supercodeplatform.trace.pojo.producttesting;

import java.util.Date;

public class TestingType {
    private Integer id;

    private String testingTypeId;

    private String organizationId;

    private String createId;

    private String createMan;

    private boolean disableFlag;

    private String testingTypeName;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestingTypeId() {
        return testingTypeId;
    }

    public void setTestingTypeId(String testingTypeId) {
        this.testingTypeId = testingTypeId == null ? null : testingTypeId.trim();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    public boolean getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getTestingTypeName() {
        return testingTypeName;
    }

    public void setTestingTypeName(String testingTypeName) {
        this.testingTypeName = testingTypeName == null ? null : testingTypeName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}