package com.jgw.supercodeplatform.trace.service.blockchain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jgw.blockchain.client.model.BlockChainResultInfo;
import com.jgw.blockchain.client.service.BlockChainPlatformService;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.OrganizationCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.mapper1.blockchain.NodeBlockChainInfoMapper;
import com.jgw.supercodeplatform.trace.dto.blockchain.CheckNodeBlockInfoParam;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStructWrapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.blockchain.NodeBlockChainInfo;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoListVO;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoVO;

@Component
public class NodeBlockChainInfoService extends AbstractPageService{
    @Autowired
    private BlockChainPlatformService blockChainPlatformService;
    
    
    @Autowired
    private NodeBlockChainInfoMapper nodeBlockChainInfoDao;
    
    @Autowired
    private CommonUtil commonUtil;
    
    private static  long interfaceId=63L;
    
    //TODO 
    @Override
	protected List<NodeBlockChainInfoListVO> searchResult(DaoSearch searchParams) throws Exception {
    	String organizationId=commonUtil.getOrganizationId();
    	String flag=commonUtil.getSeniorFunFlag();
		int startNumber = (searchParams.getCurrent()-1)*searchParams.getPageSize();
		searchParams.setStartNumber(startNumber);
		List<NodeBlockChainInfo>list=nodeBlockChainInfoDao.list(searchParams,organizationId);
		List<NodeBlockChainInfoListVO> nodeBlockChainInfoVOs=null;
		if (null!=list && !list.isEmpty()) {
			nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoListVO>(list.size());
			for (NodeBlockChainInfo nodeBlockChainInfo : list) {
				NodeBlockChainInfoListVO vo=new NodeBlockChainInfoListVO();
				vo.setBlockNum(nodeBlockChainInfo.getBlockNum());
				vo.setCmtTime(nodeBlockChainInfo.getCmtTime());
				vo.setProductName(nodeBlockChainInfo.getProductName());
				vo.setTraceBatchInfoId(nodeBlockChainInfo.getTraceBatchInfoId());
				vo.setTraceBatchName(nodeBlockChainInfo.getTraceBatchName());
				vo.setAuthFlag(flag==null?"0":"1");
				nodeBlockChainInfoVOs.add(vo);
			}
		}else {
			nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoListVO>(0);
		}
		return nodeBlockChainInfoVOs;
	}

    /**
     * 根据批次id查询上链溯源信息
     * @param traceBatchInfoId
     * @return
     * @throws SuperCodeException 
     */
	public RestResult<List<NodeBlockChainInfoVO>> queryByTraceBatchInfoId(String traceBatchInfoId) throws SuperCodeException {
		RestResult<List<NodeBlockChainInfoVO>> restResult=new RestResult<List<NodeBlockChainInfoVO>>();
		
//		String flag=commonUtil.getSeniorFunFlag();
//		if (StringUtils.isBlank(flag) || !"1".equals(flag)) {
//			restResult.setState(500);
//			restResult.setMsg("该组织无权限查看上链节点信息");
//			return restResult;
//		}
		
		List<NodeBlockChainInfo>list=nodeBlockChainInfoDao.queryByTraceBatchInfoId(traceBatchInfoId);
		if (null==list || list.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该批次未上链");
			return restResult;
		}
		List<NodeBlockChainInfoVO> nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoVO>(list.size());
		for (NodeBlockChainInfo nodeBlockChainInfo : list) {
			String transactionHash=nodeBlockChainInfo.getTransactionHash();
			NodeBlockChainInfoVO vo=new NodeBlockChainInfoVO();
			String nodeInfo=nodeBlockChainInfo.getNodeInfo();
			NodeInsertBlockChainStructWrapper noWrapper=JSONObject.parseObject(nodeInfo, NodeInsertBlockChainStructWrapper.class);
			vo.setLocalDataJson(noWrapper.getNodeInfo());
			vo.setBlockChainId(nodeBlockChainInfo.getBlockChainId());
			vo.setBlockHash(nodeBlockChainInfo.getBlockHash());
			vo.setBlockNo(nodeBlockChainInfo.getBlockNo());
			vo.setFunctionId(nodeBlockChainInfo.getFunctionId());
			vo.setFunctionName(nodeBlockChainInfo.getFunctionName());
			vo.setTransactionHash(transactionHash);
			vo.setTransactionTime(nodeBlockChainInfo.getTransactionTime());
		
			BlockChainResultInfo blockChainResultInfo=blockChainPlatformService.getBlockChainInfo(interfaceId, transactionHash);
			if (null==blockChainResultInfo) {
				vo.setCheck(false);
			}else {
				String blockNodeInfo=blockChainResultInfo.getData();
				if (nodeInfo.equals(blockNodeInfo)) {
					vo.setCheck(true);
				}else {
					vo.setCheck(false);
				}
				NodeInsertBlockChainStructWrapper lastnoWrapper=JSONObject.parseObject(nodeInfo, NodeInsertBlockChainStructWrapper.class);
				vo.setBlockchainDataJson(lastnoWrapper.getNodeInfo());
			}
			nodeBlockChainInfoVOs.add(vo);
		}
		restResult.setState(200);
		restResult.setMsg("成功");
		restResult.setResults(nodeBlockChainInfoVOs);
		return restResult;
	}

    /**
     * 根据批次id校验当前批次溯源信息上链状态
     * @param checkNodeBlockInfoParam
     * @return
     */
	public RestResult<Map<String, String>> checkNodeBlockInfo(CheckNodeBlockInfoParam checkNodeBlockInfoParam) {
		RestResult<Map<String, String>> restResult=new RestResult<Map<String, String>>();
		
		
		if (null==checkNodeBlockInfoParam || null==checkNodeBlockInfoParam.getTraceBatchInfoIds() || checkNodeBlockInfoParam.getTraceBatchInfoIds().isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("参数不能为空");
			return restResult;
		}
		List<String> traceBatchInfoIdS= checkNodeBlockInfoParam.getTraceBatchInfoIds();
		Map<String, String>dataMap=new LinkedHashMap<String, String>(traceBatchInfoIdS.size());
		
		outer:for (String traceBatchInfoId : traceBatchInfoIdS) {
			List<NodeBlockChainInfo>list=nodeBlockChainInfoDao.queryByTraceBatchInfoId(traceBatchInfoId);
			if (null==list || list.isEmpty()) {
				dataMap.put(traceBatchInfoId, "未上链");
				continue;
			}
			for (NodeBlockChainInfo nodeBlockChainInfo : list) {
				String transactionHash=nodeBlockChainInfo.getTransactionHash();
				String nodeInfo=nodeBlockChainInfo.getNodeInfo();
				
				BlockChainResultInfo blockChainResultInfo=blockChainPlatformService.getBlockChainInfo(interfaceId, transactionHash);
				if (null==blockChainResultInfo) {
					dataMap.put(traceBatchInfoId, "校验不通过");
					continue outer;
				}else {
					String blockNodeInfo=blockChainResultInfo.getData();
					if (!nodeInfo.equals(blockNodeInfo)) {
						dataMap.put(traceBatchInfoId, "校验不通过");
						continue outer;
					}
				}
			}
			dataMap.put(traceBatchInfoId, "校验通过");
		}
		restResult.setState(200);
		restResult.setMsg("成功");
		restResult.setResults(dataMap);
		return restResult;
	}
	
	@Override
	protected int count(DaoSearch searchParams) throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		return nodeBlockChainInfoDao.count(searchParams,organizationId);
	}



	/**
     * 新增时上链
     * @param lineData
	 * @param traceBatchInfoId 
     * @param containObj
     * @param fieldsMap 
     * @throws SuperCodeException 
     * @throws SuperCodeTraceException 
     */
	public void coChain(LineBusinessData lineData, boolean isNode, String traceBatchInfoId, Map<String, TraceFunFieldConfig> fieldsMap) throws SuperCodeException, SuperCodeTraceException {
		List<FieldBusinessParam> fields=lineData.getFields();
		if (null==fields || fields.isEmpty()) {
			throw new SuperCodeException("新增溯源记录时入链参数不能为空", 500);
		}
		
		//保存到数据库的节点上链业务数据实体
		NodeBlockChainInfo nodeBlockChainInfo=new NodeBlockChainInfo();
		nodeBlockChainInfo.setInterfaceId(interfaceId);
		//拼装上链对象
		boolean functionNotSet=true;
		List<NodeInsertBlockChainStruct> list=new ArrayList<NodeInsertBlockChainStruct>();
		for (FieldBusinessParam fieldBusinessParam : fields) {
			String fieldCode=fieldBusinessParam.getFieldCode();
    		TraceFunFieldConfig traceFunFieldConfig=fieldsMap.get(fieldCode);
     		if (null==traceFunFieldConfig) {
     			throw new SuperCodeException("修改溯源记录时入链时，从动态表查出的字段-"+fieldCode+"在字段配置表中不存在", 500);
			}
     		
			Integer objectType=fieldBusinessParam.getObjectType();
	   		Integer extraCreate=traceFunFieldConfig.getExtraCreate();
			if (null==extraCreate || (1==extraCreate && null!=objectType)) {
				String fieldValue=fieldBusinessParam.getFieldValue();
				String objectUniqueValue=fieldBusinessParam.getObjectUniqueValue();
				
				NodeInsertBlockChainStruct noStruct=new NodeInsertBlockChainStruct();
				noStruct.setFieldCode(fieldCode);
				noStruct.setExtraCreate(extraCreate);
				try {
					String fieldName=traceFunFieldConfig.getFieldName();
					noStruct.setFieldName(fieldName);
					
					//设置功能名称和功能id
					if (functionNotSet) {
						String functionId=traceFunFieldConfig.getFunctionId();
						String functionName=traceFunFieldConfig.getFunctionName();
						nodeBlockChainInfo.setFunctionId(functionId);
						nodeBlockChainInfo.setFunctionName(functionName);
						functionNotSet=false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//如果是新增节点溯源信息则使用参数的批次id
				if (isNode) {
					nodeBlockChainInfo.setTraceBatchInfoId(traceBatchInfoId);
				}
				if (null!=objectType) {
					noStruct.setObjectUniqueValue(objectUniqueValue);
					ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
					switch (objectTypeEnum) {
					case TRACE_BATCH:
						//如果是定制功能新增则使用字段里的批次id
						if (!isNode) {
							nodeBlockChainInfo.setTraceBatchInfoId(objectUniqueValue);	
						}
						nodeBlockChainInfo.setTraceBatchName(fieldValue);
						break;
					case PRODUCT:
						nodeBlockChainInfo.setProductId(objectUniqueValue);
						nodeBlockChainInfo.setProductName(fieldValue);
						break;
					case USER:
						break;
					default:
						break;
					}
				}
				noStruct.setFieldValue(fieldValue);
				noStruct.setObjectType(objectType);
				list.add(noStruct);
			}
		}
		int len=realCoChain(nodeBlockChainInfo, list);
	}

	

    /**
     *  修改时上链 目前先单条上链
     * @param listDdata
     * @param containObj
     * @param fieldsMap 
     * @throws SuperCodeException 
     */
	public void updateCoChain(List<LinkedHashMap<String, Object>> listData, boolean containObj, Map<String, TraceFunFieldConfig> fieldsMap) throws SuperCodeException {
     	if (null==listData || listData.isEmpty() || listData.size()>1) {
     		throw new SuperCodeException("修改溯源记录时入链参数不能为空且入链长度不能大于1", 500);
		}	
     	LinkedHashMap<String, Object> lineMap=listData.get(0);
		//保存到数据库的节点上链业务数据实体
		NodeBlockChainInfo nodeBlockChainInfo=new NodeBlockChainInfo();
		
		//拼装上链对象
		boolean functionNotSet=true;
		List<NodeInsertBlockChainStruct> list=new ArrayList<NodeInsertBlockChainStruct>();
     	for(String fieldCode:lineMap.keySet()) {
     		TraceFunFieldConfig traceFunFieldConfig=fieldsMap.get(fieldCode);
     		if (null==traceFunFieldConfig) {
     			throw new SuperCodeException("修改溯源记录时入链时，从动态表查出的字段-"+fieldCode+"在字段配置表中不存在", 500);
			}
     		
     		Integer extraCreate=traceFunFieldConfig.getExtraCreate();
     		String objectType=traceFunFieldConfig.getObjectType();
     		//如果字段是前端选择的或者是对象的唯一值字段则入链，否则不入链因为前端展示不需要默认字段
     		if (null==extraCreate || (1==extraCreate && null!=objectType)) {
    			String fieldType=traceFunFieldConfig.getFieldType();
    			String fieldName=traceFunFieldConfig.getFieldName();
    			Object fieldValue=lineMap.get(fieldCode);
    			
    			NodeInsertBlockChainStruct noStruct=new NodeInsertBlockChainStruct();
    			noStruct.setFieldCode(fieldCode);
    			noStruct.setFieldName(fieldName);
    			noStruct.setExtraCreate(extraCreate);
    			try {
    				//设置功能名称和功能id
    				if (functionNotSet) {
    					String functionId=traceFunFieldConfig.getFunctionId();
    					String functionName=traceFunFieldConfig.getFunctionName();
    					nodeBlockChainInfo.setFunctionId(functionId);
    					nodeBlockChainInfo.setFunctionName(functionName);
    					functionNotSet=false;
    				}		
    				if (StringUtils.isNotBlank(objectType)) {
    					//设置对象类型
    					Integer int_objectType=Integer.valueOf(objectType);
    					noStruct.setObjectType(int_objectType);
    					
    					//设置nodeBlockChainInfo的产品和批次名称
    					ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(int_objectType);
    					switch (objectTypeEnum) {
    					case TRACE_BATCH:
    						Object traceBatchInfoId=lineMap.get(objectTypeEnum.getFieldCode());
    						String s_traceBatchInfoId=(traceBatchInfoId==null?null:String.valueOf(traceBatchInfoId));
    						nodeBlockChainInfo.setTraceBatchName(String.valueOf(fieldValue));
    						nodeBlockChainInfo.setTraceBatchInfoId(s_traceBatchInfoId);
    						
    						noStruct.setObjectUniqueValue(s_traceBatchInfoId);
    						break;
    					case PRODUCT:
    						Object productId=lineMap.get(objectTypeEnum.getFieldCode());
    						String s_productId=(productId==null?null:String.valueOf(productId));
    						nodeBlockChainInfo.setProductId(s_productId);
    						nodeBlockChainInfo.setProductName(String.valueOf(fieldValue));
    						
    						noStruct.setObjectUniqueValue(s_productId);
    						break;
    					case USER:
    						Object userId=lineMap.get(objectTypeEnum.getFieldCode());
    						String s_userId=(userId==null?null:String.valueOf(userId));
    						noStruct.setObjectUniqueValue(s_userId);
    						break;
    					default:
    						break;
    					}
    				}
					if (null!=fieldValue) {
						//如果是对象类型则不用存值只需存对象唯一值，否则存下字段值
						if ("8".equals(fieldType)) {
							SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							noStruct.setFieldValue(format.format(fieldValue));
						}else {
							noStruct.setFieldValue(String.valueOf(fieldValue));
						}
					}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			list.add(noStruct);
			}

     	}
     	int len=realCoChain(nodeBlockChainInfo, list);
     	
	}
	/**
	 * 执行上链且拼装NodeBlockChainInfo对象并插入
	 * @param nodeBlockChainInfo
	 * @param list
	 * @return
	 * @throws SuperCodeException
	 */
	private int realCoChain(NodeBlockChainInfo nodeBlockChainInfo, List<NodeInsertBlockChainStruct> list)
			throws SuperCodeException {
		//数据上链
		String coChainData=JSONObject.toJSONString(list);
		NodeInsertBlockChainStructWrapper wrapper=new NodeInsertBlockChainStructWrapper();
		wrapper.setNodeInfo(coChainData);
		BlockChainResultInfo blockChainResultInfo=blockChainPlatformService.postCochain(interfaceId, wrapper);
		
		//设置上链数据
		if (null!=blockChainResultInfo) {
			try {
				//TODO 
				OrganizationCache organizationCache=commonUtil.getOrganization();
				nodeBlockChainInfo.setOrganizationId(organizationCache.getOrganizationId());
				nodeBlockChainInfo.setOrganizationName(organizationCache.getOrganizationFullName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			nodeBlockChainInfo.setBlockHash(blockChainResultInfo.getBlockHash());
			nodeBlockChainInfo.setBlockNo(blockChainResultInfo.getBlockNo());
			String wrappercoChainData=JSONObject.toJSONString(wrapper);
			nodeBlockChainInfo.setNodeInfo(wrappercoChainData);
			nodeBlockChainInfo.setTransactionHash(blockChainResultInfo.getTransactionHash());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			nodeBlockChainInfo.setTransactionTime(format.format(new Date(blockChainResultInfo.getTransactionTime())));
			int len=nodeBlockChainInfoDao.insert(nodeBlockChainInfo);
			return len;
		}
		return 0;
	}




	
}
