package com.jgw.supercodeplatform.trace.pojo.blockchain;

public class NodeBlockChainInfo {
	private Long blockChainId;
	
	private String productId;
	
	private String productName;
	
	private String traceBatchInfoId;
	
	private String traceBatchName;
	
	private String functionId;
	
	private String functionName;
	
	private Long interfaceId;
	// 上链节点信息
	private String nodeInfo;
	// 区块号
	private Long blockNo;
	
	private String blockHash;
	
	private String transactionHash;
	
	private String transactionTime;
	
	private String cmtTime;
	
	private String organizationId;
	
	private String organizationName;
	
	// 上链节点信息种是否包含对象
	private Integer containObj;
	
	// 当前批次上链数量--不保存数据库
	private Integer blockNum;

	private String sysId;

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public Long getBlockChainId() {
		return blockChainId;
	}

	public void setBlockChainId(Long blockChainId) {
		this.blockChainId = blockChainId;
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

	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
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

	public String getCmtTime() {
		return cmtTime;
	}

	public void setCmtTime(String cmtTime) {
		this.cmtTime = cmtTime;
	}

	public Integer getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(Integer blockNum) {
		this.blockNum = blockNum;
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
