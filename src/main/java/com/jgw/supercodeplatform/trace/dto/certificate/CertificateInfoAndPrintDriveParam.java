package com.jgw.supercodeplatform.trace.dto.certificate;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CertificateInfoAndPrintDriveParam  extends CertificateInfoParam{
    private String printDrive;

    public String getPrintDrive() {
        return printDrive;
    }

    public void setPrintDrive(String printDrive) {
        this.printDrive = printDrive;
    }
}