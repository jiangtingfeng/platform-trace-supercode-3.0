package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CertificateInfoParam {
    private Long id;

    private String certificateInfoId;

    @ApiModelProperty(value = "合格证编号")
    private String certificateNumber;

    @ApiModelProperty(value = "合格证名称")
    private String certificateName;

    @ApiModelProperty(value = "合格证模板id")
    private String templateId;

    @ApiModelProperty(value = "合格证模板名称")
    private String templateName;

    private String createId;

    @ApiModelProperty(value = "创建人")
    private String createMan;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Date updateTime;

    @ApiModelProperty(value = "状态：禁用为1，启用为0")
    private Integer disableFlag;

    @ApiModelProperty(value = "字段数据：json格式字符串")
    private String certificateInfoData;

    @ApiModelProperty(value = "打印宽度")
    private Integer printWidth;

    @ApiModelProperty(value = "打印高度")
    private Integer printHeight;

    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateInfoId() {
        return certificateInfoId;
    }

    public void setCertificateInfoId(String certificateInfoId) {
        this.certificateInfoId = certificateInfoId == null ? null : certificateInfoId.trim();
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
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

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getCertificateInfoData() {
        return certificateInfoData;
    }

    public void setCertificateInfoData(String certificateInfoData) {
        this.certificateInfoData = certificateInfoData == null ? null : certificateInfoData.trim();
    }

    public Integer getPrintWidth() {
        return printWidth;
    }

    public void setPrintWidth(Integer printWidth) {
        this.printWidth = printWidth;
    }

    public Integer getPrintHeight() {
        return printHeight;
    }

    public void setPrintHeight(Integer printHeight) {
        this.printHeight = printHeight;
    }
}