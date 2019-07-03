package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

public class CertificateTemplateReturnParam {
    private Long id;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "模板id")
    private String templateNumber;


    public String getTemplateNumber() {
        return templateNumber;
    }

    public void setTemplateNumber(String templateNumber) {
        this.templateNumber = templateNumber;
    }

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
        this.templateId = templateId;
    }
}
