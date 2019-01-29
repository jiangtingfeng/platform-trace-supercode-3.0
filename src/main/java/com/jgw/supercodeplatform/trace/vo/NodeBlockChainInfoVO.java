package com.jgw.supercodeplatform.trace.vo;

import java.util.List;

import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "区块链返回前端节点数据详情model")
public class NodeBlockChainInfoVO {
	
	@ApiModelProperty(value = "主键id")
    private Long blockChainId;

	@ApiModelProperty(value = "功能节点id")
	private String functionId;
	
	@ApiModelProperty(value = "功能节点名称")
	private String functionName;
	
	// 上链节点信息
	@ApiModelProperty(value = "当前业务系统节点对象数据",hidden=true)
	private List<NodeInsertBlockChainStruct> currentNodeInfoList;
	
	// 上链节点信息
	@ApiModelProperty(value = "上链业务系统节点对象数据",hidden=true)
	private List<NodeInsertBlockChainStruct> lastNodeInfoList;
	
	@ApiModelProperty(value = "当前业务系统节点数据")
	private String currentNodeInfo;
	
	@ApiModelProperty(value = "上链节点数据")
	private String lastNodeInfo;
	// 区块号
	@ApiModelProperty(value = "区块号")
	private Long blockNo;
	
	@ApiModelProperty(value = "区块hash--所在区块链位置")
	private String blockHash;
	
	@ApiModelProperty(value = "交易hash--全球唯一区块链hash编号")
	private String transactionHash;
	
	@ApiModelProperty(value = "交易时间")
	private String transactionTime;
	/**
	 * 1上链 2 疑视串改 3未上链
	 */
	@ApiModelProperty(value = "上链状态 ，1：验证通过，2 校验不通过")
	private boolean check;

	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Long getBlockChainId() {
		return blockChainId;
	}

	public void setBlockChainId(Long blockChainId) {
		this.blockChainId = blockChainId;
	}


	public String getCurrentNodeInfo() {
		return currentNodeInfo;
	}

	public void setCurrentNodeInfo(String currentNodeInfo) {
		this.currentNodeInfo = currentNodeInfo;
	}

	public String getLastNodeInfo() {
		return lastNodeInfo;
	}

	public void setLastNodeInfo(String lastNodeInfo) {
		this.lastNodeInfo = lastNodeInfo;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public List<NodeInsertBlockChainStruct> getCurrentNodeInfoList() {
		return currentNodeInfoList;
	}

	public void setCurrentNodeInfoList(List<NodeInsertBlockChainStruct> currentNodeInfoList) {
		this.currentNodeInfoList = currentNodeInfoList;
	}

	public List<NodeInsertBlockChainStruct> getLastNodeInfoList() {
		return lastNodeInfoList;
	}

	public void setLastNodeInfoList(List<NodeInsertBlockChainStruct> lastNodeInfoList) {
		this.lastNodeInfoList = lastNodeInfoList;
	}

	public Long getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(Long blockNo) {
		this.blockNo = blockNo;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

}
