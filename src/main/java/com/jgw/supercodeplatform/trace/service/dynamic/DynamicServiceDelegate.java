package com.jgw.supercodeplatform.trace.service.dynamic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.model.NodeOrFunFields;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.AddBusinessDataModel;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicHideParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;

@Component
public class DynamicServiceDelegate {
	@Autowired
	private TraceBatchInfoService traceBatchInfoService;
	
	@Autowired
	private TraceFunFieldConfigService traceFunFieldConfigService;
	
	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	@Autowired
	private FunctionFieldCache functionFieldManageService;
	
	public RestResult<List<String>> delete(String functionId,List<Long> ids,String traceTemplateId) throws Exception {
		RestResult<List<String>> restResult =new RestResult<List<String>>();
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		if (null==ids || ids.isEmpty()) {
			 throw new SuperCodeTraceException("主键id必传", 500);
		}
		
		deleteInSqlBuilder(ids, sqlFieldValueBuilder);
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		if (StringUtils.isBlank(tableName)) {
			 throw new SuperCodeTraceException("删除节点功能数据时无法根据功能id："+functionId+"获取到表名", 500);
		}
		String delete_sql = "delete from " + tableName + sqlFieldValueBuilder.toString();
		String query_sql = "select TraceBatchInfoId  from " + tableName + sqlFieldValueBuilder.toString();
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		
		List<LinkedHashMap<String, Object>> data=dao.select(query_sql);
		if (null==data || data.isEmpty()) {
			 throw new SuperCodeTraceException("不存在此记录", 500);
		}
		String traceBatchInfoId=(String) data.get(0).get("TraceBatchInfoId");
		TraceBatchInfo traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
		if (null==traceBatchInfo) {
			 throw new SuperCodeTraceException("无法查询批次记录", 500);
		}
		
		dao.delete(delete_sql);
		traceBatchInfo.setNodeDataCount((traceBatchInfo.getNodeDataCount()==null?0:traceBatchInfo.getNodeDataCount())-ids.size());
		traceBatchInfoService.updateTraceBatchInfo(traceBatchInfo);
		
		restResult.setState(200);
		restResult.setMsg("删除成功");
		return restResult;
	}

	private void deleteInSqlBuilder(List<Long> ids,StringBuilder sqlFieldValueBuilder) throws SuperCodeTraceException {
		if (null == ids || ids.isEmpty()) {
		  throw new SuperCodeTraceException("删除数据，参数id必须传", 500);
		}
		int i = 0;
		sqlFieldValueBuilder.append(" where Id in (");
		for (Long id : ids) {
			sqlFieldValueBuilder.append(id);
			i++;
			if (i<ids.size()) {
				sqlFieldValueBuilder.append(",");
			}
		}
		sqlFieldValueBuilder.append(")");
	}
	
	
    /**
     *  新增和修改时参数校验，一定会获取模板id
     * @param param
     * @param autoNode
     * @param traceTemplateId
     * @param functionId
     * @return
     * @throws SuperCodeTraceException 
     */
	public AddBusinessDataModel addMethodSqlBuilderParamValidate(String functionId,LineBusinessData lineBusinessData,boolean isNode,String traceBatchInfoId,String traceTemplateId) throws SuperCodeTraceException {
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		AddBusinessDataModel adDataModel=new AddBusinessDataModel();
		try {
			if (StringUtils.isBlank(functionId)) {
				throw new SuperCodeTraceException("功能id不能为空", 500);
			}
			if (null==lineBusinessData || null==lineBusinessData.getFields() || lineBusinessData.getFields().isEmpty()) {
				throw new SuperCodeTraceException("data不能为空", 500);
			}
			adDataModel.setFunctionId(functionId);
			adDataModel.setLineData(lineBusinessData);
			
			//如果是新增节点业务数据则必须是带批次信息的
			if (isNode) {
				if (StringUtils.isBlank(traceBatchInfoId)) {
					throw new SuperCodeTraceException("节点新增数据，traceBatchInfoId 不能为空", 500);
				}
				if (StringUtils.isBlank(traceTemplateId)) {
					throw new SuperCodeTraceException("节点新增数据，traceTemplateId 不能为空", 500);
				}
				adDataModel.setTraceBatchInfoId(traceBatchInfoId);
				adDataModel.setTraceTemplateId(traceTemplateId);
			}else {
				//如果新增的是定制功能则在数据里查找批次信息
				List<FieldBusinessParam> fields=lineBusinessData.getFields();
				for (FieldBusinessParam fieldParam : fields) {
					Integer objectType=fieldParam.getObjectType();
					if (null!=objectType) {
						ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
						switch (objectTypeEnum) {
						case TRACE_BATCH:
							//如果不是自动节点的插入修改则必须传模板id
							//如果是自动节点则新增时无模板id但必须有批次号
							adDataModel.setTraceBatchInfoId(fieldParam.getObjectUniqueValue());
							break;
						default:
							break;
						}
					}
				}
				if (StringUtils.isBlank(adDataModel.getTraceBatchInfoId())) {
					throw new SuperCodeTraceException("生产管理新增数据无法获取到批次唯一id", 500);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SuperCodeTraceException("解析data失败:" + e.getMessage(), 500);
		}
		return adDataModel;
	}
	
    /**
     *  返回值只对新增有效
     * @param lineBusinessData
     * @param operateType
     * @param sqlFieldNameBuilder
     * @param sqlFieldValueBuilder
     * @param isNode
     * @return
     * @throws SuperCodeTraceException
     * @throws SuperCodeException
     */
	public boolean funAddOrUpdateSqlBuilder(LineBusinessData lineBusinessData, int operateType,StringBuilder sqlFieldNameBuilder,StringBuilder sqlFieldValueBuilder,boolean isNode) throws SuperCodeTraceException, SuperCodeException {
    	List<FieldBusinessParam> fieldBusinessList=lineBusinessData.getFields();
    	boolean containObj=false;
    	for (FieldBusinessParam fieldBusinessParam : fieldBusinessList) {
    		switch (operateType) {
    		case 1: // 表示新增数据操作
    			boolean flag=caseAdd(sqlFieldNameBuilder, sqlFieldValueBuilder, fieldBusinessParam,isNode);
    			if (flag) {
    				containObj=true;
				}
    			break;
    		case 3: // 表示修改数据操作
    			caseUpdate(sqlFieldNameBuilder,sqlFieldValueBuilder, fieldBusinessParam);
    			break;
    		default:
    			break;
    		}
		}
    	return containObj;
	}
	/**
	 * 
	 * @param sqlFieldNameBuilder
	 * @param sqlFieldValueBuilder
	 * @param isNode 
	 * @param lineNode
	 * @param fieldCode
	 * @param                      fieldType:插入不会传动态表主键id所以fieldType不会有为空的情况
	 * @return 
	 * @throws SuperCodeTraceException 
	 */
	private boolean caseAdd(StringBuilder sqlFieldNameBuilder, StringBuilder sqlFieldValueBuilder, FieldBusinessParam fieldBusinessParam, boolean isNode) throws SuperCodeTraceException {
		String fieldCode = fieldBusinessParam.getFieldCode();
		// 剩下的类型都可以当字符处理包括时间
		String fieldValue = fieldBusinessParam.getFieldValue();
		boolean containObj=false;
		if (StringUtils.isNotBlank(fieldValue) && !"null".equalsIgnoreCase(fieldValue)) {
			sqlFieldNameBuilder.append(fieldCode).append(",");
			sqlFieldValueBuilder.append("'").append(fieldValue).append("'").append(",");
			Integer objectType=fieldBusinessParam.getObjectType();
			if (null!=objectType) {
				containObj=true;
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				switch (objectTypeEnum) {
				//新增数据设置批次id时只有定制功能才需要在字段数据里找，节点数据新增有单独的字段接收批次唯一id
				case TRACE_BATCH:
					if (!isNode) {
						sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append(",");
						sqlFieldValueBuilder.append("'").append(fieldBusinessParam.getObjectUniqueValue()).append("'").append(",");
					}
					break;
				case PRODUCT:
					sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append(",");
					sqlFieldValueBuilder.append("'").append(fieldBusinessParam.getObjectUniqueValue()).append("'").append(",");
					break;
				case USER:
					sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append(",");
					sqlFieldValueBuilder.append("'").append(fieldBusinessParam.getObjectUniqueValue()).append("'").append(",");
					break;
				default:
					break;
				}
			}
		}
		return containObj;
	}

	/**
	 * 
	 * @param sqlFieldNameBuilder
	 * @param sqlFieldValueBuilder 
	 * @param sqlFieldValueBuilder
	 * @param lineNode
	 * @param fieldCode
	 * @param                      fieldType:更新不会更新id所以fieldType不会有为空的情况
	 * @throws SuperCodeTraceException 
	 */
	private void caseUpdate(StringBuilder sqlFieldNameBuilder,StringBuilder sqlFieldValueBuilder, FieldBusinessParam fieldBusinessParam) throws SuperCodeTraceException {
		// 剩下的类型都可以当字符处理包括时间
		String fieldValue = fieldBusinessParam.getFieldValue();
		String fieldCode = fieldBusinessParam.getFieldCode();
		if (StringUtils.isNotBlank(fieldValue) && !"null".equalsIgnoreCase(fieldValue)) {
			if ("Id".equals(fieldCode)) {
				sqlFieldValueBuilder.append(" where Id=").append(fieldValue);
			}else {
				sqlFieldNameBuilder.append(fieldCode).append("=").append("'").append(fieldValue).append("'")
				.append(",");
				Integer objectType=fieldBusinessParam.getObjectType();
				if (null!=objectType) {
					ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
					switch (objectTypeEnum) {
					case TRACE_BATCH:
						String traceBatchInfoId=fieldBusinessParam.getObjectUniqueValue();
						//修改时，如果没有修改对象字段，前端不会传对象类型的ObjectUniqueValue值
						if (StringUtils.isNotBlank(traceBatchInfoId)) {
							sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append("=").append("'").append(traceBatchInfoId).append("'")
							.append(",");
						}
						break;
					case PRODUCT:
						String productId=fieldBusinessParam.getObjectUniqueValue();
						if (StringUtils.isNotBlank(productId)) {
							sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append("=").append("'").append(productId).append("'")
							.append(",");
						}
						break;
					case USER:
						String userId=fieldBusinessParam.getObjectUniqueValue();
						if (StringUtils.isNotBlank(userId)) {
							sqlFieldNameBuilder.append(objectTypeEnum.getFieldCode()).append("=").append("'").append(userId).append("'")
							.append(",");
						}
						break;
					default:
						break;
					}
				}
			}
		}
		
	}
    /**
     * 隐藏节点业务数据
     * @param param
     * @return
     * @throws SuperCodeTraceException
     */
	public RestResult<String> hide(DynamicHideParam param) throws SuperCodeTraceException {
		RestResult<String> restResult =new RestResult<String>();
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		String functionId = param.getFunctionId();
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		if (StringUtils.isBlank(tableName)) {
			 throw new SuperCodeTraceException("删除节点功能数据时无法根据功能id："+functionId+"获取到表名", 500);
		}
		deleteInSqlBuilder(param.getIds(), sqlFieldValueBuilder);
		String sql = "update " + tableName + " set DeleteStatus= "+param.getDeleteStatus()+sqlFieldValueBuilder.toString();
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(param.getTraceTemplateId(),functionId);
		dao.update(sql);
		restResult.setState(200);
		restResult.setMsg("删除成功");
		return restResult;
	}
	
	/**
	 * 拼装查询时返回的字段信息
	 * @param functionId
	 * @param isNode
	 * @param traceTemplateId
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public NodeOrFunFields selectFields(String functionId, boolean isNode, String traceTemplateId)
			throws SuperCodeTraceException {
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
		// 返回的字段已经过fieldWeight排序
		StringBuilder fieldNameBuilder = new StringBuilder();
		NodeOrFunFields noFields=new NodeOrFunFields();
		for (String key : fieldsMap.keySet()) {
			TraceFunFieldConfig fieldConfig=fieldsMap.get(key);
			if (null!=fieldConfig) {
				String fieldType=fieldConfig.getFieldType();
				if ("8".equals(fieldType)) {
					fieldNameBuilder.append("DATE_FORMAT("+key+",'%Y-%m-%d %H:%i:%S')");
				}else if(fieldType.startsWith("13")) {
					noFields.setContainObj(true);
				}
			}
			fieldNameBuilder.append(key).append(",");
		}
		String fields = fieldNameBuilder.substring(0, fieldNameBuilder.length() - 1);
		noFields.setFields(fields);
		return noFields;
	}
	
	/**
	 * 拼装查询时返回的字段信息
	 * @param functionId
	 * @param isNode
	 * @param traceTemplateId
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public NodeOrFunFields selectFields(Map<String, TraceFunFieldConfig> fieldsMap)
			throws SuperCodeTraceException {

		if (null == fieldsMap || fieldsMap.isEmpty()) {
			throw new SuperCodeTraceException("无此功能字段", 500);
		}
		NodeOrFunFields noFields=new NodeOrFunFields();
		// 返回的字段已经过fieldWeight排序
		StringBuilder fieldNameBuilder = new StringBuilder();
		for (String key : fieldsMap.keySet()) {
			TraceFunFieldConfig fieldConfig=fieldsMap.get(key);
			if (null!=fieldConfig) {
				String fieldType=fieldConfig.getFieldType();
				if ("8".equals(fieldType)) {
					fieldNameBuilder.append("DATE_FORMAT("+key+",'%Y-%m-%d %H:%i:%S')");
				}else if(fieldType.startsWith("13")) {
					noFields.setContainObj(true);
				}
			}
			fieldNameBuilder.append(key).append(",");
		}
		String fields = fieldNameBuilder.substring(0, fieldNameBuilder.length() - 1);
		noFields.setFields(fields);
		return noFields;
	}
}
