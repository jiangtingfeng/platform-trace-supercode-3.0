package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CertificateInfoCopyParam {
    private Long id;

    private String certificateInfoId;
    private String certificateName;


    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
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
        this.certificateInfoId = certificateInfoId;
    }
}