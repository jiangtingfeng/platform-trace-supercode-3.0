package com.jgw.supercodeplatform.blockchain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.jgw.blockchain.client.model.BlockChainResultInfo;
import com.jgw.blockchain.client.service.BlockChainPlatformService;
import com.jgw.supercodeplatform.SuperCodeTraceApplication;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dao.mapper1.blockchain.NodeBlockChainInfoMapper;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStructWrapper;
import com.jgw.supercodeplatform.trace.pojo.blockchain.NodeBlockChainInfo;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoVO;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeTraceApplication.class) // 指定我们SpringBoot工程的Application启动类
public class BlockChainTest {
    @Autowired
    private BlockChainPlatformService blockChainPlatformService;
	
    @Autowired
    private NodeBlockChainInfoMapper nodeBlockChainInfoDao;
    
	@Test
	public void batchUpdate() {
		NodeInsertBlockChainStruct node=new NodeInsertBlockChainStruct();
		node.setFieldCode("dd");
		node.setFieldName("批次名称");
		node.setFieldValue("ddds");
		
		NodeInsertBlockChainStruct node1=new NodeInsertBlockChainStruct();
		node1.setFieldCode("dd");
		node1.setFieldName("批次名称");
		node1.setFieldValue("ddds");
		node1.setObjectType(1003);
		node1.setObjectUniqueValue("sdsd");
		List<NodeInsertBlockChainStruct> list=new ArrayList<NodeInsertBlockChainStruct>();
		list.add(node);
		list.add(node1);
		NodeInsertBlockChainStructWrapper wapper=new NodeInsertBlockChainStructWrapper();
		String data=JSONObject.toJSONString(list);
		wapper.setNodeInfo(data);
		
		BlockChainResultInfo ifo=blockChainPlatformService.postCochain(63l, wapper);
		System.out.println(ifo);
	}
	
	@Test
	public void insert() {
		NodeInsertBlockChainStruct node2=new NodeInsertBlockChainStruct();
		node2.setFieldCode("dd");
		node2.setFieldName("批次名称");
		node2.setFieldValue("ddds");
		
		NodeInsertBlockChainStruct node1=new NodeInsertBlockChainStruct();
		node1.setFieldCode("dd");
		node1.setFieldName("批次名称");
		node1.setFieldValue("ddds");
		node1.setObjectType(1003);
		node1.setObjectUniqueValue("sdsd");
		List<NodeInsertBlockChainStruct> list=new ArrayList<NodeInsertBlockChainStruct>();
		list.add(node2);
		list.add(node1);
		String nodeinfo=JSONObject.toJSONString(list);
		
		
		NodeBlockChainInfo node=new NodeBlockChainInfo();
		node.setBlockHash("jdsijidj2124");
		node.setTraceBatchInfoId("1");
		node.setProductId("1");
		node.setTraceBatchName("批次1");
		node.setProductName("苹果");
		node.setFunctionId("dzzc1");
		node.setFunctionName("定制功能1");
		node.setNodeInfo(nodeinfo);
		node.setBlockNo(25L);
		node.setOrganizationId("8af7275c691949f382b5cf8094e732cb");
		nodeBlockChainInfoDao.insert(node);
	}
	@Test
	public void list() {
		DaoSearch searchParams=new DaoSearch();
		searchParams.setCurrent(1);
		searchParams.setPageSize(10);
		
		int startNumber = (searchParams.getCurrent()-1)*searchParams.getPageSize();
		searchParams.setStartNumber(startNumber);
		List<NodeBlockChainInfo>list=nodeBlockChainInfoDao.list(searchParams,"8af7275c691949f382b5cf8094e732cb");
		List<NodeBlockChainInfoVO> nodeBlockChainInfoVOs=null;
		if (null!=list && !list.isEmpty()) {
			nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoVO>(list.size());
			for (NodeBlockChainInfo nodeBlockChainInfo : list) {
				NodeBlockChainInfoVO vo=new NodeBlockChainInfoVO();
				String nodeInfo=nodeBlockChainInfo.getNodeInfo();
				List<NodeInsertBlockChainStruct> nodeInsertList=JSONObject.parseArray(nodeInfo, NodeInsertBlockChainStruct.class);
				vo.setCurrentNodeInfoList(nodeInsertList);
				vo.setBlockChainId(nodeBlockChainInfo.getBlockChainId());
				vo.setBlockHash(nodeBlockChainInfo.getBlockHash());
				vo.setBlockNo(nodeBlockChainInfo.getBlockNo());
				vo.setBlockNum(nodeBlockChainInfo.getBlockNum());
				vo.setCmtTime(nodeBlockChainInfo.getCmtTime());
				vo.setFunctionId(nodeBlockChainInfo.getFunctionId());
				vo.setFunctionName(nodeBlockChainInfo.getFunctionName());
				vo.setInterfaceId(nodeBlockChainInfo.getInterfaceId());
				vo.setOrganizationId(nodeBlockChainInfo.getOrganizationId());
				vo.setOrganizationName(nodeBlockChainInfo.getOrganizationName());
				vo.setProductId(nodeBlockChainInfo.getProductId());
				vo.setProductName(nodeBlockChainInfo.getProductName());
				vo.setTraceBatchInfoId(nodeBlockChainInfo.getTraceBatchInfoId());
				vo.setTraceBatchName(nodeBlockChainInfo.getTraceBatchName());
				vo.setTransactionHash(nodeBlockChainInfo.getTransactionHash());
				vo.setTransactionTime(nodeBlockChainInfo.getTransactionTime());
				nodeBlockChainInfoVOs.add(vo);
			}
		}else {
			nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoVO>(0);
		}
	}
}
