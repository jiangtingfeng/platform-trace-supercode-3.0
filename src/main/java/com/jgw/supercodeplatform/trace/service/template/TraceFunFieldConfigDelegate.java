package com.jgw.supercodeplatform.trace.service.template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;
import com.jgw.supercodeplatform.trace.service.dynamic.FunctionFieldCacheService;
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
	private FunctionFieldCacheService functionFieldManageService;
	
	private static  List<String> defaultCreateFields=new ArrayList<>(Arrays.asList(new String[]{"OrganizationId","TraceTemplateId","TraceBatchInfoId","SysId","Id"}));
	/**
     * @see根据字段动态生成表，新增定制功能和新增溯源模板创建节点共同使用方法
     *          插入字段数据 并如果是创建模板节点则生成企业功能路由关系
     * @param param
     * @param organizationId
     * @param nodeFunctionId 生成节点时生成对应的功能id，如果TraceFunFieldConfigParam中functionId为空表示是新增手动节点此时需使用nodeFunctionId生成企业功能路由关系
     * @param nodeFunctionName 
     * @param businessTypes :节点类型 1自动节点 2手动节点 3默认节点，自动节点不需要创建表
     * @param traceTemplateId :溯源模板id--在新增溯源模板自动节点时需要传该字段
     * @param isAdd 用于buildFunFieldConfig方法
     * @throws SuperCodeTraceException 
	 * @throws SuperCodeException 
     */
	public void createTableAndGerenteOrgFunRouteAndSaveFields(List<TraceFunFieldConfigParam> param,
			String organizationId,boolean isAdd,boolean formTemplate,String nodeFunctionId, String nodeFunctionName, String businessTypes,String traceTemplateId) throws Exception {
		if (null!=param && !param.isEmpty()) {
			StringBuilder build=new StringBuilder();
			SimpleDateFormat format=new SimpleDateFormat("MMdd_HHmm");
			String time=format.format(new Date());
			String tableName="trace_dynamic_"+time+"_";
			if (formTemplate) {
				if (StringUtils.isBlank(traceTemplateId) || StringUtils.isBlank(businessTypes)) {
					throw new SuperCodeTraceException("节点创建调用createTableAndGerenteOrgFunRouteAndSaveFields时 必须传traceTemplateId和businessTypes，traceTemplateId="+traceTemplateId+",businessTypes="+businessTypes, 500);
				}
				tableName=tableName+"node_"+nodeFunctionId;
			}else {
				tableName=tableName+"fun_"+param.get(0).getFunctionId();
			}
			
			//默认添加主键即系统id和组织id为了区分不同系统和组织下的数据，创建的时候前端会根据字段顺序排列好，所以建表时字段是有序的不需要查询的时候再次排序，在修改表结构时要对顺序重新处理
			build.append("create table ").append(tableName).append(" (Id bigint primary key auto_increment,SysId varchar(50),OrganizationId varchar(50),TraceTemplateId varchar(50),DeleteStatus int(2),TraceBatchInfoId varchar(50),");
			
			//封装字段表行数据，顺序必须在dynamicCreateTable之前因为sql拼装依赖这个方法
			List<TraceFunFieldConfig> tffcList=buildFunFieldConfig(param,tableName, build,isAdd,formTemplate,nodeFunctionId,nodeFunctionName,traceTemplateId,businessTypes);

			//保存字段
			dao.batchInsert(tffcList);
			int sequence=commonUtil.getDynamicDatabaseSequence();
			//只在新建溯源模板能获取组织id时才能执行,如果该功能为自动节点可能已创建了企业路由关系，则不需要再建立
			if (formTemplate) {
				TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(traceTemplateId, tffcList.get(0).getFunctionId());
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
			}else {
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
			//如果是创建模板自动节点则不需要创建表
			if (formTemplate) {
				switch (businessTypes) {
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
			}else {
				//如果是定制功能创建则直接调用dynamicDDLTable方法
				dynamicCreateTableDao.dynamicDDLTable(create_table_sql);
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
	 * 字段更新时抽象方法
	 * @param param
	 * @param tableName
	 * @param isAdd
	 * @param formTemplate
	 * @param nodeFunctionId
	 * @param nodeFunctionName
	 * @param traceTemplateId
	 * @param businessTypes
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public RestResult<String> updateAbstract(List<TraceFunFieldConfigParam> param, String tableName, boolean isAdd,
			boolean formTemplate, String nodeFunctionId, String nodeFunctionName, String traceTemplateId,
			String businessType) throws SuperCodeTraceException {
		RestResult<String> restResult = new RestResult<String>();
		
		if (StringUtils.isBlank(tableName)) {
			restResult.setState(500);
			restResult.setMsg("更新功能或节点字段对应的动态表名必传");
			return restResult;
		}
		StringBuilder build = new StringBuilder();
		build.append("ALTER TABLE  ").append(tableName).append(" ADD ");

		List<TraceFunFieldConfig> tffcList =buildFunFieldConfig(param, tableName, build,
				isAdd, formTemplate, nodeFunctionId, nodeFunctionName, traceTemplateId, businessType);
		if (null != tffcList && !tffcList.isEmpty()) {
			String sql = build.substring(0, build.length() - 1);
            //动态修改表结构新增字段，放在最前面如果失败则不进行字段保存操作
			DynamicBaseMapper dynamicCreateTableDao = applicationAware.getDynamicMapperByFunctionId(traceTemplateId,
					param.get(0).getFunctionId());
			
			//修改的时候如果修改的是模板的自动节点则不能去定制功能表里再加字段，因为自动节点能选的字段都在定制功能表里
			if (!(formTemplate && "1".equals(businessType))) {
				dynamicCreateTableDao.dynamicDDLTable(sql);
			}

            //保存新增字段
			dao.batchInsert(tffcList);
			try {
                //TODO 更新缓存中字段信息有问题--待好好考虑下对应节点字段和定制功能字段
				TraceFunTemplateconfigQueryParam queryParam = new TraceFunTemplateconfigQueryParam();
				if (formTemplate) {
					queryParam.setFunctionId(nodeFunctionId);
					queryParam.setTraceTemplateId(traceTemplateId);
					if (StringUtils.isBlank(businessType)) {
						queryParam.setBusinessType(1);
					}else {
						queryParam.setBusinessType(Integer.valueOf(businessType));
					}
					
				}else {
					queryParam.setFunctionId(tffcList.get(0).getFunctionId());
					queryParam.setTypeClass(1);
				}
				queryParam.setTypeClass(1);
				functionFieldManageService.flush(queryParam);
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
		if (!defaultCreateFields.contains(traceFunFieldConfig.getFieldCode())) {
			String value=traceFunFieldConfig.getDefaultValue();
			String fieldCode=traceFunFieldConfig.getFieldCode();
			if (StringUtils.isBlank(value)) {
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
	 * @param businessTypes 
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public List<TraceFunFieldConfig> buildFunFieldConfig(List<TraceFunFieldConfigParam> param, String tableName, StringBuilder build,boolean isAdd,boolean formTemplate,String nodeFunctionId, String nodeFunctionName, String traceTemplateId, String businessTypes) throws SuperCodeTraceException {
		List<TraceFunFieldConfig> tffcList=new ArrayList<TraceFunFieldConfig>();
		boolean getDefaultFields=false;
		Collections.sort(param);
		for (TraceFunFieldConfigParam traceFunConfigParam : param) {
			String fieldCode=traceFunConfigParam.getFieldCode();
			//如果新增的时候传了组织id这些字段则不保存使用默认字段，修改的时候只传新增的字段不会出现这种情况
			if (defaultCreateFields.contains(fieldCode)) {
				continue;
			}
			TraceFunFieldConfig tffc=new TraceFunFieldConfig();
			Integer typeClass=traceFunConfigParam.getTypeClass();
			String functionId=traceFunConfigParam.getFunctionId();
			String functionName=traceFunConfigParam.getFunctionName();
			String enTableName=tableName;
			if (!isAdd) {
				enTableName=traceFunConfigParam.getEnTableName();
			}
			if (formTemplate) {
				//来源于模板创建则为2
				typeClass=2;
				//如果创建模板的节点是自动节点则functionid使用默认定制功能节点id，entableName使用定制功能节点表名
				if ("1".equals(businessTypes)) {
					enTableName=traceFunConfigParam.getEnTableName();
					if (StringUtils.isBlank(enTableName)) {
						throw new SuperCodeTraceException("自动节点中的enTableName不能为空" ,500);
					}
				}else {
					//如果不是自动节点而且是创建的时候则使用节点功能id，更新的时候使用字段里的
					if (isAdd) {
						functionId=nodeFunctionId;
					}
				}
				
				//模板创建节点新增时使用节点名称
				if (isAdd) {
					functionName=nodeFunctionName;
				}
			}else {
				//来源于定制功能创建则为1
				typeClass=1;
			}
			if (StringUtils.isBlank(functionId) || StringUtils.isBlank(functionName) ) {
				throw new SuperCodeTraceException((isAdd?"新增字段":"更新字段")+"操作功能id和功能名称放到字段属中，来源于模板创建="+formTemplate, 500);
			}
			String fieldType=traceFunConfigParam.getFieldType();
			String defaultValue=traceFunConfigParam.getDefaultValue();
			Integer maxSize=traceFunConfigParam.getMaxSize();
			Integer isrequired=traceFunConfigParam.getIsRequired();
			String fieldName=traceFunConfigParam.getFieldName();
			if (fieldName.length()>10 || fieldCode.length()>30) {
				throw new SuperCodeTraceException("字段名："+fieldName+" 长度大于10，且结构化名："+fieldCode+"长度不能超过30", 500);
			}
			String objectType=traceFunConfigParam.getObjectType();
			build.append(fieldCode).append(" ");
			//TODO 对象类型做特殊处理
			if (fieldType.startsWith("13_")) {
				if (StringUtils.isBlank(objectType)) {
					throw new SuperCodeTraceException("对象类型字段-"+fieldCode+"必须传objectType", 500);
				}
				String type=fieldType.split("_")[1];
				build.append(type);
				tffc.setFieldType("13");
				
				
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
					Integer minSize=traceFunConfigParam.getMinSize();
					if (null!=maxSize && maxSize!=0) {
						build.append("varchar(").append(maxSize).append(")");
						break;
					}else if(null!=minSize && minSize!=0) {
						build.append("varchar(").append(minSize).append(")");
						break;
					}else {
						throw new SuperCodeTraceException("字段："+fieldCode+"为默认字符类型，需要设置最大长度或最小长度", 500);
					}

				}
			}
			if (null==isrequired) {
				isrequired=1;
			}
			//非空必填参数
			if (1==isrequired) {
				build.append(" not null ");
			}
			
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
			

			tffc.setEnTableName(enTableName);
			
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
			tffcList.add(tffc);
			if (isAdd && !getDefaultFields) {
				List<TraceFunFieldConfig> extraFields=getDefaultExtraFields(enTableName,functionId, functionName,traceTemplateId,typeClass);
				tffcList.addAll(extraFields);
				getDefaultFields=true;
			}
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
		for (String fieldCode : defaultCreateFields) {
			TraceFunFieldConfig tffc_id=new TraceFunFieldConfig();
			switch (fieldCode) {
			case "OrganizationId":
				tffc_id.setFieldName("组织id");
				break;
			case "TraceTemplateId":
				tffc_id.setFieldName("模板id");
				break;
			case "SysId":
				tffc_id.setFieldName("系统id");
				break;
			case "Id":
				tffc_id.setFieldName("主键id");
				break;
			case "TraceBatchInfoId":
				tffc_id.setFieldName("批次唯一id");
				break;
			default:
				break;
			}
			tffc_id.setFieldCode(fieldCode);
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
}
