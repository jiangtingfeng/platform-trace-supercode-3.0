package com.jgw.supercodeplatform.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "区块链列表数据model")
public class NodeBlockChainInfoListVO {
	
	@ApiModelProperty(value = "主键id")
    private Long blockChainId;
	
	@ApiModelProperty(value = "产品名称")
	private String productName;
	
	@ApiModelProperty(value = "批次唯一id")
	private String traceBatchInfoId;
	
	@ApiModelProperty(value = "批次名称")
	private String traceBatchName;
	
	@ApiModelProperty(value = "创建时间")
	private String cmtTime;
	
	// 当前批次上链数量
	@ApiModelProperty(value = "溯源数量")
	private Integer blockNum;

	@ApiModelProperty(value = "是否有权限查看具体节点信息,1有权限，0 无权限")
	private String authFlag;
	
	public Long getBlockChainId() {
		return blockChainId;
	}

	public String getAuthFlag() {
		return authFlag;
	}


	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}


	public void setBlockChainId(Long blockChainId) {
		this.blockChainId = blockChainId;
	}

	public Integer getBlockNum() {
		return blockNum;
	}
	

	public void setBlockNum(Integer blockNum) {
		this.blockNum = blockNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTraceBatchInfoId() {
		return traceBatchInfoId;
	}

	public void setTraceBatchInfoId(String traceBatchInfoId) {
		this.traceBatchInfoId = traceBatchInfoId;
	}

	public String getTraceBatchName() {
		return traceBatchName;
	}

	public void setTraceBatchName(String traceBatchName) {
		this.traceBatchName = traceBatchName;
	}

	public String getCmtTime() {
		return cmtTime;
	}

	public void setCmtTime(String cmtTime) {
		this.cmtTime = cmtTime;
	}

}
