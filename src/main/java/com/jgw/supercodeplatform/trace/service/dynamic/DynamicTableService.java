package com.jgw.supercodeplatform.trace.service.dynamic;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.Field;
import com.jgw.supercodeplatform.trace.common.util.CommonUtilComponent;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.*;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.*;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicUpdateNodeParam;
import com.jgw.supercodeplatform.trace.enums.BatchTableType;
import com.jgw.supercodeplatform.trace.enums.ComponentTypeEnum;
import com.jgw.supercodeplatform.trace.enums.RegulationTypeEnum;
import com.jgw.supercodeplatform.trace.enums.TraceUseSceneEnum;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.tracefun.*;
import com.jgw.supercodeplatform.trace.service.antchain.AntChainInfoService;
import com.jgw.supercodeplatform.trace.service.tracefun.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.model.NodeOrFunFields;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicAddFunParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicDeleteFunParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicAddNodeParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicDeleteNodeParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.blockchain.NodeBlockChainInfoService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;
import com.jgw.supercodeplatform.trace.vo.TraceFunTemplateconfigVO;

@Service
@Transactional
public class DynamicTableService extends AbstractPageService<DynamicTableRequestParam> {
	private static Logger logger = LoggerFactory.getLogger(DynamicTableService.class);
	@Autowired
	private FunctionFieldCache functionFieldManageService;

	@Autowired
	private TraceFunFieldConfigService traceFunFieldConfigService;

	@Autowired
	private TraceBatchInfoService traceBatchInfoService;
	
	@Autowired
	private TraceFunTemplateconfigService traceFunTemplateconfigService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private DynamicServiceDelegate dynamicServiceDelegate;
	
	@Autowired
	private NodeBlockChainInfoService blockChainService;

	@Autowired
	private TraceApplicationContextAware applicationAware;

	@Autowired
	private AntChainInfoService antChainInfoService;

	@Autowired
	private TraceFunComponentMapper traceFunComponentMapper;

	@Autowired
	private TraceFunRegulationMapper traceFunRegulationMapper;

	@Autowired
	private TraceBatchNamedMapper traceBatchNamedMapper;


	@Autowired
	private CommonUtilComponent commonUtilComponent;

	@Autowired
	private TraceBatchRelationMapper traceBatchRelationMapper;

	@Autowired
	private TraceObjectBatchInfoMapper traceObjectBatchInfoMapper;

	@Autowired
	private TraceObjectBatchInfoService traceObjectBatchInfoService;

	@Autowired
	private TraceBatchNamedService traceBatchNamedService;

	@Autowired
	private TraceBatchRelationEsService traceBatchRelationEsService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CodeRelationService codeRelationService;


	private String getBatchInfoId(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String batchInfoId=null;
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				if(objectTypeEnum!=null){
					switch (objectTypeEnum) {
						case TRACE_BATCH:
						case MassifBatch:
							batchInfoId = fieldParam.getObjectUniqueValue();
							break;
						default:
							break;
					}
				}
			}
		}
		return batchInfoId;
	}


	private String getProductName(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String massifId=null;
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				if(objectTypeEnum!=null){
					switch (objectTypeEnum) {
						case PRODUCT:
							massifId = fieldParam.getFieldValue();
							break;
						default:
							break;
					}
				}
			}
		}
		return massifId;
	}

	private String getProductId(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String massifId=null;
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				if(objectTypeEnum!=null){
					switch (objectTypeEnum) {
						case PRODUCT:
							massifId = fieldParam.getObjectUniqueValue();
							break;
						default:
							break;
					}
				}
			}
		}
		return massifId;
	}

	private FieldBusinessParam getObjectParam(LineBusinessData lineBusinessData,String fieldCode)
	{
		List<FieldBusinessParam> params= lineBusinessData.getFields().stream().filter(e->e.getFieldCode().equals(fieldCode)).collect(Collectors.toList());
		FieldBusinessParam param=null;
		if(params!=null&&params.size()>0){
			param=params.get(0);
		}
		return param;
	}

	private FieldBusinessParam getObjectParam(LineBusinessData lineBusinessData,Integer objectCode) throws SuperCodeTraceException
	{
		List<FieldBusinessParam> params=lineBusinessData.getFields().stream().filter(e->e.getObjectType()!=null&& e.getObjectType().intValue()==objectCode.intValue()).collect(Collectors.toList());
		FieldBusinessParam param=null;
		if(params!=null&&params.size()>0){
			param=params.get(0);
		}
		return param;
	}

	private String getMassifName(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String massifName="";
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				switch (objectTypeEnum) {
					case MassifInfo:
						massifName = fieldParam.getFieldValue();
						break;
					default:
						break;
				}
			}
		}
		return massifName;
	}

	private String getDeviceId(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String deviceId=null;
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				switch (objectTypeEnum) {
					case Device:
						deviceId = fieldParam.getObjectUniqueValue();
						break;
					default:
						break;
				}
			}
		}
		return deviceId;
	}

	private String getCodeAssociateType(LineBusinessData lineBusinessData) throws SuperCodeTraceException
	{
		String associateType=null;
		List<FieldBusinessParam> fields=lineBusinessData.getFields();
		for (FieldBusinessParam fieldParam : fields) {
			Integer objectType=fieldParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				switch (objectTypeEnum) {
					case CodeAssociate:
						associateType = fieldParam.getObjectUniqueValue();
						break;
					default:
						break;
				}
			}
		}
		return associateType;
	}

	/**
	 * 新增定制功能数据无法让前端直接传模板id和批次id需要自己找
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public RestResult<String> addFunData(DynamicAddFunParam param, LinkedHashMap<String, Object> identityMap)
			throws Exception {
		String functionId = param.getFunctionId();
		RestResult<String> backResult = new RestResult<String>();
		//校验参数
		AddBusinessDataModel addBusinessDataModel = dynamicServiceDelegate.addMethodSqlBuilderParamValidate(param.getFunctionId(),param.getLineData(),false,null,null);

		//校验批次记录存不存在
		String traceBatchInfoId=addBusinessDataModel.getTraceBatchInfoId();
		TraceBatchInfo traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
		TraceObjectBatchInfo traceObjectBatchInfo=null;
		if (null==traceBatchInfo) {
			traceObjectBatchInfo=traceObjectBatchInfoMapper.selectByTraceBatchInfoId(traceBatchInfoId);
			if(traceObjectBatchInfo!=null){
				Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(traceObjectBatchInfo), Map.class);
				traceBatchInfo = JSONObject.parseObject(JSONObject.toJSONString(map), TraceBatchInfo.class);
			}else {
				/*backResult.setState(500);
				backResult.setMsg("无法根据traceBatchInfoId="+traceBatchInfoId+"查询到记录");
				return backResult;*/
			}
		}
		//获取到表名
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		
		
		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		StringBuilder sqlFieldValueBuilder=new StringBuilder();
		
		dynamicServiceDelegate.funAddOrUpdateSqlBuilder(param.getLineData(), 1,sqlFieldNameBuilder,sqlFieldValueBuilder,false);
		
		String organizationId=null;
		try {
			 organizationId=commonUtil.getOrganizationId();
		} catch (Exception e) {
		}
		
		String insertSql = addDefaultField(tableName, null,organizationId, sqlFieldNameBuilder,
				sqlFieldValueBuilder);
		
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,functionId);
		dao.insert(insertSql);

		List<LinkedHashMap<String, Object>> identityRow= dao.select("select @@IDENTITY");
		String parentId = identityRow.get(0).get("@@IDENTITY").toString();
		identityMap.put("traceBatchInfoId",traceBatchInfoId);
		identityMap.put("ParentId",parentId);
		if(traceBatchInfo!=null){
			identityMap.put("ProductName",traceBatchInfo.getProductName());
			identityMap.put("ProductId",traceBatchInfo.getProductId());
		}

		//更新批次节点数据条数
		try {
			if(traceBatchInfo!=null){
				Map<String, TraceFunFieldConfig> fieldsMap = functionFieldManageService.getFunctionIdFields(null,functionId,1);
				if (null == fieldsMap || fieldsMap.isEmpty()) {
					throw new SuperCodeTraceException("无此功能字段", 500);
				}

				Boolean flag=commonUtil.getTraceSeniorFunFlag();
				if (null!=flag && flag) {
					blockChainService.coChain(param.getLineData(),false,null,fieldsMap,null);
				}

				Boolean traceAntSeniorFunFlag = commonUtil.getTraceAntSeniorFunFlag();
				if(traceAntSeniorFunFlag != null && traceAntSeniorFunFlag){
					antChainInfoService.coChain(param.getLineData(),false,null,fieldsMap,null);
				}
				Integer nodeDataCount=traceBatchInfo.getNodeDataCount();
				if (null==nodeDataCount) {
					traceBatchInfo.setNodeDataCount(1);
				}else {
					traceBatchInfo.setNodeDataCount(nodeDataCount+1);
				}
				if(traceObjectBatchInfo==null){
					traceBatchInfoService.updateTraceBatchInfo(traceBatchInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		backResult.setState(200);
		backResult.setMsg("操作成功");
		return backResult;
	}

	/**
	 * 保存功能组件中的字段数据
	 * @param param
	 * @param identityMap
	 * @throws Exception
	 */
	public void addFunComponentData(DynamicAddFunParam param,LinkedHashMap<String, Object> identityMap) throws Exception {
		String functionId = param.getFunctionId();

		//获取到表名
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);

		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		StringBuilder sqlFieldValueBuilder=new StringBuilder();

		dynamicServiceDelegate.funAddOrUpdateSqlBuilder(param.getLineData(), 1,sqlFieldNameBuilder,sqlFieldValueBuilder,false);

		if(identityMap.get("traceBatchInfoId")!=null){
			String traceBatchInfoId=String.valueOf(identityMap.get("traceBatchInfoId"));
			sqlFieldNameBuilder.append(ObjectTypeEnum.TRACE_BATCH.getFieldCode()).append(",");
			sqlFieldValueBuilder.append("'").append(traceBatchInfoId).append("'").append(",");
		}

		String parentId=identityMap.get("ParentId").toString();
		sqlFieldNameBuilder.append("ParentId").append(",");
		sqlFieldValueBuilder.append("'").append(parentId).append("'").append(",");

		String organizationId=null;
		try {
			organizationId=commonUtil.getOrganizationId();
		} catch (Exception e) {
		}

		String insertSql = addDefaultField(tableName, null,organizationId, sqlFieldNameBuilder,
				sqlFieldValueBuilder);

		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,functionId);
		dao.insert(insertSql);
	}


	/**
	 * 根据使用场景自动创建批次和批次关联数据
	 * @param traceFunRegulation
	 * @param param
	 * @param parentTraceBatchInfo
	 * @return
	 * @throws Exception
	 */
	private List<BaseBatchInfo> CreateBatchInfoWithRelation(TraceFunRegulation traceFunRegulation, DynamicAddFunParam param,TraceBatchInfo parentTraceBatchInfo) throws Exception
	{
		String traceTemplateId="cd153b772694428e8435ad5e594f90c5";
		String traceTemplateName="桃溯源模板419";
		Integer userSceneType= traceFunRegulation.getUseSceneType();
		String productName=null,productId=null,parentTraceBatchInfoId=null;
		if(parentTraceBatchInfo!=null){
			productName = parentTraceBatchInfo.getProductName();
			productId=parentTraceBatchInfo.getProductId();
			parentTraceBatchInfoId=parentTraceBatchInfo.getTraceBatchInfoId();
		}
		int createBatchType = traceFunRegulation.getCreateBatchType();
		int objectAssociatedType= traceFunRegulation.getObjectAssociatedType();

		ObjectTypeEnum objectTypeEnum= ObjectTypeEnum.getType(objectAssociatedType);
		int parentBatchTableType=BatchTableType.getBatchTableType(objectTypeEnum).getKey();


		List<BaseBatchInfo> baseBatchInfos=new ArrayList<BaseBatchInfo>();
		if(userSceneType == TraceUseSceneEnum.CreateBatch.getKey()) {
			//创建关联对象批次
			if(objectAssociatedType == ObjectTypeEnum.MassifInfo.getCode())
				createBatchType=ObjectTypeEnum.MassifBatch.getCode();
			else if(objectAssociatedType == ObjectTypeEnum.PRODUCT.getCode()){
				createBatchType=ObjectTypeEnum.TRACE_BATCH.getCode();
				productName=getProductName(param.getLineData());
				productId= getProductId(param.getLineData());
			}
			BaseBatchInfo baseBatchInfo=new BaseBatchInfo(productName,productId);
			String traceBatchInfoId=null;
			String traceBatchName=traceBatchNamedService.buildBatchName(traceFunRegulation,baseBatchInfo);

			if(createBatchType==ObjectTypeEnum.MassifBatch.getCode()){
				String massifId=null;
				FieldBusinessParam massifParam= getObjectParam(param.getLineData(),ObjectTypeEnum.MassifInfo.getCode());
				if(massifParam!=null){
					massifId=massifParam.getObjectUniqueValue();
				}
				TraceObjectBatchInfo traceObjectBatchInfo=new TraceObjectBatchInfo();
				traceObjectBatchInfo.setTraceBatchName(traceBatchName);
				traceObjectBatchInfo.setBatchType(ObjectTypeEnum.MassifBatch.getCode());
				traceObjectBatchInfo.setSerialNumber(baseBatchInfo.getSerialNumber());
				traceObjectBatchInfo.setObjectId(massifId);
				traceObjectBatchInfo.setTraceTemplateId(traceTemplateId);
				traceObjectBatchInfo.setTraceTemplateName(traceTemplateName);
				traceObjectBatchInfoService.insertTraceObjectBatchInfo(traceObjectBatchInfo);
				traceBatchInfoId = traceObjectBatchInfo.getTraceBatchInfoId();
			}else if(createBatchType == ObjectTypeEnum.TRACE_BATCH.getCode()){

				TraceBatchInfo traceBatchInfo=new TraceBatchInfo(traceBatchName,productId,productName,traceBatchName,traceTemplateId,traceTemplateName,createBatchType,baseBatchInfo.getSerialNumber());
				traceBatchInfoService.insertTraceBatchInfo(traceBatchInfo);
				traceBatchInfoId = traceBatchInfo.getTraceBatchInfoId();
			}

			baseBatchInfo.setTraceBatchName(traceBatchName);
			baseBatchInfo.setTraceBatchInfoId(traceBatchInfoId);
			baseBatchInfos.add(baseBatchInfo);

			TraceBatchRelation traceBatchRelation=new TraceBatchRelation();
			traceBatchRelation.setCurrentBatchId(traceBatchInfoId);
			traceBatchRelation.setBatchRelationId(getUUID());
			traceBatchRelationEsService.insertTraceBatchRelation(traceBatchRelation);

			List<FieldBusinessParam> fieldBusinessParams= param.getLineData().getFields();
			List<FieldBusinessParam> batchParams= fieldBusinessParams.stream().filter(e->e.getFieldCode().equals("TraceBatchInfoId")).collect(Collectors.toList());
			batchParams.get(0).setFieldValue(baseBatchInfo.getTraceBatchInfoId());

		} else if(userSceneType==TraceUseSceneEnum.CreateBatchInheritNodeData.getKey()) {
			//批次继承，创建新批次并继承溯源信息
			if(StringUtils.isEmpty(productId)){
				productName=getProductName(param.getLineData());
				productId= getProductId(param.getLineData());
			}
			BaseBatchInfo baseBatchInfo=new BaseBatchInfo(productName,productId);
			String traceBatchName=traceBatchNamedService.buildBatchName(traceFunRegulation,baseBatchInfo);

			TraceBatchInfo traceBatchInfo=new TraceBatchInfo(traceBatchName,productId,productName,traceBatchName,traceTemplateId,traceTemplateName,createBatchType,baseBatchInfo.getSerialNumber());
			traceBatchInfoService.insertTraceBatchInfo(traceBatchInfo);

			baseBatchInfo.setTraceBatchName(traceBatchName);
			baseBatchInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
			baseBatchInfos.add(baseBatchInfo);

			TraceBatchRelation traceBatchRelation=new TraceBatchRelation(getUUID(),traceBatchInfo.getTraceBatchInfoId(),parentTraceBatchInfoId,parentBatchTableType);
			traceBatchRelationEsService.insertTraceBatchRelation(traceBatchRelation);
		} else if(userSceneType==TraceUseSceneEnum.BatchMixWithSameObject.getKey()){
			//批次混合，多个相同关联对象批次混合并创建新对象批次
			if(!StringUtils.isEmpty(parentTraceBatchInfoId)){
				String[] parentTraceBatchInfoIds= parentTraceBatchInfoId.split(",");
				BaseBatchInfo baseBatchInfo=new BaseBatchInfo(productName,productId);
				String traceBatchName=traceBatchNamedService.buildBatchName(traceFunRegulation,baseBatchInfo);

				TraceBatchInfo traceBatchInfo=new TraceBatchInfo(traceBatchName,productId,productName,traceBatchName,traceTemplateId,traceTemplateName,createBatchType,baseBatchInfo.getSerialNumber());
				traceBatchInfoService.insertTraceBatchInfo(traceBatchInfo);

				baseBatchInfo.setTraceBatchName(traceBatchName);
				baseBatchInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
				baseBatchInfos.add(baseBatchInfo);

				for(String id : parentTraceBatchInfoIds){
					TraceBatchRelation traceBatchRelation=new TraceBatchRelation(getUUID(),traceBatchInfo.getTraceBatchInfoId(),id,parentBatchTableType) ;
					traceBatchRelationEsService.insertTraceBatchRelation(traceBatchRelation);
				}
			}

		} else if(userSceneType==TraceUseSceneEnum.BatchMixWithDistinctObject.getKey()){

		}else if(userSceneType==TraceUseSceneEnum.BatchDivide.getKey()){
			//批次分裂，由关联对象分裂成为多个嵌套对象的批次
			String divideRule= traceFunRegulation.getSplittingRule();
			List<FunComponentDataModel> componentDataModels= param.getLineData().getFunComponentDataModels().stream().filter(e->e.getComponentName().equals(divideRule)).collect(Collectors.toList());
			if(componentDataModels!=null && componentDataModels.size()>0){
				List<List<FieldBusinessParam>> dataModels=	componentDataModels.get(0).getFieldRows();
				for(List<FieldBusinessParam> fieldBusinessParams:dataModels){
					BaseBatchInfo baseBatchInfo=new BaseBatchInfo(productName,productId);
					String traceBatchName=traceBatchNamedService.buildBatchName(traceFunRegulation,baseBatchInfo);

					TraceBatchInfo traceBatchInfo=new TraceBatchInfo(traceBatchName,productId,productName,traceBatchName,traceTemplateId,traceTemplateName,createBatchType,baseBatchInfo.getSerialNumber());
					traceBatchInfoService.insertTraceBatchInfo(traceBatchInfo);

					baseBatchInfo.setTraceBatchName(traceBatchName);
					baseBatchInfo.setTraceBatchInfoId(traceBatchInfo.getTraceBatchInfoId());
					baseBatchInfos.add(baseBatchInfo);

					TraceBatchRelation traceBatchRelation=new TraceBatchRelation(getUUID(),traceBatchInfo.getTraceBatchInfoId(),parentTraceBatchInfoId,parentBatchTableType) ;
					traceBatchRelationEsService.insertTraceBatchRelation(traceBatchRelation);
				}
			}

		}

		return baseBatchInfos;
	}

	private RestResult<String> addFunData(DynamicAddFunParam param,TraceFunRegulation traceFunRegulation) throws Exception{

		RestResult<String> restResult=null;
		LinkedHashMap<String, Object> identityMap=new LinkedHashMap<String, Object>();
		//保存定制功能主表中的字段数据
		restResult=addFunData(param,identityMap);

		String deviceId=getDeviceId(param.getLineData());
		if(!StringUtils.isEmpty(deviceId)){
			//添加设备使用记录
			String operationalContent=getMassifName(param.getLineData())+traceFunRegulation.getFunctionName();
			deviceService.insertUsageInfo(deviceId,operationalContent);
		}
		String associateType=getCodeAssociateType(param.getLineData());
		if(!StringUtils.isEmpty(associateType)){
			//添加码关联信息
			codeRelationService.insertCodeRelationInfo(param.getLineData().getFields());
		}

		List<FunComponentDataModel> componentDataModels= param.getLineData().getFunComponentDataModels();
		if (componentDataModels!=null && componentDataModels.size()>0){
			for(FunComponentDataModel funComponentDataModel:componentDataModels){
				if(ComponentTypeEnum.isNestComponent(funComponentDataModel.getComponentType())){
					List<List<FieldBusinessParam>> fieldRows= funComponentDataModel.getFieldRows();
					if (fieldRows!=null && fieldRows.size()>0){
						for(List<FieldBusinessParam> fields: fieldRows){
							DynamicAddFunParam componentFunParam=new DynamicAddFunParam();
							componentFunParam.setFunctionId(funComponentDataModel.getComponentId());
							LineBusinessData lineBusinessData=new LineBusinessData();
							lineBusinessData.setFields(fields);
							componentFunParam.setLineData(lineBusinessData);

							//保存功能组件中的字段数据
							addFunComponentData(componentFunParam,identityMap);

							if(funComponentDataModel.getComponentType()== ComponentTypeEnum.MaterielCompent.getKey()){
								//添加物料出库记录
								materialService.insertOutOfStockInfo(fields);
							}
						}
					}
				}
			}
		}
		return restResult;
	}



	/**
	 * 定制功能保存数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public RestResult<String> addFunDataV3(DynamicAddFunParam param) throws Exception
	{
		TraceBatchInfo traceBatchInfo=null;
		FieldBusinessParam massifParam= getObjectParam(param.getLineData(), ObjectTypeEnum.MassifInfo.getCode());
		if(massifParam!=null){
			FieldBusinessParam traceBatchInfoParam= getObjectParam(param.getLineData(),"TraceBatchInfoId");
			if(traceBatchInfoParam!=null && StringUtils.isEmpty(traceBatchInfoParam.getFieldValue())){
				TraceObjectBatchInfo traceObjectBatchInfo= traceObjectBatchInfoMapper.selectByObjectId(massifParam.getObjectUniqueValue());
				if(traceObjectBatchInfo!=null){
					traceBatchInfoParam.setFieldValue(traceObjectBatchInfo.getTraceBatchInfoId());
					traceBatchInfo= commonUtil.convert(traceObjectBatchInfo,TraceBatchInfo.class);
				}
			}
		}

		String traceBatchInfoId= getBatchInfoId(param.getLineData());
		if (traceBatchInfo==null){
			if(!StringUtils.isEmpty(traceBatchInfoId)){
				traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
			}
		}

		TraceFunRegulation traceFunRegulation= traceFunRegulationMapper.selectByFunId(param.getFunctionId());

		List<BaseBatchInfo> baseBatchInfos=null;
		if(traceFunRegulation!=null && traceFunRegulation.getRegulationType() == RegulationTypeEnum.ControlNode.getKey()) //控制节点
		{
			//根据使用场景自动创建批次和批次关联数据
			baseBatchInfos=CreateBatchInfoWithRelation(traceFunRegulation,param,traceBatchInfo);
		}else if(traceFunRegulation!=null && traceFunRegulation.getRegulationType() == RegulationTypeEnum.ProcedureNode.getKey())
		{
			if(traceBatchInfo!=null){
				baseBatchInfos = new ArrayList<BaseBatchInfo>();
				BaseBatchInfo baseBatchInfo=new BaseBatchInfo(traceBatchInfo.getTraceBatchInfoId());
				baseBatchInfo.setTraceBatchName(traceBatchInfo.getTraceBatchName());
				baseBatchInfos.add(baseBatchInfo);
			}
		}

		RestResult<String> restResult=null;

/*		if(baseBatchInfos!=null&& baseBatchInfos.size()>0){
			for (BaseBatchInfo baseBatchInfo:baseBatchInfos){
				List<FieldBusinessParam> fieldBusinessParams= param.getLineData().getFields();
				List<FieldBusinessParam> batchParams= fieldBusinessParams.stream().filter(e->e.getFieldCode().equals("TraceBatchInfoId")).collect(Collectors.toList());
				batchParams.get(0).setFieldValue(baseBatchInfo.getTraceBatchInfoId());
				//fieldBusinessParams.add(new FieldBusinessParam("traceBatchInfoId", baseBatchInfo.getTraceBatchInfoId()));
				//fieldBusinessParams.add(new FieldBusinessParam("traceBatchName", baseBatchInfo.getTraceBatchName()));
				restResult= addFunData(param);
			}
		}else {
			restResult= addFunData(param);
		}*/

		restResult= addFunData(param,traceFunRegulation);

		return restResult;
	}

    /**
     * 新增节点业务数据模板id和批次id都可以从前端获取
     * @param param
     * @return
     * @throws Exception 
     */
	public RestResult<String> addNodeData(DynamicAddNodeParam param)
			throws Exception {
		RestResult<String> backResult = new RestResult<String>();
		String functionId = param.getFunctionId();
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		String traceTemplateId=param.getTraceTemplateId();
		if (StringUtils.isBlank(traceTemplateId)) {
			throw new SuperCodeTraceException("模板id不能为空", 500);
		}else {
			List<TraceFunTemplateconfigVO> traceFunTemplateconfigList = traceFunTemplateconfigService.listNodes(traceTemplateId);
			if (null==traceFunTemplateconfigList || traceFunTemplateconfigList.isEmpty()) {
				throw new SuperCodeTraceException("无法根据该模板id查询出一条模板节点记录", 500);
			}
			param.setTraceTemplateId(traceTemplateId);
		}
		//校验该传的参数有没有传
		AddBusinessDataModel adDataModel = dynamicServiceDelegate.addMethodSqlBuilderParamValidate(param.getFunctionId(),param.getLineData(),true,param.getTraceBatchInfoId(),param.getTraceTemplateId());

		
		//校验批次记录存不存在
		String traceBatchInfoId=adDataModel.getTraceBatchInfoId();
		TraceBatchInfo traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
		if (null==traceBatchInfo) {
			backResult.setState(500);
			backResult.setMsg("无法根据traceBatchInfoId="+traceBatchInfoId+"查询到记录");
			return backResult;
		}
		
		//获取表名
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		
		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		StringBuilder sqlFieldValueBuilder=new StringBuilder();
		
		//拼装sql
		dynamicServiceDelegate.funAddOrUpdateSqlBuilder(adDataModel.getLineData(), 1,sqlFieldNameBuilder,sqlFieldValueBuilder,true);
		
		//判断当前sql里是否包含批次信息
		int batchInfoId=sqlFieldNameBuilder.indexOf("TraceBatchInfoId");
		if (batchInfoId<0) {
			sqlFieldNameBuilder.append(" TraceBatchInfoId,");
			sqlFieldValueBuilder.append("'").append(traceBatchInfoId).append("',");
		}
		
		String organizationId=commonUtil.getOrganizationId();
		//组装插入sql
		String insertSql = addDefaultField(tableName, traceTemplateId,organizationId, sqlFieldNameBuilder,
				sqlFieldValueBuilder);
		
		//根据模板id和功能id获取对应的库mapper
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		//插入
		dao.insert(insertSql);
		
		try {
			//数据上链
			Map<String, TraceFunFieldConfig> fieldsMap = functionFieldManageService.getFunctionIdFields(traceTemplateId,functionId,2);
			if (null == fieldsMap || fieldsMap.isEmpty()) {
				throw new SuperCodeTraceException("无此功能字段", 500);
			}

			Boolean flag=commonUtil.getTraceSeniorFunFlag();
			if (null!=flag && flag) {
				blockChainService.coChain(param.getLineData(),true,traceBatchInfoId,fieldsMap,traceBatchInfo);
			}
			Boolean traceAntSeniorFunFlag = commonUtil.getTraceAntSeniorFunFlag();
			if(traceAntSeniorFunFlag != null && traceAntSeniorFunFlag){
				antChainInfoService.coChain(param.getLineData(),true,traceBatchInfoId,fieldsMap,traceBatchInfo);
			}
			//插入成功更新批次节点数据条数
			Integer nodeDataCount = traceBatchInfo.getNodeDataCount();
			if (null == nodeDataCount) {
				traceBatchInfo.setNodeDataCount(1);
			} else {
				traceBatchInfo.setNodeDataCount(nodeDataCount + 1);
			}
			traceBatchInfoService.updateTraceBatchInfo(traceBatchInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		backResult.setState(200);
		backResult.setMsg("操作成功");
		return backResult;
	}
    /**
     * 注意进入这个方法事 StringBuilder sqlFieldValueBuilder后面都有一个人','
     * @param tableName
     * @param traceTemplateId
     * @param organizationId
     * @param sqlFieldNameBuilder
     * @param sqlFieldValueBuilder
     * @return
     * @throws SuperCodeException
     */
	private String addDefaultField(String tableName, String traceTemplateId,String organizationId,
			StringBuilder sqlFieldNameBuilder, StringBuilder sqlFieldValueBuilder) throws SuperCodeException {
	
		sqlFieldNameBuilder.append(" SortDateTime");
		sqlFieldValueBuilder.append(System.currentTimeMillis());
		
		
		if (StringUtils.isNotBlank(organizationId)) {
			sqlFieldNameBuilder.append(",").append(" OrganizationId");
			sqlFieldValueBuilder.append(",").append("'").append(organizationId).append("'");
		}
		
		if (StringUtils.isNotBlank(traceTemplateId)) {
			sqlFieldNameBuilder.append(",").append(" TraceTemplateId");
			sqlFieldValueBuilder.append(",").append("'").append(traceTemplateId).append("'");
		}
		
		String fieldNames =sqlFieldNameBuilder.toString();
		String fieldValues = sqlFieldValueBuilder.toString();
		String insertSql = "insert into " + tableName + " (" + fieldNames + ") values (" + fieldValues + ")";
		return insertSql;
	}

	/**
	 * 删除节点
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public RestResult<List<String>> delete(DynamicDeleteNodeParam param)
			throws Exception {
		return dynamicServiceDelegate.delete(param.getFunctionId(),param.getIds(),param.getTraceTemplateId());
	}
    /**
     * 删除定制功能业务数据
     * @param param
     * @return
     * @throws Exception 
     */
	public RestResult<List<String>> deleteFun(DynamicDeleteFunParam param) throws Exception {
		return dynamicServiceDelegate.delete(param.getFunctionId(),param.getIds(),null);
	}

	public RestResult<String> update(String functionId, LineBusinessData lineData, boolean isNode, String traceTemplateId)
			throws SuperCodeTraceException, SuperCodeException {
		RestResult<String> backResult = new RestResult<String>();

		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		if (isNode) {
			if (StringUtils.isBlank(traceTemplateId)) {
				throw new SuperCodeTraceException("修改溯源节点数据时traceTemplateId不能为空", 500);
			}
		}


		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		StringBuilder sqlFieldValueBuilder=new StringBuilder();
		updateDynamicTable(lineData,tableName,dao,isNode,sqlFieldValueBuilder);

		List<FunComponentDataModel> funComponentDataModels= lineData.getFunComponentDataModels();
		if(funComponentDataModels!=null && funComponentDataModels.size()>0){
			for(FunComponentDataModel funComponentDataModel:funComponentDataModels){
				List<List<FieldBusinessParam>> fieldRows= funComponentDataModel.getFieldRows();
				if(fieldRows!=null && fieldRows.size()>0){
					for(List<FieldBusinessParam> fieldRow:fieldRows){
						LineBusinessData funComponentlineData=new LineBusinessData();
						funComponentlineData.setFields(fieldRow);
						String compTableName = traceFunFieldConfigService.getEnTableNameByFunctionId(funComponentDataModel.getComponentId());
						StringBuilder sqlFunFieldValueBuilder=new StringBuilder();
						updateDynamicTable(funComponentlineData,compTableName,dao,isNode,sqlFunFieldValueBuilder);
					}
				}
			}
		}
		
		try {
			Map<String, TraceFunFieldConfig> fieldsMap =null;
			if (isNode) {
				if (StringUtils.isBlank(traceTemplateId)) {
					throw new SuperCodeTraceException("节点业务数据查询必须传模板id", 500);
				}
				fieldsMap = functionFieldManageService.getFunctionIdFields(traceTemplateId,functionId,2);
			} else {
				fieldsMap = functionFieldManageService.getFunctionIdFields(null,functionId,1);
			}
			if (null == fieldsMap || fieldsMap.isEmpty()) {
				throw new SuperCodeTraceException("无此功能字段", 500);
			}
			//获取当前节点或功能的字段
			NodeOrFunFields nodeOrFunFields=dynamicServiceDelegate.selectFields(fieldsMap);
			String querySQL="select "+nodeOrFunFields.getFields()+" from "+tableName+sqlFieldValueBuilder.toString();
			List<LinkedHashMap<String, Object>> listDdata=dao.select(querySQL);
			blockChainService.updateCoChain(listDdata,nodeOrFunFields.isContainObj(),fieldsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		backResult.setMsg("操作成功");
		backResult.setState(200);
		return backResult;
	}

	private void updateDynamicTable(LineBusinessData lineData,String tableName,DynamicBaseMapper dao,boolean isNode,StringBuilder sqlFieldValueBuilder) throws SuperCodeTraceException, SuperCodeException
	{
		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		dynamicServiceDelegate.funAddOrUpdateSqlBuilder(lineData, 3,sqlFieldNameBuilder,sqlFieldValueBuilder,isNode);

		//拼装查询语句
		String queryIdSQL="select Id from "+tableName+sqlFieldValueBuilder.toString();
		List<LinkedHashMap<String, Object>> idlistDdata=dao.select(queryIdSQL);
		if (null==idlistDdata || idlistDdata.isEmpty()) {
			throw new SuperCodeTraceException("要修改的记录不存在", 500);
		}

		String sql = "update " + tableName + " set " ;
		String fieldNames = sqlFieldNameBuilder.substring(0, sqlFieldNameBuilder.length()-1);
		sql=sql+ fieldNames+sqlFieldValueBuilder.toString() ;
		dao.update(sql);
	}

    /**
     *  定制功能列表查询
     * @param param
     * @return
     * @throws Exception
     */
	public RestResult<PageResults<List<Map<String, Object>>>> list(DynamicTableRequestParam param) throws Exception {
		RestResult<PageResults<List<Map<String, Object>>>> result=new RestResult<PageResults<List<Map<String, Object>>>>();
		String functionId=param.getFunctionId();
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("请求头里的functionId不能为空", 500);
		}
		PageResults<List<Map<String, Object>>> p = listSearchViewLike(param);
		result.setMsg("成功");
		result.setState(200);
		result.setResults(p);
        return result;
	}
    /**
     * 定制功能列表查询记录
     */
	@Override
	protected List<LinkedHashMap<String, Object>> searchResult(DynamicTableRequestParam param)
			throws SuperCodeTraceException, SuperCodeException {
		String orgnizationId=commonUtil.getOrganizationId();
		String sql = querySqlBuilder(null,null,param.getFunctionId(), null,null, false,false,orgnizationId,param);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,param.getFunctionId());
		List<LinkedHashMap<String, Object>> list = dao.select(sql);

		String funId=param.getFunctionId();
		getFunComponentData(funId,list);

		return list;
	}

	private void getFunComponentData(String funId,List<LinkedHashMap<String, Object>> list)
			throws SuperCodeTraceException, SuperCodeException {

		String orgnizationId=commonUtil.getOrganizationId();
		List<TraceFunComponent> traceFunComponents= traceFunComponentMapper.selectByFunId(funId);

		//遍历定制功能列表数据，根据主表Id到功能组件数据表中查询组件数据
		for (TraceFunComponent traceFunComponent: traceFunComponents){
			if(!ComponentTypeEnum.isNestComponent( traceFunComponent.getComponentType()))
				continue;
			if(list!=null && list.size()>0){
				List<String> ids= list.stream().map(e->e.get("Id").toString()).collect(Collectors.toList());
				String componentId=traceFunComponent.getComponentId();
				String sql=null;
				try {
					sql = queryComponentSqlBuilder(componentId, orgnizationId, ids);
				}catch (Exception e){
					e.printStackTrace();
					continue;
				}
				DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,componentId);
				List<LinkedHashMap<String, Object>> componentDataList = dao.select(sql);
				if (componentDataList!=null && componentDataList.size()>0){
					for (LinkedHashMap<String, Object> rowMap:list){
						List<LinkedHashMap<String, Object>> childRows= componentDataList.stream().filter(e->e.get("ParentId")!=null && e.get("ParentId").toString().equals(rowMap.get("Id").toString())).collect(Collectors.toList());
						if(childRows!=null && childRows.size()>0){
							ArrayList<HashMap<String,Object>> components=(ArrayList<HashMap<String,Object>>)rowMap.get("components");
							if (components==null){
								components=new ArrayList<HashMap<String, Object>>();
								rowMap.put("components",components);
							}
							HashMap<String, Object> component= new HashMap<String, Object>();
							component.put("componentId",traceFunComponent.getComponentId());
							component.put("fields",childRows);
							components.add(component);
						}
					}
				}
			}
		}
	}

    /**
     * 定制功能列表查询统计数量
     */
	@Override
	protected int count(DynamicTableRequestParam param) throws Exception {
		String orgnizationId=commonUtil.getOrganizationId();
		String sql = querySqlBuilder(null,null,param.getFunctionId(), null,null, true,false,orgnizationId,param);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,param.getFunctionId());
		return dao.count(sql);
	}

    /**
     * {@code}该方法支持节点数据查询也支持定制功能数据查询
     * @param traceBatchInfoId
     * @param traceTemplateId
     * @param functionId
     * @param tableName
     * @param nodeType
     * @param isCount
     * @param orgnizationId 
     * @param searchParam
     * @return
     * @throws SuperCodeTraceException
     * @throws SuperCodeException
     */
	private String querySqlBuilder(String traceBatchInfoId,String traceTemplateId,String functionId, String tableName,String nodeType, 
			boolean isCount,boolean fromH5Page,String orgnizationId, DaoSearch searchParam)
			throws SuperCodeTraceException, SuperCodeException {

		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		
		Map<String, TraceFunFieldConfig> fieldsMap =null;
		if (StringUtils.isBlank(nodeType)) {
			fieldsMap = functionFieldManageService
					.getFunctionIdFields(null,functionId,1);
		} else {
			if (StringUtils.isBlank(traceTemplateId)) {
				throw new SuperCodeTraceException("节点业务数据查询必须传模板id", 500);
			}
			fieldsMap = functionFieldManageService
					.getFunctionIdFields(traceTemplateId,functionId,2);
		}
		if (null == fieldsMap || fieldsMap.isEmpty()) {
			throw new SuperCodeTraceException("无此功能字段", 500);
		}
		
		if (StringUtils.isBlank(tableName)) {
			tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
			if (StringUtils.isBlank(tableName)) {
				throw new SuperCodeTraceException("动态表查询querySqlBuilder方法，无法根据功能id：" + functionId + "获取表名", 500);
			}
		}

		String sortFiled = "SortDateTime";

		Map<String, TraceFunFieldConfig> sortFileds= fieldsMap.entrySet().stream().filter((e)->e.getValue().getFieldType().equals("6")).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

		if (sortFileds.size()>0){
			Map.Entry<String, TraceFunFieldConfig> field= sortFileds.entrySet().iterator().next();
			sortFiled = field.getValue().getFieldCode();
		}

		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		
		//来源于h5过滤掉已被隐藏的记录
		if (fromH5Page) {
			sqlFieldValueBuilder.append(" where  (DeleteStatus!=1 or DeleteStatus is null) ");
			//如果是生产管理里的查询则没有批次id
			if (StringUtils.isNotBlank(traceBatchInfoId)) {
				if (StringUtils.isNotBlank(nodeType) && !"3".equals(nodeType)) {
					
					sqlFieldValueBuilder.append(" and TraceBatchInfoId='").append(traceBatchInfoId).append("'");
				}
			}else {
				//有组织信息就添加组织信息
				if (StringUtils.isNotBlank(orgnizationId)) {
					sqlFieldValueBuilder.append(" and OrganizationId='").append(orgnizationId).append("'");

				}
			}
		}else {
			
			//如果是生产管理里的查询则没有批次id
			if (StringUtils.isNotBlank(traceBatchInfoId) && !"3".equals(nodeType)) {
				sqlFieldValueBuilder.append(" where TraceBatchInfoId='").append(traceBatchInfoId).append("'");
			}else {
				if (StringUtils.isNotBlank(orgnizationId)) {
					sqlFieldValueBuilder.append(" where OrganizationId='").append(orgnizationId).append("'");
				}
			}
		}
		
		Integer flag=searchParam.getFlag();
		if (null == flag) {
			// 无查询
			logger.info("flag为空，无参数查询，使用默认的模板id和组织id过滤");
		} else {

			if (1 == flag) {
				// 普通搜索--拼装所有字段匹配
				String search=searchParam.getSearch();
				if (null == search) {
					throw new SuperCodeTraceException("普通搜索 search不能为空", 500);
				}
				
				if (sqlFieldValueBuilder.length()==0) {
					sqlFieldValueBuilder.append(" where ");
				}else {
					sqlFieldValueBuilder.append(" and ");
				}
				sqlFieldValueBuilder.append("  (");
				for (String fieldCode : fieldsMap.keySet()) {
					if (!FunctionFieldCache.defaultCreateFields.contains(fieldCode)) {
						sqlFieldValueBuilder.append(fieldCode).append(" like binary ").append("'%").append(search).append("%'");
							sqlFieldValueBuilder.append(" or ");
					}
					
				}
				sqlFieldValueBuilder.replace(sqlFieldValueBuilder.length()-4, sqlFieldValueBuilder.length(), "");
				sqlFieldValueBuilder.append(")");

			} else {
				// 高级搜索
				Map<String,String> parmsMap=searchParam.getParams();
				if (null == parmsMap || parmsMap.isEmpty()) {
					throw new SuperCodeTraceException("高级搜索搜索 params不能为空", 500);
				}
				
				if (sqlFieldValueBuilder.length()==0) {
					sqlFieldValueBuilder.append(" where ");
				}else {
					sqlFieldValueBuilder.append(" and ");
				}
				for (String fieldCode : parmsMap.keySet()) {
					if (!FunctionFieldCache.defaultCreateFields.contains(fieldCode)) {
						String fieldValue = parmsMap.get(fieldCode);
						sqlFieldValueBuilder.append(fieldCode).append("=").append("'").append(fieldValue).append("'");
							sqlFieldValueBuilder.append(" and ");
					}
				}
				sqlFieldValueBuilder.replace(sqlFieldValueBuilder.length()-4, sqlFieldValueBuilder.length(), "");
			}
		}

		String sql = null;
		if (isCount) {
			sql = "select count(*) from " + tableName + sqlFieldValueBuilder.toString();
		} else {
			sqlFieldValueBuilder.append(" order by " + sortFiled+" desc ");

			if (null !=searchParam.getStartNumber() && null != searchParam.getPageSize()) {
				sqlFieldValueBuilder.append(" limit ").append(searchParam.getStartNumber()).append(",")
						.append(searchParam.getPageSize());
			}
			
			NodeOrFunFields nodeOrFunFields =dynamicServiceDelegate.selectFields(fieldsMap);
					
			sql = "select " + nodeOrFunFields.getFields() + " from " + tableName + sqlFieldValueBuilder.toString();
		}
		return sql;
	}

	private String queryComponentSqlBuilder(String functionId,String orgnizationId, List<String> ids)
			throws SuperCodeTraceException, SuperCodeException
	{
		Map<String, TraceFunFieldConfig> fieldsMap  = functionFieldManageService.getFunctionIdFields(null,functionId,1);

		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);

		NodeOrFunFields nodeOrFunFields =dynamicServiceDelegate.selectFields(fieldsMap);

		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		sqlFieldValueBuilder.append(" where OrganizationId='").append(orgnizationId).append("'");
		sqlFieldValueBuilder.append(" and ");
		sqlFieldValueBuilder.append( String.format("ParentId in (%s)",String.join(",", ids)));

		String sql = "select " + nodeOrFunFields.getFields() + " from " + tableName + sqlFieldValueBuilder.toString();
		return sql;
	}

	public RestResult<String> hide(DynamicHideParam param) throws SuperCodeTraceException {
		return dynamicServiceDelegate.hide(param);
	}
    
	public List<LinkedHashMap<String, Object>> queryTemplateNodeBatchData(String traceBatchInfoId, String traceTemplateId, String functionId,String tableName,String businessType, boolean fromH5, String orgnizationId) throws SuperCodeTraceException, SuperCodeException {
		String sql = querySqlBuilder(traceBatchInfoId,traceTemplateId,functionId, tableName, businessType,false,fromH5,orgnizationId,new DaoSearch());
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		List<LinkedHashMap<String, Object>> list = dao.select(sql);
		return list;
		
	}
	/**
	 * 获取单条记录
	 * @param id
	 * @param functionId
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public RestResult<Map<String, Object>> getById(Long id, String functionId) throws Exception {
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,functionId);
		Map<String, TraceFunFieldConfig> fieldsMap = functionFieldManageService.getFunctionIdFields(null,functionId,1);
		if (null == fieldsMap || fieldsMap.isEmpty()) {
			throw new SuperCodeTraceException("无此功能字段", 500);
		}
		
		RestResult<Map<String, Object>> restResult=new RestResult<Map<String, Object>>();
		// 返回的字段已经过fieldWeight排序
		StringBuilder fieldNameBuilder = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			TraceFunFieldConfig fieldConfig=fieldsMap.get(key);
			if (null!=fieldConfig) {
				String fieldType=fieldConfig.getFieldType();
				if ("8".equals(fieldType)) {
					fieldNameBuilder.append("DATE_FORMAT("+key+",'%Y-%m-%d %H:%i:%S')");
				}
			}
			fieldNameBuilder.append(key).append(",");
		}
		String fields = fieldNameBuilder.substring(0, fieldNameBuilder.length() - 1);
		String sql = "select " + fields + " from " + tableName + " where Id="+id;
		List<LinkedHashMap<String, Object>> list = dao.select(sql);

		getFunComponentData(functionId,list);

		if (null!=list && !list.isEmpty()) {
			restResult.setResults(list.get(0));
		}
		restResult.setMsg("成功");
		restResult.setState(200);
		return restResult;
	}

}
