package com.jgw.supercodeplatform.trace.service.antchain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.OrganizationCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.mapper1.antchain.AntChainMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityCommonMessage;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityCommonMessage.Item;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityCommonMessage.ItemGroup;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityMessage.TraceabilityPhaseReply;
import com.jgw.supercodeplatform.trace.dto.antchain.TraceabilityQueryMessage;
import com.jgw.supercodeplatform.trace.dto.blockchain.CheckNodeBlockInfoParam;
import com.jgw.supercodeplatform.trace.dto.blockchain.NodeInsertBlockChainStruct;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.antchain.AntChainInfo;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoListVO;
import com.jgw.supercodeplatform.trace.vo.NodeBlockChainInfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AntChainInfoService extends AbstractPageService {
	private static Logger logger = LoggerFactory.getLogger(AntChainInfoService.class);
	

    @Autowired
    private AntChainMapper antChainMapper;
    
    @Autowired
    private TraceBatchInfoMapper traceBatchInfoDao;
    
    @Autowired
    private CommonUtil commonUtil;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
				0,
						5,
						60,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());

    //TODO
    @Override
	protected List<NodeBlockChainInfoListVO> searchResult(DaoSearch searchParams) throws Exception {
    	String organizationId=commonUtil.getOrganizationId();
    	String strFlag="0";
    	Boolean flag=commonUtil.getTraceSeniorFunFlag();
    	if (null!=flag && flag) {
    		strFlag="1";
		}
    	
		int startNumber = (searchParams.getCurrent()-1)*searchParams.getPageSize();
		searchParams.setStartNumber(startNumber);
		List<AntChainInfo>list= antChainMapper.list(searchParams,organizationId);
		List<NodeBlockChainInfoListVO> nodeBlockChainInfoVOs=null;
		if (null!=list && !list.isEmpty()) {
			nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoListVO>(list.size());
			for (AntChainInfo antchainInfo : list) {
				NodeBlockChainInfoListVO vo=new NodeBlockChainInfoListVO();
				vo.setBlockNum(antchainInfo.getBlockNum());
				vo.setCmtTime(antchainInfo.getCmtTime());
				vo.setProductName(antchainInfo.getProductName());
				vo.setTraceBatchInfoId(antchainInfo.getTraceBatchInfoId());
				vo.setTraceBatchName(antchainInfo.getTraceBatchName());
				vo.setAuthFlag(strFlag);
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
		
		Boolean flag=commonUtil.getTraceSeniorFunFlag();
		if (null==flag || !flag) {
			restResult.setState(500);
			restResult.setMsg("该组织无权限查看上链节点信息");
			return restResult;
		}
		
		List<AntChainInfo>list= antChainMapper.queryByTraceBatchInfoId(traceBatchInfoId);
		if (null==list || list.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该批次未上链");
			return restResult;
		}
		List<NodeBlockChainInfoVO> nodeBlockChainInfoVOs=new ArrayList<NodeBlockChainInfoVO>(list.size());
		for (AntChainInfo antchainInfo : list) {
			String transactionHash=antchainInfo.getTransactionHash();
			NodeBlockChainInfoVO vo=new NodeBlockChainInfoVO();
			String nodeInfo=antchainInfo.getNodeInfo();
			vo.setLocalDataJson(nodeInfo);
			vo.setBlockChainId(antchainInfo.getBlockChainId());
			vo.setBlockHash(antchainInfo.getBlockHash());
			vo.setBlockNo(antchainInfo.getBlockNo());
			vo.setFunctionId(antchainInfo.getFunctionId());
			vo.setFunctionName(antchainInfo.getFunctionName());
			vo.setTransactionHash(transactionHash);
			vo.setTransactionTime(antchainInfo.getTransactionTime());
			try {
				vo.setCheck(nodeInfo.equals(getNodeInfo(transactionHash)));
				vo.setBlockchainDataJson(nodeInfo);
			} catch (Exception e) {
				e.printStackTrace();
				vo.setCheck(false);
			}
			nodeBlockChainInfoVOs.add(vo);
		}
		restResult.setState(200);
		restResult.setMsg("成功");
		restResult.setResults(nodeBlockChainInfoVOs);
		return restResult;
	}

	/**
	 * 获取蚂蚁区块信息
	 * @param txHash
	 * @return
	 */
	private String getNodeInfo(String txHash) throws Exception {
		TraceabilityQueryMessage.TraceInfo txByTxHash = AntChainUtils.getTxByTxHash(txHash);
		List<NodeInsertBlockChainStruct> list = new ArrayList<>();
		List<Item> itemsList = txByTxHash.getPayload().getItemsList();
		for (Item item: itemsList){
			NodeInsertBlockChainStruct nodeInsertBlockChainStruct = new NodeInsertBlockChainStruct();
			nodeInsertBlockChainStruct.setFieldName(item.getTitle());
			nodeInsertBlockChainStruct.setFieldValue(item.getValue());
			nodeInsertBlockChainStruct.setFieldCode(item.getKey());
			try {
				nodeInsertBlockChainStruct.setObjectType(Integer.valueOf(item.getType()));

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			String extraCreate = item.getExtInfoOrDefault("extraCreate",  null);
			if(extraCreate != null) {
				try {
					nodeInsertBlockChainStruct.setExtraCreate(Integer.valueOf(extraCreate));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			nodeInsertBlockChainStruct.setObjectUniqueValue(item.getExtInfoOrDefault("objectUniqueValue",null));
			list.add(nodeInsertBlockChainStruct);
		}
		return JSONObject.toJSONString(list);
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
			List<AntChainInfo>list= antChainMapper.queryByTraceBatchInfoId(traceBatchInfoId);
			if (null==list || list.isEmpty()) {
				dataMap.put(traceBatchInfoId, "未上链");
				continue;
			}

			for (AntChainInfo antchainInfo : list) {
				String transactionHash=antchainInfo.getTransactionHash();
				String nodeInfo=antchainInfo.getNodeInfo();
				try {
					String nodeInfo1 = getNodeInfo(transactionHash);
					if(!nodeInfo.equals(nodeInfo1)){
						dataMap.put(traceBatchInfoId, "校验不通过");
						continue outer;
					}
				} catch (Exception e) {
					e.printStackTrace();
					dataMap.put(traceBatchInfoId, "校验不通过");
					continue outer;
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
		return antChainMapper.count(searchParams,organizationId);
	}



	/**
     * 新增时上链
     * @param lineData
	 * @param traceBatchInfoId 
     * @param containObj
     * @param fieldsMap 
	 * @param traceBatchInfo 
     * @throws SuperCodeException 
     * @throws SuperCodeTraceException
     */
	public void coChain(LineBusinessData lineData, boolean isNode, String traceBatchInfoId, Map<String, TraceFunFieldConfig> fieldsMap, TraceBatchInfo traceBatchInfo) throws SuperCodeException, SuperCodeTraceException {
		List<FieldBusinessParam> fields=lineData.getFields();
		if (null==fields || fields.isEmpty()) {
			throw new SuperCodeException("新增溯源记录时入链参数不能为空", 500);
		}
		
		//保存到数据库的节点上链业务数据实体
		AntChainInfo antchainInfo=new AntChainInfo();
		// TODO
		antchainInfo.setNodeInfoId(1L);
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
						antchainInfo.setFunctionId(functionId);
						antchainInfo.setFunctionName(functionName);
						functionNotSet=false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//如果是新增节点溯源信息则使用参数的批次实体填充数据
				if (isNode) {
					antchainInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
					antchainInfo.setTraceBatchName(traceBatchInfo.getTraceBatchName());
					antchainInfo.setProductId(traceBatchInfo.getProductId());
					antchainInfo.setProductName(traceBatchInfo.getProductName());
				}
				if (null!=objectType) {
					noStruct.setObjectUniqueValue(objectUniqueValue);
					ObjectTypeEnum objectTypeEnum= ObjectTypeEnum.getType(objectType);
					switch (objectTypeEnum) {
					case TRACE_BATCH:
						//如果是定制功能新增则使用字段里的批次id
						if (!isNode) {
							antchainInfo.setTraceBatchInfoId(objectUniqueValue);
						}
						antchainInfo.setTraceBatchName(fieldValue);
						break;
					case PRODUCT:
						antchainInfo.setProductId(objectUniqueValue);
						antchainInfo.setProductName(fieldValue);
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
		String coChainData=JSONObject.toJSONString(list);
		antchainInfo.setNodeInfo(coChainData);
		try {
			//TODO
			OrganizationCache organizationCache=commonUtil.getOrganization();
			antchainInfo.setOrganizationId(organizationCache.getOrganizationId());
			antchainInfo.setOrganizationName(organizationCache.getOrganizationFullName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		antChainMapper.insert(antchainInfo);
		realCoChain(antchainInfo, list);
	}

	/**
     * 定时任务重新上链
	 */
	public void reCoChain(){
		List<AntChainInfo> list = antChainMapper.queryUnchain(new Date());
		for (AntChainInfo antchainInfo: list){
			String nodeInfo = antchainInfo.getNodeInfo();
			List<NodeInsertBlockChainStruct> nodeInsertBlockChainStructs = JSON.parseArray(nodeInfo, NodeInsertBlockChainStruct.class);
			realCoChain(antchainInfo, nodeInsertBlockChainStructs);
		}

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
		AntChainInfo antChainInfo=new AntChainInfo();
		//拼装上链对象
		boolean functionNotSet=true;
		boolean hasBatch=false;
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
						antChainInfo.setFunctionId(functionId);
						antChainInfo.setFunctionName(functionName);
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
								if (StringUtils.isNotBlank(s_traceBatchInfoId)) {
									TraceBatchInfo traceBatchInfo =traceBatchInfoDao.selectByTraceBatchInfoId(s_traceBatchInfoId);
									if (null==traceBatchInfo) {
										logger.error("溯源信息更新时上链失败，批次id："+s_traceBatchInfoId+"记录不存在");
										return;
									}
									//只有批次的数据才上链
									hasBatch=true;
									antChainInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
									antChainInfo.setTraceBatchName(traceBatchInfo.getTraceBatchName());
									antChainInfo.setProductId(traceBatchInfo.getProductId());
									antChainInfo.setProductName(traceBatchInfo.getProductName());
								}
								noStruct.setObjectUniqueValue(s_traceBatchInfoId);
								break;
							case PRODUCT:
								Object productId=lineMap.get(objectTypeEnum.getFieldCode());
								String s_productId=(productId==null?null:String.valueOf(productId));
								antChainInfo.setProductId(s_productId);
								antChainInfo.setProductName(String.valueOf(fieldValue));

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
     	if (hasBatch) {
			String coChainData=JSONObject.toJSONString(list);
			antChainInfo.setNodeInfo(coChainData);
			try {
				OrganizationCache organizationCache=commonUtil.getOrganization();
				antChainInfo.setOrganizationId(organizationCache.getOrganizationId());
				antChainInfo.setOrganizationName(organizationCache.getOrganizationFullName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			antChainMapper.insert(antChainInfo);
     		realCoChain(antChainInfo, list);
		}
     	
	}

	/**
	 * 执行上链且拼装NodeBlockChainInfo对象并插入
	 * @param antchainInfo
	 * @param list
	 * @return
	 * @throws SuperCodeException
	 */
	private void realCoChain(AntChainInfo antchainInfo, List<NodeInsertBlockChainStruct> list) {
		//数据上链
		executor.execute(() ->{
			ItemGroup.Builder group = ItemGroup.newBuilder()
					.setKey(antchainInfo.getFunctionId())
					.setTitle(antchainInfo.getFunctionName());
			for (NodeInsertBlockChainStruct nodeInsertBlockChainStruct : list){
				Item.Builder item = Item.newBuilder()
						.setKey(nodeInsertBlockChainStruct.getFieldCode())
						.setTitle(nodeInsertBlockChainStruct.getFieldName())
						.setType(String.valueOf(nodeInsertBlockChainStruct.getObjectType()))
						.setValue(nodeInsertBlockChainStruct.getFieldValue())
						.putExtInfo("extraCreate", String.valueOf(nodeInsertBlockChainStruct.getExtraCreate()))
						.setBizTime(System.currentTimeMillis());
				if(nodeInsertBlockChainStruct.getObjectUniqueValue() != null) {
					item.putExtInfo("objectUniqueValue", nodeInsertBlockChainStruct.getObjectUniqueValue());
				}
				group.addItems(item);
			}
			try {
				AntChainUtils.regProductList(antchainInfo.getTraceBatchInfoId());
				long time = System.currentTimeMillis();
				TraceabilityCommonMessage.CommonReply regReply = AntChainUtils.sendCoChain(antchainInfo.getTraceBatchInfoId(), group, antchainInfo.getBlockChainId(),time);
				if (regReply.getCode() == AntChainUtils.SUCCESS_CODE) {
					TraceabilityPhaseReply traceabilityPhaseReply = regReply.getPayload().unpack(TraceabilityPhaseReply.class);
					updateTx(antchainInfo.getBlockChainId(), traceabilityPhaseReply,time);
				}else if(regReply.getCode() == 512){
					Thread.sleep(20000);
					regReply = AntChainUtils.sendCoChain(antchainInfo.getTraceBatchInfoId(), group, antchainInfo.getBlockChainId(), time);
					if (regReply.getCode() == AntChainUtils.SUCCESS_CODE) {
						TraceabilityPhaseReply traceabilityPhaseReply = regReply.getPayload().unpack(TraceabilityPhaseReply.class);
						updateTx(antchainInfo.getBlockChainId(), traceabilityPhaseReply, time);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}


	/**
	 * 更新区块信息
	 * @param id
	 * @param traceabilityPhaseReply
	 * @throws Exception
	 */
	public void updateTx(Long id, TraceabilityPhaseReply traceabilityPhaseReply,  Long time) throws Exception {
		Thread.sleep(10000);// 需要等待一段时间才能查询到区块信息
		TraceabilityQueryMessage.TraceInfo traceInfo = AntChainUtils.getTxByTxHash(traceabilityPhaseReply.getTxHash());
		if(traceInfo == null){
			return;
		}
		antChainMapper.updateTx(id, traceabilityPhaseReply.getTxHash(),traceInfo.getBlockInfo().getBlockHash(), new Date(time), traceInfo.getBlockInfo().getHeight());
	}
}
