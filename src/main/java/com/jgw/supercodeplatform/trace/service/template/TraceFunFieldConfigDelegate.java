package com.jgw.supercodeplatform.trace.service.template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceFunComponentMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceFunRegulationMapper;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.BatchNamedRuleField;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.CustomizeFun;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.FunComponent;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldSortCaram;
import com.jgw.supercodeplatform.trace.enums.ComponentTypeEnum;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunComponent;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import com.jgw.supercodeplatform.trace.service.tracefun.TraceBatchNamedService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.DefaultFieldEnum;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;


/**
 * 负责字段配置及模板节点字段管理
 * @author czm
 *
 */
@Component
public class TraceFunFieldConfigDelegate {
	private static Logger logger = LoggerFactory.getLogger(TraceFunFieldConfigDelegate.class);
	@Autowired
	private TraceOrgFunRouteMapper traceOrgFunRouteDao;
	
	@Autowired
	private TraceFunFieldConfigMapper dao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	@Autowired
	private FunctionFieldCache functionFieldManageService;

	@Autowired
	private TraceFunComponentMapper traceFunComponentMapper;

	@Autowired
	private TraceFunRegulationMapper traceFunRegulationMapper;

	@Autowired
	private TraceBatchNamedService traceBatchNamedService;
	
	//字段默认值的最大长度
	private static int defaultValueMaxSise;
	
	@Value("${defaultValue.maxSixe}")
	public  void setDefaultValueMaxSise(int defaultValueMaxSise) {
		TraceFunFieldConfigDelegate.defaultValueMaxSise = defaultValueMaxSise;
	}
	/**
     * 溯源模板新增节点逻辑 
     * @param param
     * @param organizationId
     * @param isAdd
     * @param nodeFunctionId
     * @param nodeFunctionName
     * @param businessType
     * @param traceTemplateId
     * @throws Exception
     */
	public void tempalteCreateTableAndGerenteOrgFunRouteAndSaveFields(List<TraceFunFieldConfigParam> param,boolean isAdd,String nodeFunctionId, String nodeFunctionName, String businessType,String traceTemplateId) throws Exception {
		if (null!=param && !param.isEmpty()) {
			StringBuilder build=new StringBuilder();
			SimpleDateFormat format=new SimpleDateFormat("MMdd_HHmm");
			String time=format.format(new Date());
			
			if (StringUtils.isBlank(traceTemplateId) || StringUtils.isBlank(businessType)) {
				throw new SuperCodeTraceException("节点创建调用createTableAndGerenteOrgFunRouteAndSaveFields时 必须传traceTemplateId和businessTypes，traceTemplateId="+traceTemplateId+",businessType="+businessType, 500);
			}
			
			String organizationId=commonUtil.getOrganizationId();
			
			
			//决定表名，如果是自动节点则使用定制功能的表名
			String tableName=null;
			//数据库序号 默认为1如果是自动节点则使用之前的数据库记录
			int sequence=1;
			if ("1".equals(businessType)) {
				tableName=param.get(0).getEnTableName();
				TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(null, nodeFunctionId);
				if (null==traceOrgFunRoute) {
					throw new SuperCodeTraceException("溯源模板操作根据节点id"+nodeFunctionId+"查询定制功能路由关系无数据，businessType为"+businessType, 500);
				}
				sequence=Integer.valueOf(traceOrgFunRoute.getDatabaseAddress());
			}else {
				tableName="trace_dynamic_"+time+"_";
				tableName=tableName+"node_"+nodeFunctionId;
				sequence=commonUtil.getDynamicDatabaseSequence();
			}

			
			//默认添加主键即系统id和组织id为了区分不同系统和组织下的数据，创建的时候前端会根据字段顺序排列好，所以建表时字段是有序的不需要查询的时候再次排序，在修改表结构时要对顺序重新处理
			build.append("create table ").append(tableName).append(" (Id bigint primary key auto_increment,SysId varchar(50),OrganizationId varchar(50),TraceTemplateId varchar(50),DeleteStatus int(2),TraceBatchInfoId varchar(50),ProductId varchar(50),UserId varchar(50),SortDateTime bigint(100), ParentId bigint(20),  ");
			
			//封装字段表行数据，顺序必须在dynamicCreateTable之前因为sql拼装依赖这个方法
			List<TraceFunFieldConfig> tffcList=buildFunFieldConfig(param,nodeFunctionId,nodeFunctionName,tableName,2,traceTemplateId,true, build);
			
			//如果是新增节点则加入默认字段
            if (isAdd) {
            	List<TraceFunFieldConfig> extraFields=getDefaultExtraFields(tableName,nodeFunctionId, nodeFunctionName,traceTemplateId,2);
            	tffcList.addAll(extraFields);
			}
			//保存字段


			dao.batchInsert(tffcList);
			
			//只在新建溯源模板能获取组织id时才能执行,如果该功能为自动节点可能已创建了企业路由关系，则不需要再建立
			
			TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(traceTemplateId, nodeFunctionId);
			if (null==traceOrgFunRoute) {
				//决定插入哪个数据库
				//保存企业功能路由关系表
				TraceOrgFunRoute tofr=new TraceOrgFunRoute();
				tofr.setFunctionId(nodeFunctionId);
				tofr.setTableName(tableName);
				tofr.setTraceTemplateId(traceTemplateId);
				tofr.setOrganizationId(organizationId);
				tofr.setDatabaseAddress(sequence+"");
				traceOrgFunRouteDao.insert(tofr);
			}
			
			//由于create table会强制提交事务所以把动态创建表放在其它数据库操作前面这样下面会开启新事务防止当前事务被强制提交
			String create_table_sql=build.substring(0, build.length()-1)+")";
			
			//获取分库的动态mapper
			DynamicBaseMapper dynamicCreateTableDao=applicationAware.getDynamicMapperByTableNum();
			//如果是创建模板自动节点则不需要创建表
	
			switch (businessType) {
			case "1":     
				logger.info("创建自动节点时不需创建动态表");
				break;
			case "2":
				//如果创建的手动节点则动态创建表
				dynamicCreateTableDao.dynamicDDLTable(create_table_sql);
				break;
			case "3":
				//如果是默认节点则不仅要创建表还要插入默认值
				StringBuilder insertFieldNames=new StringBuilder();
				StringBuilder insertFieldValues=new StringBuilder();
				//检查默认节点属性默认值是否为空
				checkNullDefaultValueAndBuilderSql(tffcList,insertFieldNames,insertFieldValues);
				//创建默认节点动态表
				dynamicCreateTableDao.dynamicDDLTable(create_table_sql);
				insertFieldNames.append(" SortDateTime,");
				insertFieldValues.append(System.currentTimeMillis()).append(",");
				//插入默认节点表数据
				insertFieldNames.append(" OrganizationId,").append("TraceTemplateId").append(")");
				insertFieldValues.append("'").append(commonUtil.getOrganizationId()).append("'").append(",").append("'").append(traceTemplateId).append("'").append(")");
				
				StringBuilder insertValueSql=new StringBuilder();
				insertValueSql.append("insert into ").append(tableName).append("(").append(insertFieldNames.toString()).append(" values ( ").
				append(insertFieldValues.toString());
				
				dynamicCreateTableDao.insert(insertValueSql.toString());
				break;
			default:
				break;
			}
			
			try {
				//设置redis中库的表数量
				commonUtil.setDynamicDatabaseNum(sequence, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * @see根据字段动态生成表，新增定制功能和新增溯源模板创建节点共同使用方法
     *          插入字段数据 并如果是创建模板节点则生成企业功能路由关系
     * @param param
     * @param organizationId
     * @param nodeFunctionId 生成节点时生成对应的功能id，如果TraceFunFieldConfigParam中functionId为空表示是新增手动节点此时需使用nodeFunctionId生成企业功能路由关系
     * @param nodeFunctionName 
     * @param businessType :节点类型 1自动节点 2手动节点 3默认节点，自动节点不需要创建表
     * @param traceTemplateId :溯源模板id--在新增溯源模板自动节点时需要传该字段
     * @param isAdd 用于buildFunFieldConfig方法
     * @throws SuperCodeTraceException 
	 * @throws SuperCodeException 
     */
	public void createTableAndGerenteOrgFunRouteAndSaveFields(List<TraceFunFieldConfigParam> param,boolean isAdd,String functionId, String functionName) throws Exception {
		if (null!=param && !param.isEmpty()) {
			StringBuilder build=new StringBuilder();
			SimpleDateFormat format=new SimpleDateFormat("MMdd_HHmm");
			String time=format.format(new Date());
			String tableName="trace_dynamic_"+time+"_"+"fun_"+param.get(0).getFunctionId();
			
			//默认添加主键即系统id和组织id为了区分不同系统和组织下的数据，创建的时候前端会根据字段顺序排列好，所以建表时字段是有序的不需要查询的时候再次排序，在修改表结构时要对顺序重新处理
			build.append("create table ").append(tableName).append(" (Id bigint primary key auto_increment,SysId varchar(50),OrganizationId varchar(50),TraceTemplateId varchar(50),DeleteStatus int(2),TraceBatchInfoId varchar(50),ProductId varchar(50),UserId varchar(50),SortDateTime bigint(100), ParentId bigint(20), ");
			
			//封装字段表行数据，顺序必须在dynamicCreateTable之前因为sql拼装依赖这个方法
			List<TraceFunFieldConfig> tffcList=buildFunFieldConfig(param,functionId,functionName,tableName,1,null,true, build);
			
			//如果是新增节点则加入默认字段
            if (isAdd) {
            	List<TraceFunFieldConfig> extraFields=getDefaultExtraFields(tableName,functionId, functionName,null,1);
            	tffcList.addAll(extraFields);
			}
			
			//保存字段
			dao.batchInsert(tffcList);
			
			int sequence=commonUtil.getDynamicDatabaseSequence();
			//只在新建溯源模板能获取组织id时才能执行,如果该功能为自动节点可能已创建了企业路由关系，则不需要再建立

			List<TraceOrgFunRoute> orgList=traceOrgFunRouteDao.selectByFunctionIdWithTempIdIsNotNull(functionId);
			if (null==orgList || orgList.isEmpty()) {
				//如果是创建定制功能则默认保存一条记录，保存功能id和数据库的对应关系
				TraceOrgFunRoute tofr=new TraceOrgFunRoute();
				tofr.setFunctionId(tffcList.get(0).getFunctionId());
				tofr.setTableName(tableName);
				tofr.setDatabaseAddress(sequence+"");
				traceOrgFunRouteDao.insert(tofr);
			}
			
			//由于create table会强制提交事务所以把动态创建表放在其它数据库操作前面这样下面会开启新事务防止当前事务被强制提交
			String create_table_sql=build.substring(0, build.length()-1)+")";
			
			//获取分库的动态mapper
			DynamicBaseMapper dynamicCreateTableDao=applicationAware.getDynamicMapperByTableNum();

			//如果是定制功能创建则直接调用dynamicDDLTable方法
			dynamicCreateTableDao.dynamicDDLTable(create_table_sql);
			try {
				//设置redis中库的表数量
				commonUtil.setDynamicDatabaseNum(sequence, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取预定义的物料组件字段
	 * @return
	 */
	private List<TraceFunFieldConfigParam> getMaterielFieldConfigs()
	{
		String funId="Materiel_Compent_Field";
		List<TraceFunFieldConfig> funFieldConfigs= dao.selectDZFPartFieldsByFunctionId(funId);
		List<TraceFunFieldConfigParam> fieldConfigParams=funFieldConfigs.stream().map(e->new TraceFunFieldConfigParam(e.getFieldCode(),e.getFieldName(),
				e.getFieldType(),e.getFieldWeight(),e.getIsRequired(),e.getMaxSize(),e.getMinSize(),e.getTypeClass())).collect(Collectors.toList());
		return  fieldConfigParams;
	}

	public void saveFunComponent(FunComponent funComponent,String funId) throws Exception
	{
		String traceFunComponentId= commonUtil.getUUID();

		if(ComponentTypeEnum.isNestComponent(funComponent.getComponentType())){
			if(ComponentTypeEnum.MaterielCompent.getKey() == Integer.valueOf(funComponent.getComponentType())){
				// 物料组件
				List<TraceFunFieldConfigParam> fieldConfigParams= getMaterielFieldConfigs();
				funComponent.setTraceFunFieldConfigModel(fieldConfigParams);
			}
			List<TraceFunFieldConfigParam> traceFunFieldConfigParams= funComponent.getTraceFunFieldConfigModel();
			if (traceFunFieldConfigParams!=null || traceFunFieldConfigParams.size()>0){
				for(TraceFunFieldConfigParam fieldConfigParam:traceFunFieldConfigParams){
					fieldConfigParam.setFunctionId(traceFunComponentId);
					fieldConfigParam.setFunctionName(funComponent.getComponentName());
				}
				createTableAndGerenteOrgFunRouteAndSaveFields(traceFunFieldConfigParams,true,traceFunComponentId,funComponent.getComponentName());
			}
		}else {
			List<TraceFunFieldConfigParam> traceFunFieldConfigParams= funComponent.getTraceFunFieldConfigModel();
			if (traceFunFieldConfigParams!=null || traceFunFieldConfigParams.size()>0){
				for(TraceFunFieldConfigParam fieldConfigParam:traceFunFieldConfigParams){
					fieldConfigParam.setComponentId(traceFunComponentId);
				}
				//createTableAndGerenteOrgFunRouteAndSaveFields(traceFunFieldConfigParams,true,traceFunComponentId,funComponent.getComponentName());
			}
		}

		TraceFunComponent traceFunComponent=new TraceFunComponent();
		traceFunComponent.setComponentId(traceFunComponentId);
		traceFunComponent.setComponentName(funComponent.getComponentName());
		traceFunComponent.setComponentType(funComponent.getComponentType());
		traceFunComponent.setFunId(funId);
		traceFunComponent.setFieldWeight(funComponent.getFieldWeight());
		traceFunComponentMapper.insertTraceFunComponent(traceFunComponent);
	}

	public void saveFunRegulation(CustomizeFun customizeFun){
		String funId=customizeFun.getFunId();
		TraceFunRegulation traceFunRegulation=new TraceFunRegulation();
		traceFunRegulation.setFunId(funId);
		traceFunRegulation.setObjectAssociatedType(customizeFun.getObjectAssociatedType());
		traceFunRegulation.setRegulationType(customizeFun.getRegulationType());
		traceFunRegulation.setMultipleInput(customizeFun.isMultipleInput());
		traceFunRegulation.setUseSceneType(customizeFun.getUseSceneType());
		traceFunRegulation.setBatchNamingLinkCharacter(customizeFun.getBatchNamedLinkCharacter());
		traceFunRegulation.setBatchTimeControl(customizeFun.getBatchTimeControl());
		traceFunRegulation.setCreateBatchType(customizeFun.getCreateBatchType());
		traceFunRegulation.setSplittingRule(customizeFun.getSplittingRule());
		traceFunRegulation.setLayoutType(customizeFun.getLayoutType());
		traceFunRegulation.setFunctionName(customizeFun.getFunName());
		traceFunRegulationMapper.insertTraceFunRegulation(traceFunRegulation);

		List<BatchNamedRuleField> batchNamedRuleFields= customizeFun.getBatchNamedRuleFieldModels();
		if (batchNamedRuleFields!=null && batchNamedRuleFields.size()>0){
			List<TraceBatchNamed> traceBatchNameds= batchNamedRuleFields.stream().map(e->new TraceBatchNamed(e.getFieldName(),e.getFieldCode(),funId,e.getFieldFormat(),e.isDisableFlag())).collect(Collectors.toList());
			traceBatchNamedService.insertTraceBatchNamed(traceBatchNameds);
		}
	}

	/**
	 * 创建定制功能使用规则、功能组件、字段
	 * @param customizeFun
	 * @throws Exception
	 */
	public void saveFunComponentAndRegulation(CustomizeFun customizeFun) throws Exception
	{
		String funId=customizeFun.getFunId();
		List<FunComponent> funComponentModels=customizeFun.getFunComponentModels();
		if (funComponentModels!=null && funComponentModels.size()>0){
			for (FunComponent funComponent:funComponentModels){
				saveFunComponent(funComponent,funId);
			}
		}

		saveFunRegulation(customizeFun);
	}

	public RestResult<String> updateFields(List<TraceFunFieldConfigParam> param, String tableName, boolean isAdd,
										   boolean formTemplate, String nodeFunctionId, String nodeFunctionName, String traceTemplateId,
										   String businessType) throws SuperCodeTraceException {
		List<TraceFunFieldConfig> fields=new ArrayList<TraceFunFieldConfig>();
		RestResult<String> restResult=new RestResult<String>();
		for (TraceFunFieldConfigParam traceFunFieldConfigParam : param) {
			if (null==traceFunFieldConfigParam.getId()) {
				throw new SuperCodeTraceException("主键id不能为空", 500);
			}
			TraceFunFieldConfig field=new TraceFunFieldConfig();

			field.setDefaultValue(traceFunFieldConfigParam.getDefaultValue());
			field.setIsRequired(traceFunFieldConfigParam.getIsRequired());
			field.setShowHidden(traceFunFieldConfigParam.getShowHidden());

			field.setId(traceFunFieldConfigParam.getId());

			dao.updateField(field);

			fields.add(field);
		}
		if (null==fields || fields.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("参数不能为空");
		}else {
			//dao.UpdateFields(fields);
			restResult.setState(200);
			restResult.setMsg("操作成功");
		}
		return restResult;
	}
	
	/**
	 * 字段更新时抽象方法
	 * @param param
	 * @param tableName
	 * @param isAdd
	 * @param formTemplate
	 * @param nodeFunctionId
	 * @param nodeFunctionName
	 * @param traceTemplateId
	 * @param businessType
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public RestResult<String> addNewFields(List<TraceFunFieldConfigParam> param, String tableName, boolean isAdd,
			boolean formTemplate, String nodeFunctionId, String nodeFunctionName, String traceTemplateId,
			String businessType) throws SuperCodeTraceException {
		RestResult<String> restResult = new RestResult<String>();
		
		if (StringUtils.isBlank(tableName)) {
			restResult.setState(500);
			restResult.setMsg("更新功能或节点字段对应的动态表名必传");
			return restResult;
		}
		StringBuilder build = new StringBuilder();
		build.append("ALTER TABLE  ").append(tableName);
		
		int typeClass=1;
		if (formTemplate) {
			typeClass=2;
		}
		
		List<TraceFunFieldConfig> tffcList =buildFunFieldConfig(param, nodeFunctionId, nodeFunctionName,
				tableName, typeClass, traceTemplateId,false, build);
		if (null != tffcList && !tffcList.isEmpty()) {
			String sql = build.substring(0, build.length() - 1);
            //动态修改表结构新增字段，放在最前面如果失败则不进行字段保存操作
			DynamicBaseMapper dynamicCreateTableDao = applicationAware.getDynamicMapperByFunctionId(traceTemplateId,nodeFunctionId);
			
			//修改的时候如果修改的是模板的自动节点则不能去定制功能表里再加字段，因为自动节点能选的字段都在定制功能表里
			if (!(formTemplate && "1".equals(businessType))) {
				dynamicCreateTableDao.dynamicDDLTable(sql);
			}

            //保存新增字段
			dao.batchInsert(tffcList);
			try {
                //TODO 更新缓存中字段信息有问题--待好好考虑下对应节点字段和定制功能字段
				if (formTemplate) {
					functionFieldManageService.flush(traceTemplateId,nodeFunctionId,2);
				}else {
					functionFieldManageService.flush(null,nodeFunctionId,1);
				}
			} catch (Exception e) {
				logger.error("更新字段时刷新字段缓存数据出错，错误信息:" + e.getMessage());
			}
			restResult.setMsg("操作成功");
			restResult.setState(200);
		} else {
			restResult.setMsg("无法新增字段，无法拼装出字段数据");
			restResult.setState(500);
		}
		return restResult;
	}
	
    /**
     * 新增默认节点时，属性 默认值不能为空
     * @param tffcList
     * @param insertFieldValues 
     * @param insertFieldNames 
     * @throws SuperCodeTraceException 
     */
	private void checkNullDefaultValueAndBuilderSql(List<TraceFunFieldConfig> tffcList, StringBuilder insertFieldNames, StringBuilder insertFieldValues) throws SuperCodeTraceException {
      if (null==tffcList || tffcList.isEmpty()) {
    	  throw new SuperCodeTraceException("创建默认节点调用checkNullDefaultValue时字段集合为空 请先检查参数", 500);
	   }
      for (TraceFunFieldConfig traceFunFieldConfig : tffcList) {
    	  //默认创建的字段不需要有默认值
		if (!FunctionFieldCache.defaultCreateFields.contains(traceFunFieldConfig.getFieldCode())) {
			String value=traceFunFieldConfig.getDefaultValue();
			String fieldCode=traceFunFieldConfig.getFieldCode();
			if (!FunctionFieldCache.defaultCreateFields.contains(fieldCode) && StringUtils.isBlank(value)) {
				throw new SuperCodeTraceException("创建默认节点，字段："+fieldCode+"默认值为空", 500);
			}
			insertFieldNames.append(fieldCode).append(",");
			insertFieldValues.append("'").append(value).append("'").append(",");
		}
	  }
      
	}
	
	/**
	 * 根据传入的字段构建
	 * @param param
	 * @param tableName
	 * @param build
	 * @param formTemplate:true表示方法调用是来源于添加溯源模板，字段功能id需使用节点功能id否则使用字段自身的functionId
	 * @param nodeFunctionId
	 * @param nodeFunctionName 
	 * @param traceTemplateId 
	 * @param businessType
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public List<TraceFunFieldConfig> buildFunFieldConfig(List<TraceFunFieldConfigParam> param,String functionId, String functionName, String tableName,Integer typeClass, String traceTemplateId,boolean isadd, StringBuilder build) throws SuperCodeTraceException {
		List<TraceFunFieldConfig> tffcList=new ArrayList<TraceFunFieldConfig>();
		Collections.sort(param);
		for (TraceFunFieldConfigParam traceFunConfigParam : param) {
			String fieldCode=traceFunConfigParam.getFieldCode();
			//如果新增的时候传了组织id这些字段则不保存使用默认字段，修改的时候只传新增的字段不会出现这种情况
			if (FunctionFieldCache.defaultCreateFields.contains(fieldCode)) {
				continue;
			}
			TraceFunFieldConfig tffc=new TraceFunFieldConfig();
			String fieldType=traceFunConfigParam.getFieldType();
			String defaultValue=traceFunConfigParam.getDefaultValue();
			Integer maxSize=traceFunConfigParam.getMaxSize();
			Integer isrequired=traceFunConfigParam.getIsRequired();
			String fieldName=traceFunConfigParam.getFieldName();
			if (fieldName.length()>10 || fieldCode.length()>30) {
				throw new SuperCodeTraceException("字段名："+fieldName+" 长度大于10，且结构化名："+fieldCode+"长度不能超过30", 500);
			}
			String objectType=traceFunConfigParam.getObjectType();
			//如果是修改增加字段则append “add column”
			if (!isadd) {
				build.append(" ADD COLUMN ");
			}
			build.append(fieldCode).append(" ");
			if (fieldType.startsWith("13")) {
				if (StringUtils.isBlank(objectType)) {
					throw new SuperCodeTraceException("对象类型字段-"+fieldCode+"必须传objectType", 500);
				}
				try {
					String type=fieldType.split("_")[1];
					build.append(type);
				} catch (Exception e) {
					build.append("varchar(100)");
				}
				tffc.setFieldType(fieldType);
				
			}else {
				tffc.setFieldType(fieldType);
				switch (fieldType) {
				case "5":        //表示金额使用默认的整数长度和小数点长度
					build.append("decimal(18,6)");
					break;
				case "6":        //表示日期
					build.append("date");
					break;
				case "7":        //表示时间
					build.append("time");
					break;
				case "8":        //表示日期和时间
					build.append("datetime");
					break;
				default:
				    if (null==maxSize || maxSize>1000) {
						throw new SuperCodeTraceException("字段-"+fieldCode+"长度不能为空且最大长度不能超过"+defaultValueMaxSise, 500);
					}
					build.append("varchar(").append(maxSize).append(")");
					break;
				}
			}
			
			
			if (null==isrequired) {
				isrequired=0;
			}
			//已和产品沟通过，非空必填参数由前端控制，后台不控制
//			if (1==isrequired) {
//				build.append(" not null ");
//			}
			
			if (StringUtils.isNotBlank(defaultValue)) {
				build.append(" default '").append(defaultValue).append("'");
			}
			build.append(",");
			
			tffc.setObjectFieldId(traceFunConfigParam.getObjectFieldId());
			tffc.setFieldCode(fieldCode);
			tffc.setDataValue(traceFunConfigParam.getDataValue());
			tffc.setDefaultValue(defaultValue);
			tffc.setTraceTemplateId(traceTemplateId);//如果是新建自动节点的话保存模板id
			//来源于模板创建节点且该节点不是自动节点时设置功能id和功能名称为节点的功能id和功能名称
			

			tffc.setEnTableName(tableName);
			tffc.setFunctionId(functionId);
			tffc.setFunctionName(functionName);
			tffc.setIsRemarkEnable(traceFunConfigParam.getIsRemarkEnable());
			tffc.setIsRequired(traceFunConfigParam.getIsRequired());
			tffc.setMaxNumber(traceFunConfigParam.getMaxNumber());
			tffc.setMaxSize(maxSize);
			tffc.setMinNumber(traceFunConfigParam.getMinNumber());
			tffc.setMinSize(traceFunConfigParam.getMinSize());
			tffc.setRequiredNumber(traceFunConfigParam.getRequiredNumber());
			tffc.setShowHidden(null==traceFunConfigParam.getShowHidden()?1:traceFunConfigParam.getShowHidden());
			tffc.setValidateFormat(traceFunConfigParam.getValidateFormat());
			tffc.setFieldName(fieldName);
			tffc.setTypeClass(typeClass);
			tffc.setObjectType(objectType);
			tffc.setFieldWeight(traceFunConfigParam.getFieldWeight());
			tffc.setComponentId(traceFunConfigParam.getComponentId());
			tffcList.add(tffc);

		}

		return tffcList;
	}
	/**
	 * 获取默认的组织id系统id，模板id的字段信息插入表字段配置
	 * @param fields
	 * @param enTableName
	 * @param functionId
	 * @param functionName
	 * @param traceTemplateId 
	 * @param typeClass 
	 * @return
	 */
	public List<TraceFunFieldConfig> getDefaultExtraFields(String enTableName,String functionId,String functionName, String traceTemplateId, Integer typeClass) {
		List<TraceFunFieldConfig> tffcList=new ArrayList<TraceFunFieldConfig>();
		DefaultFieldEnum[] fieldEnums=DefaultFieldEnum.values();
		for (DefaultFieldEnum defaultFieldEnum : fieldEnums) {
			TraceFunFieldConfig tffc_id=new TraceFunFieldConfig();
			tffc_id.setFieldName(defaultFieldEnum.getFieldName());
			tffc_id.setFieldCode(defaultFieldEnum.getFieldCode());
			tffc_id.setCreateBy(null);
			tffc_id.setFieldType("1");
			tffc_id.setEnTableName(enTableName);
			tffc_id.setTraceTemplateId(traceTemplateId);
			tffc_id.setShowHidden(0);
			tffc_id.setExtraCreate(1);
			tffc_id.setFunctionId(functionId);
			tffc_id.setFunctionName(functionName);
			tffc_id.setIsRequired(0);
			tffc_id.setMaxSize(50);
			tffc_id.setTypeClass(typeClass);
			tffcList.add(tffc_id);
		}
		return tffcList;
	}
	
	
	/**
	 * 校验新增字段参数非空
	 * @param param
	 * @return 
	 * @throws SuperCodeTraceException
	 */
	public static boolean checkAddParam(List<TraceFunFieldConfigParam> param) throws SuperCodeTraceException {
		
	  if (null==param || param.isEmpty()) {
		  throw new SuperCodeTraceException("组件外层至少添加一个字段", 500);
	  }	
	  Map<String, Integer> fieldMap=new HashMap<String, Integer>();
	  boolean containBtach=false;
	  boolean containProduct=false;
	  
      for (TraceFunFieldConfigParam traceFunFieldConfigParam : param) {
    	  String fieldCode=traceFunFieldConfigParam.getFieldCode();
    	  String fieldName=traceFunFieldConfigParam.getFieldName();
    	  String fieldType=traceFunFieldConfigParam.getFieldType();
    	  Integer maxSize=traceFunFieldConfigParam.getMaxSize();
    	  if (StringUtils.isBlank(fieldCode) || StringUtils.isBlank(fieldName) || StringUtils.isBlank(fieldType)) {
			throw new SuperCodeTraceException("请注意fieldCode,fieldName,fieldType字段必填", 500);
	      }
    	  Integer flag=fieldMap.get(fieldCode);
    	  if (null!=flag) {
    		  throw new SuperCodeTraceException("存在重复的结构化名称-"+fieldCode, 500);
		  }
    	  
		  //这里先检查下长度是否超过最大值，因为时间类型和金额等没有最大值所以需要加null!=maxSize
		  if (null!=maxSize && maxSize>1000) {
			throw new SuperCodeTraceException("字段-"+fieldCode+"长度不能为空且最大长度不能超过"+defaultValueMaxSise, 500);
		  }
    	  fieldMap.put(fieldCode, 1);
    	  String objectType=traceFunFieldConfigParam.getObjectType();
    	  if (fieldType.startsWith("13")) {
        	  if (StringUtils.isBlank(objectType)) {
      			throw new SuperCodeTraceException("对象类型 objectType不能为空", 500);
      	      }
        	  int traceBatchCode=ObjectTypeEnum.TRACE_BATCH.getCode();
        	  int productCode=ObjectTypeEnum.PRODUCT.getCode();
        	  if (Integer.valueOf(objectType).equals(traceBatchCode)) {
        		  containBtach=true;
			  }
        	  if (Integer.valueOf(objectType).equals(productCode)) {
        		  containProduct=true;
			  }
		  }
    	  String defaultValue=traceFunFieldConfigParam.getDefaultValue();
    	  if (StringUtils.isNotBlank(defaultValue)) {
			if (defaultValueMaxSise<defaultValue.length()) {
				throw new SuperCodeTraceException("默认值长度不能超过"+defaultValueMaxSise+"，请检查字段："+fieldCode+"默认值长度", 500);
			}
		  }
	  }		
      return containBtach && containProduct;
	}
	
	/**
	 *  校验定制功能编辑参数
	 * @param param
	 * @throws SuperCodeTraceException
	 */
	public  boolean checkupdateFunParam(List<TraceFunFieldConfigParam> param,List<TraceFunFieldConfigParam> addConfigLlist) throws SuperCodeTraceException {
	  Map<String, Integer> dobleFieldCheckMap=new HashMap<String, Integer>();
	  Map<String, Integer> existDobleFieldCheckMap=new HashMap<String, Integer>();
	  String functionId=param.get(0).getFunctionId();
 	  if (StringUtils.isBlank(functionId)) {
		  throw new SuperCodeTraceException("请注意functionId必填", 500);
	  }
	  List<TraceFunFieldConfig> configList=dao.selectDZFPartFieldsByFunctionId(functionId);
	  if (null==configList || configList.isEmpty()) {
		  throw new SuperCodeTraceException("该定制功能不存在，请先新增再修改", 500);
	  }
	  
	  //转map
	  Map<Long, TraceFunFieldConfig> configfieldMap=new HashMap<Long, TraceFunFieldConfig>();
	  for (TraceFunFieldConfig traceFunFieldConfig : configList) {
		  configfieldMap.put(traceFunFieldConfig.getId(), traceFunFieldConfig);
		  existDobleFieldCheckMap.put(traceFunFieldConfig.getFieldCode(), 1);
	  }
	  
	  boolean isJustAddField=true;
      for (TraceFunFieldConfigParam traceFunFieldConfigParam : param) {
    	  Long param_id=traceFunFieldConfigParam.getId();
    	  String param_fieldCode=traceFunFieldConfigParam.getFieldCode();
    	  String param_fieldName=traceFunFieldConfigParam.getFieldName();
    	  String param_fieldType=traceFunFieldConfigParam.getFieldType();
    	  Integer param_maxSize=traceFunFieldConfigParam.getMaxSize();
    	  functionId=traceFunFieldConfigParam.getFunctionId();
    	  String functionName=traceFunFieldConfigParam.getFunctionName();
     	  if (StringUtils.isBlank(functionId) || StringUtils.isBlank(functionName)) {
    		  throw new SuperCodeTraceException("请注意functionId,functionName必填", 500);
    	  }
    	  
    	  if (StringUtils.isBlank(param_fieldCode) || StringUtils.isBlank(param_fieldName) || StringUtils.isBlank(param_fieldType)) {
    		  throw new SuperCodeTraceException("请注意fieldCode,fieldName,fieldType字段必填", 500);
    	  }
    	  Integer flag=dobleFieldCheckMap.get(param_fieldCode);
    	  if (null!=flag) {
    		  throw new SuperCodeTraceException("存在重复的fieldCode-"+param_fieldCode, 500);
    	  }
    	  dobleFieldCheckMap.put(param_fieldCode, 1);
    	  String objectType=traceFunFieldConfigParam.getObjectType();
    	  if (param_fieldType.startsWith("13")) {
        	  if (StringUtils.isBlank(objectType)) {
      			throw new SuperCodeTraceException("对象类型 objectType不能为空", 500);
      	      }
		  }
    	  if (null==param_id) {
    		  boolean exist=existDobleFieldCheckMap.containsKey(param_fieldCode);
    		  if (exist) {
    			  throw new SuperCodeTraceException("存在重复的fieldCode-"+param_fieldCode, 500);
			  }
    		  addConfigLlist.add(traceFunFieldConfigParam);
		  }else {
			  TraceFunFieldConfig existConfig=configfieldMap.get(param_id);
			  String fieldCode=existConfig.getFieldCode();
	    	  String fieldName=existConfig.getFieldName();
	    	  String fieldType=existConfig.getFieldType();
	    	  Integer maxSize=existConfig.getMaxSize();
			  if (!param_fieldCode.equals(fieldCode)) {
				List<TraceOrgFunRoute> traceOrgFunRouteList=traceOrgFunRouteDao.selectByFunctionIdWithTempIdIsNotNull(functionId);
		        if (null!=traceOrgFunRouteList && !traceOrgFunRouteList.isEmpty()) {
		        	throw new SuperCodeTraceException("该定制功能已下发组织，修改只能新增字段", 500);
				}
		        isJustAddField=false;
			  }else if (!param_fieldName.equals(fieldName)){
				List<TraceOrgFunRoute> traceOrgFunRouteList=traceOrgFunRouteDao.selectByFunctionIdWithTempIdIsNotNull(functionId);
		        if (null!=traceOrgFunRouteList && !traceOrgFunRouteList.isEmpty()) {
		        	throw new SuperCodeTraceException("该定制功能已下发组织，修改只能新增字段", 500);
				}
		        isJustAddField=false;
			  }else if (!fieldType.startsWith("13") && !param_fieldType.equals(fieldType)){
				List<TraceOrgFunRoute> traceOrgFunRouteList=traceOrgFunRouteDao.selectByFunctionIdWithTempIdIsNotNull(functionId);
		        if (null!=traceOrgFunRouteList && !traceOrgFunRouteList.isEmpty()) {
		        	throw new SuperCodeTraceException("该定制功能已下发组织，修改只能新增字段", 500);
				}
		        isJustAddField=false;
			  }else {
				  if (null!=param_maxSize && null!=maxSize) {
					if (param_maxSize!=maxSize) {
						List<TraceOrgFunRoute> traceOrgFunRouteList=traceOrgFunRouteDao.selectByFunctionIdWithTempIdIsNotNull(functionId);
				        if (null!=traceOrgFunRouteList && !traceOrgFunRouteList.isEmpty()) {
				        	throw new SuperCodeTraceException("该定制功能已下发组织，修改只能新增字段", 500);
						}
						isJustAddField=false;
					}
				  }
			  }
			  
		  }
    	  
	  }
	 return isJustAddField;		
      
	}
}
