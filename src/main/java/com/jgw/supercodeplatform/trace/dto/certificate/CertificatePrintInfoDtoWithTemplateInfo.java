package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CertificatePrintInfoDtoWithTemplateInfo {
    private Long id;


    private String certificateInfoId;

    @ApiModelProperty(value = "合格证id")
    private String certificateId;

    @ApiModelProperty(value = "合格证编号")
    private String certificateNumber;

    @ApiModelProperty(value = "合格证名称")
    private String certificateName;


    @ApiModelProperty(value = "打印张数")
    private Integer printQuantity;


    @ApiModelProperty(value = "打印设备")
    private String printDrive;


    private String createId;

    @ApiModelProperty(value = "打印时间")
    private Date createTime;

    @ApiModelProperty(value = "打印人")
    private String createMan;


    @ApiModelProperty(value = "打印时信息")
    private String certificateInfoData;


    private  Integer printHeight;
    private  Integer printWidth;


    public Integer getPrintHeight() {
        return printHeight;
    }

    public void setPrintHeight(Integer printHeight) {
        this.printHeight = printHeight;
    }

    public Integer getPrintWidth() {
        return printWidth;
    }

    public void setPrintWidth(Integer printWidth) {
        this.printWidth = printWidth;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateInfoData() {
        return certificateInfoData;
    }

    public void setCertificateInfoData(String certificateInfoData) {
        this.certificateInfoData = certificateInfoData;
    }

    public String getPrintDrive() {
        return printDrive;
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
        this.certificateName = certificateName;
    }

    public void setPrintDrive(String printDrive) {
        this.printDrive = printDrive;
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

    public Integer getPrintQuantity() {
        return printQuantity;
    }

    public void setPrintQuantity(Integer printQuantity) {
        this.printQuantity = printQuantity;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }
}