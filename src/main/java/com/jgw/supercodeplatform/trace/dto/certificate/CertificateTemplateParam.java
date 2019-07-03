package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class CertificateTemplateParam {
    private Long id;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "模板编号")
    private String templateNumber;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "打印宽度")
    private Integer printWidth;

    @ApiModelProperty(value = "打印高度")
    private Integer printHeight;

    private String createId;

    @ApiModelProperty(value = "创建人")
    private String createMan;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Date updateTime;
//    @ApiModelProperty(value = "合格证数据")
//    private String certificateInfoData;

//    public String getCertificateInfoData() {
//        return certificateInfoData;
//    }
//
//    public void setCertificateInfoData(String certificateInfoData) {
//        this.certificateInfoData = certificateInfoData;
//    }

    public List<CertificateTemplateFieldParam> getFieldParamList() {
        return fieldParamList;
    }

    public void setFieldParamList(List<CertificateTemplateFieldParam> fieldParamList) {
        this.fieldParamList = fieldParamList;
    }

    @ApiModelProperty(value = "字段列表")
    private List<CertificateTemplateFieldParam> fieldParamList;

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

    public String getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(String templateNumber) {
        this.templateNumber = templateNumber == null ? null : templateNumber.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
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