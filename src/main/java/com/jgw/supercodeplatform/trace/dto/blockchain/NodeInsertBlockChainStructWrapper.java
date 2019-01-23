package com.jgw.supercodeplatform.trace.dto.blockchain;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "插入区块链数据结构包装实体")
public class NodeInsertBlockChainStructWrapper {
    private String nodeInfo;

	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
    
   
    
}
