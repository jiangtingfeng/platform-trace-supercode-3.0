package com.jgw.supercodeplatform.trace.service.blockchain;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.blockchain.client.service.BlockChainPlatformService;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;

@Component
public class BlockChainService {
    @Autowired
    BlockChainPlatformService blockChainPlatformService;
    
    /**
     * 新增时上链
     * @param lineData
     * @param containObj
     * @throws SuperCodeException 
     */
	public void coChain(LineBusinessData lineData, boolean containObj) throws SuperCodeException {
		List<FieldBusinessParam> fields=lineData.getFields();
		if (null==fields || fields.isEmpty()) {
			throw new SuperCodeException("新增溯源记录时入链参数不能为空", 500);
		}
		for (FieldBusinessParam fieldBusinessParam : fields) {
			String fieldCode=fieldBusinessParam.getFieldCode();
			String fieldValue=fieldBusinessParam.getFieldValue();
			Integer objectType=fieldBusinessParam.getObjectType();
			String objectUniqueValue=fieldBusinessParam.getObjectUniqueValue();
			
		}
		
	}

    /**
     *  修改时上链 目前先单条上链
     * @param listDdata
     * @param containObj
     */
	public void coChain(List<LinkedHashMap<String, Object>> listDdata, boolean containObj) {
     		
	}

}
