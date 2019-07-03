package com.jgw.supercodeplatform.trace.dto.tracebatch;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TraceBatchNodeParam {

    @ApiModelProperty(name = "批次节点模型")
    private List<TraceBatchNodeDto> batchNodeDtos;

    public List<TraceBatchNodeDto> getBatchNodeDtos() {
        return batchNodeDtos;
    }

    public void setBatchNodeDtos(List<TraceBatchNodeDto> batchNodeDtos) {
        this.batchNodeDtos = batchNodeDtos;
    }
}
