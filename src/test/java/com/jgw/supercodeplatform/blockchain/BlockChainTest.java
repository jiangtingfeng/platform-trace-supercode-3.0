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
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStructWrapper;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeTraceApplication.class) // 指定我们SpringBoot工程的Application启动类
public class BlockChainTest {
    @Autowired
    private BlockChainPlatformService blockChainPlatformService;
	
	
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
	

}
