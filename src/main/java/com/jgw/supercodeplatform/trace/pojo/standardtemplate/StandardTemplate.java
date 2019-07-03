package com.jgw.supercodeplatform.trace.pojo.standardtemplate;

import java.util.Date;

public class StandardTemplate {
    private Long id;

    private String templateId;

    private Integer categoryId;

    private String categoryName;

    private Integer level1TypeId;

    private String level1TypeName;

    private Integer level2TypeId;

    private String level2TypeName;

    private String templateNumber;

    private Integer disableFlag;

    private String createId;

    private String createMan;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getLevel1TypeId() {
        return level1TypeId;
    }

    public void setLevel1TypeId(Integer level1TypeId) {
        this.level1TypeId = level1TypeId;
    }

    public String getLevel1TypeName() {
        return level1TypeName;
    }

    public void setLevel1TypeName(String level1TypeName) {
        this.level1TypeName = level1TypeName == null ? null : level1TypeName.trim();
    }

    public Integer getLevel2TypeId() {
        return level2TypeId;
    }

    public void setLevel2TypeId(Integer level2TypeId) {
        this.level2TypeId = level2TypeId;
    }

    public String getLevel2TypeName() {
        return level2TypeName;
    }

    public void setLevel2TypeName(String level2TypeName) {
        this.level2TypeName = level2TypeName == null ? null : level2TypeName.trim();
    }

    public String getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(String templateNumber) {
        this.templateNumber = templateNumber == null ? null : templateNumber.trim();
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}