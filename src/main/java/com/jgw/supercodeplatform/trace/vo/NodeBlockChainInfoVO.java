package com.jgw.supercodeplatform.trace.vo;

import java.util.Date;
import java.util.List;

import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "区块链返回前端数据model")
public class NodeBlockChainInfoVO {
	//主键id
    private Long blockChainId;
	
	private String productId;
	
	private String productName;
	
	private String traceBatchInfoId;
	
	private String traceBatchName;
	
	private String functionId;
	
	private String functionName;
	
	private Long interfaceId;
	// 上链节点信息
	private List<NodeInsertBlockChainStruct> currentNodeInfoList;
	// 上链节点信息
	private List<NodeInsertBlockChainStruct> lastNodeInfoList;
	
	private String currentNodeInfo;
	
	private String lastNodeInfo;
	// 区块号
	private Long blockNo;
	
	private String blockHash;
	
	private String transactionHash;
	
	private Date transactionTime;
	
	private Date cmtTime;
	
	private String organizationId;
	
	private String organizationName;
	
	// 上链节点信息种是否包含对象
	private Integer containObj;

	// 当前批次上链数量
	private Integer blockNum;
	/**
	 * 1上链 2 疑视串改 3未上链
	 */
	@ApiModelProperty(value = "上链状态 ，1：验证通过，2 校验不通过")
	private Integer blockChainStatus;
	
	public Integer getBlockChainStatus() {
		return blockChainStatus;
	}

	public void setBlockChainStatus(Integer blockChainStatus) {
		this.blockChainStatus = blockChainStatus;
	}

	public Long getBlockChainId() {
		return blockChainId;
	}

	public void setBlockChainId(Long blockChainId) {
		this.blockChainId = blockChainId;
	}

	public Integer getBlockNum() {
		return blockNum;
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

	public void setBlockNum(Integer blockNum) {
		this.blockNum = blockNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public Long getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
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

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Date getCmtTime() {
		return cmtTime;
	}

	public void setCmtTime(Date cmtTime) {
		this.cmtTime = cmtTime;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getContainObj() {
		return containObj;
	}

	public void setContainObj(Integer containObj) {
		this.containObj = containObj;
	}
}
