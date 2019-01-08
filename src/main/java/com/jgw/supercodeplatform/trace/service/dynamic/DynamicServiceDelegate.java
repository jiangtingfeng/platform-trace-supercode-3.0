package com.jgw.supercodeplatform.trace.service.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.ObjectTypeEnum;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicAddOrUpdateParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicDeleteParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import com.jgw.supercodeplatform.trace.service.tracebatch.TraceBatchInfoService;

@Component
public class DynamicServiceDelegate {
	@Autowired
	private TraceBatchInfoService traceBatchInfoService;
	
	@Autowired
	private TraceFunFieldConfigService traceFunFieldConfigService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TraceFunTemplateconfigService traceFunTemplateconfigService;
	
	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	public RestResult<List<String>> delete(DynamicDeleteParam param) throws SuperCodeTraceException {
		RestResult<List<String>> restResult =new RestResult<List<String>>();
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		String functionId = param.getFunctionId();
		deleteInSqlBuilder(param, sqlFieldValueBuilder);
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		if (StringUtils.isBlank(tableName)) {
			 throw new SuperCodeTraceException("删除节点功能数据时无法根据功能id："+functionId+"获取到表名", 500);
		}
		String sql = "delete from " + tableName + sqlFieldValueBuilder.toString();
		
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(param.getTraceTemplateId(),functionId);
		dao.delete(sql);
		restResult.setState(200);
		restResult.setMsg("删除成功");
		return restResult;
	}

	private void deleteInSqlBuilder(DynamicDeleteParam param,StringBuilder sqlFieldValueBuilder) throws SuperCodeTraceException {
		List<Long> ids=param.getIds();
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
	
	public RestResult<List<String>> update(DynamicAddOrUpdateParam param)
			throws SuperCodeTraceException, SuperCodeException {
		RestResult<List<String>> restResult =funAddOrUpdateSqlBuilder(param, 3);
		if (restResult.getState() == 200) {
			List<String> insertSqls = (List<String>) restResult.getResults();
			if (null == insertSqls || insertSqls.isEmpty()) {
				throw new SuperCodeTraceException("根据参数无法生成sql，请检查参数", 500);
			}
			DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(param.getTraceTemplateId(),param.getFunctionId());
			for (String insertSql : insertSqls) {
				dao.update(insertSql);
			}
			restResult.setMsg("操作成功");
			restResult.setResults(null);
		}
		return restResult;
	}
    /**
     *  新增和修改时参数校验，一定会获取模板id
     * @param param
     * @param autoNode
     * @param traceTemplateId
     * @param functionId
     * @return
     */
	public RestResult<String> addMethodSqlBuilderParamValidate(DynamicAddOrUpdateParam param) {
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		String functionId = param.getFunctionId();
		try {
			if (StringUtils.isBlank(functionId)) {
				restResult.setState(500);
				restResult.setMsg("functionId 不能为空");
				return restResult;
			}
			List<LineBusinessData> lineBusinessDataList = param.getLineData();
			if (null==lineBusinessDataList || lineBusinessDataList.isEmpty()) {
				restResult.setState(500);
				restResult.setMsg("data 不能为空");
				return restResult;
			}
			
			//如果是新增节点业务数据则必须是带批次信息的
			if (param.isNode()) {
				String traceBatchInfoId=param.getTraceBatchInfoId();
				if (StringUtils.isBlank(traceBatchInfoId)) {
					restResult.setState(500);
					restResult.setMsg("定制功能节点新增数据，traceBatchInfoId 不能为空");
					return restResult;
				}
			}else {
				for (LineBusinessData lineBusinessData : lineBusinessDataList) {
					List<FieldBusinessParam> fields=lineBusinessData.getFields();
					for (FieldBusinessParam fieldParam : fields) {
						Integer objectType=fieldParam.getObjectType();
						if (null!=objectType) {
							
							ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
							switch (objectTypeEnum) {
							case TRACE_BATCH:
								String traceBatchInfoId=fieldParam.getObjectUniqueValue();
								//如果不是自动节点的插入修改则必须传模板id
								//如果是自动节点则新增时无模板id但必须有批次号
								TraceBatchInfo traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
								if (null==traceBatchInfo) {
									restResult.setState(500);
									restResult.setMsg("不存在此批次唯一id："+traceBatchInfoId);
									return restResult;
								}
								param.setTraceTemplateId(traceBatchInfo.getTraceTemplateId());
								break;
							default:
								break;
							}
						}
					}
				}
				
				if (StringUtils.isBlank(param.getTraceTemplateId())) {
					restResult.setState(500);
					restResult.setMsg("请求为自动节点，无法根据批次对象获取单条批次记录");
					return restResult;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			restResult.setState(500);
			restResult.setMsg("解析data失败:" + e.getMessage());
			return restResult;
		}
		return restResult;
	}
	
	/**
	 * 
	 * @param param
	 * @param       operateType:操作类型 1表示添加 2 删除 3修改 4查询
	 * @return
	 * @throws SuperCodeTraceException 
	 * @throws SuperCodeException 
	 */
	public RestResult<List<String>> funAddOrUpdateSqlBuilder(DynamicAddOrUpdateParam param, int operateType) throws SuperCodeTraceException, SuperCodeException {

		RestResult<List<String>> backResult=new RestResult<List<String>>();
	    
		String traceTemplateId=param.getTraceTemplateId();
		String functionId = param.getFunctionId();
		List<LineBusinessData> lineBusinessDataList=param.getLineData();
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		String organizationId=commonUtil.getOrganizationId();
		StringBuilder sqlFieldNameBuilder = new StringBuilder();
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		List<String> sqlList = new ArrayList<String>();
	    for (LineBusinessData lineBusinessData : lineBusinessDataList) {
	    	List<FieldBusinessParam> fieldBusinessList=lineBusinessData.getFields();
	    	for (FieldBusinessParam fieldBusinessParam : fieldBusinessList) {
	    		switch (operateType) {
	    		case 1: // 表示新增数据操作
	    			caseAdd(sqlFieldNameBuilder, sqlFieldValueBuilder, fieldBusinessParam);
	    			break;
	    		case 3: // 表示修改数据操作
	    			caseUpdate(sqlFieldNameBuilder, fieldBusinessParam);
	    			break;
	    		default:
	    			break;
	    		}
			}
			if (1 == operateType) { // 新增
				// 单条插入 批量优化再说吧
				sqlFieldNameBuilder.append(" OrganizationId,");
				sqlFieldValueBuilder.append("'").append(organizationId).append("',");
				
				sqlFieldNameBuilder.append(" TraceTemplateId");
				sqlFieldValueBuilder.append("'").append(traceTemplateId).append("'");
				
				String fieldNames =sqlFieldNameBuilder.toString();
				String fieldValues = sqlFieldValueBuilder.toString();
				String insertSql = "insert into " + tableName + " (" + fieldNames + ") values (" + fieldValues + ")";
				sqlList.add(insertSql);
			} else if (3 == operateType) { // 修改
				String fieldNames = sqlFieldNameBuilder.substring(0, sqlFieldNameBuilder.length() - 1);
				String insertSql = "update " + tableName + " set " + fieldNames ;
				sqlList.add(insertSql);
				// 跳出外循环，只能单条更新
				break;
			}
		}
	    backResult.setResults(sqlList);
	    backResult.setState(200);
		return backResult;
	}
	/**
	 * 
	 * @param sqlFieldNameBuilder
	 * @param sqlFieldValueBuilder
	 * @param lineNode
	 * @param fieldCode
	 * @param                      fieldType:插入不会传动态表主键id所以fieldType不会有为空的情况
	 * @throws SuperCodeTraceException 
	 */
	private void caseAdd(StringBuilder sqlFieldNameBuilder, StringBuilder sqlFieldValueBuilder, FieldBusinessParam fieldBusinessParam) throws SuperCodeTraceException {
		String fieldCode = fieldBusinessParam.getFieldCode();
		// 剩下的类型都可以当字符处理包括时间
		String fieldValue = fieldBusinessParam.getFieldValue();
		if (StringUtils.isNotBlank(fieldValue) && !"null".equalsIgnoreCase(fieldValue)) {
			sqlFieldNameBuilder.append(fieldCode).append(",");
			sqlFieldValueBuilder.append("'").append(fieldValue).append("'").append(",");
			Integer objectType=fieldBusinessParam.getObjectType();
			if (null!=objectType) {
				ObjectTypeEnum objectTypeEnum=ObjectTypeEnum.getType(objectType);
				switch (objectTypeEnum) {
				case TRACE_BATCH:
					sqlFieldNameBuilder.append("TraceBatchInfoId").append(",");
					sqlFieldValueBuilder.append("'").append(fieldBusinessParam.getObjectUniqueValue()).append("'").append(",");
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param sqlFieldNameBuilder
	 * @param sqlFieldValueBuilder
	 * @param lineNode
	 * @param fieldCode
	 * @param                      fieldType:更新不会更新id所以fieldType不会有为空的情况
	 */
	private void caseUpdate(StringBuilder sqlFieldNameBuilder,FieldBusinessParam fieldBusinessParam) {
		// 剩下的类型都可以当字符处理包括时间
		String fieldValue = fieldBusinessParam.getFieldValue();
		String fieldCode = fieldBusinessParam.getFieldCode();
		if (StringUtils.isNotBlank(fieldValue) && !"null".equalsIgnoreCase(fieldValue)) {
			if ("Id".equals(fieldCode)) {
				sqlFieldNameBuilder.append(" where Id=").append(fieldValue);
			}else {
				sqlFieldNameBuilder.append(fieldCode).append("=").append("'").append(fieldValue).append("'")
				.append(",");
			}
		}
		
	}
    /**
     * 隐藏或删除节点业务数据
     * @param param
     * @return
     * @throws SuperCodeTraceException
     */
	public RestResult<String> hideOrDelete(DynamicDeleteParam param) throws SuperCodeTraceException {
		RestResult<String> restResult =new RestResult<String>();
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		String functionId = param.getFunctionId();
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		if (StringUtils.isBlank(tableName)) {
			 throw new SuperCodeTraceException("删除节点功能数据时无法根据功能id："+functionId+"获取到表名", 500);
		}
		deleteInSqlBuilder(param, sqlFieldValueBuilder);
		String sql = "update " + tableName + " set DeleteStatus=1 "+sqlFieldValueBuilder.toString();
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(param.getTraceTemplateId(),functionId);
		dao.update(sql);
		restResult.setState(200);
		restResult.setMsg("删除成功");
		return restResult;
	}
}
