package com.jgw.supercodeplatform.trace.dto.common;

import io.swagger.annotations.ApiModelProperty;

public class DisableFlagDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态标记：启用值为0，禁用值为1")
    private Integer disableFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }
}
