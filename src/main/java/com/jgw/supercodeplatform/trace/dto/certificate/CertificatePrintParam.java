package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

public class CertificatePrintParam {

    @ApiModelProperty(value = "合格证id")
    private String certificateInfoId;

    @ApiModelProperty(value = "打印张数")
    private Integer printQuantity;

    @ApiModelProperty(value = "打印设备")
    private String printDrive;

    public String getCertificateInfoId() {
        return certificateInfoId;
    }

    public void setCertificateInfoId(String certificateInfoId) {
        this.certificateInfoId = certificateInfoId;
    }

    public Integer getPrintQuantity() {
        return printQuantity;
    }

    public void setPrintQuantity(Integer printQuantity) {
        this.printQuantity = printQuantity;
    }
}
