package com.jgw.supercodeplatform.trace.dto.blockchain;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "校验上链信息model")
public class CheckNodeBlockInfoParam {
	@ApiModelProperty(name = "traceBatchInfoIds", value = "批次唯一id集合", required = true)
	private List<String> traceBatchInfoIds;

	public List<String> getTraceBatchInfoIds() {
		return traceBatchInfoIds;
	}

	public void setTraceBatchInfoIds(List<String> traceBatchInfoIds) {
		this.traceBatchInfoIds = traceBatchInfoIds;
	}

}
