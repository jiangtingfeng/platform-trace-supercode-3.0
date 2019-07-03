package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CertificateInfoReturnParam {
    private Long id;

    private String certificateInfoId;

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
        this.certificateInfoId = certificateInfoId;
    }
}