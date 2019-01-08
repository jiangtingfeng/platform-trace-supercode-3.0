package com.jgw.supercodeplatform.trace.service.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicAddOrUpdateParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicDeleteParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.service.template.TraceFunFieldConfigService;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;

@Service
@Transactional
public class DynamicTableService extends AbstractPageService<DynamicTableRequestParam> {
	private static Logger logger = LoggerFactory.getLogger(DynamicTableService.class);
	@Autowired
	private FunctionFieldCacheService functionFieldManageService;

	@Autowired
	private TraceFunFieldConfigService traceFunFieldConfigService;

	@Autowired
	private TraceFunTemplateconfigService traceFunTemplateconfigService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private DynamicServiceDelegate dynamicCommonService;

	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	public RestResult<List<String>> addFunData(DynamicAddOrUpdateParam param)
			throws SuperCodeTraceException, SuperCodeException {
		param.setNode(false);
		RestResult<List<String>> backResult = new RestResult<List<String>>();
		RestResult<String> restResult = dynamicCommonService.addMethodSqlBuilderParamValidate(param);
		if (200 != restResult.getState()) {
			backResult.setState(restResult.getState());
			backResult.setMsg(restResult.getMsg());
			return backResult;
		}
		backResult = dynamicCommonService.funAddOrUpdateSqlBuilder(param, 1);
		if (restResult.getState() == 200) {
			List<String> insertSqls = (List<String>) backResult.getResults();
			if (null == insertSqls || insertSqls.isEmpty()) {
				throw new SuperCodeTraceException("根据参数无法生成sql，请检查参数", 500);
			}
			//定制功能查询记录不能加模板id
			DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,param.getFunctionId());
			for (String insertSql : insertSqls) {
				dao.insert(insertSql);
			}
			backResult.setMsg("操作成功");
			backResult.setResults(null);
		}
		return backResult;
	}

	public RestResult<List<String>> addNodeData(DynamicAddOrUpdateParam param)
			throws SuperCodeTraceException, SuperCodeException {
		param.setNode(true);
		RestResult<List<String>> backResult = new RestResult<List<String>>();
		String functionId = param.getFunctionId();
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		if (StringUtils.isBlank(param.getTraceTemplateId())) {
			// 手动节点有独立的功能id所以根据功能id只会查出一条记录
			TraceFunTemplateconfig traceFunTemplateconfig = traceFunTemplateconfigService
					.selectByNodeFunctionId(functionId);
			if (null == traceFunTemplateconfig) {
				throw new SuperCodeTraceException("无法根据该功能id:" + functionId + "查询出一条模板节点记录", 500);
			}
			param.setTraceTemplateId(traceFunTemplateconfig.getTraceTemplateId());
		}
		RestResult<String> restResult = dynamicCommonService.addMethodSqlBuilderParamValidate(param);
		if (200 != restResult.getState()) {
			backResult.setState(restResult.getState());
			backResult.setMsg(restResult.getMsg());
			return backResult;
		}
		backResult = dynamicCommonService.funAddOrUpdateSqlBuilder(param, 1);
		if (restResult.getState() == 200) {
			List<String> insertSqls = (List<String>) backResult.getResults();
			if (null == insertSqls || insertSqls.isEmpty()) {
				throw new SuperCodeTraceException("根据参数无法生成sql，请检查参数", 500);
			}
			DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(param.getTraceTemplateId(),functionId);
			for (String insertSql : insertSqls) {
				dao.insert(insertSql);
			}
			backResult.setMsg("操作成功");
			backResult.setResults(null);
		}
		return backResult;
	}

	public RestResult<List<String>> delete(DynamicDeleteParam param)
			throws SuperCodeTraceException, SuperCodeException {
		return dynamicCommonService.delete(param);
	}

	public RestResult<List<String>> update(DynamicAddOrUpdateParam param)
			throws SuperCodeTraceException, SuperCodeException {
		return dynamicCommonService.update(param);
	}
    /**
     *  定制功能列表查询
     * @param param
     * @return
     * @throws Exception
     */
	public RestResult<PageResults<List<Map<String, Object>>>> list(DynamicTableRequestParam param) throws Exception {
		RestResult<PageResults<List<Map<String, Object>>>> result=new RestResult<PageResults<List<Map<String, Object>>>>();
		PageResults<List<Map<String, Object>>> p = listSearchViewLike(param);
		List<Map<String, Object>> listData=p.getList();
		if (null==listData || listData.isEmpty()) {
			result.setState(500);
			result.setMsg("无法查询到数据");
			return result;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		String functionId = param.getFunctionId();
		
		TraceFunTemplateconfigQueryParam traceFunTemplateconfigQueryParam=new TraceFunTemplateconfigQueryParam();
		traceFunTemplateconfigQueryParam.setFunctionId(functionId);
		traceFunTemplateconfigQueryParam.setTypeClass(2);
		Map<String, TraceFunFieldConfig> fieldsMap = functionFieldManageService.getFunctionIdFields(traceFunTemplateconfigQueryParam);
		p.setOther(fieldsMap);
		result.setState(200);
		result.setResults(p);
        return result;
	}
    /**
     * 定制功能列表查询记录
     */
	@Override
	protected List<Map<String, Object>> searchResult(DynamicTableRequestParam param)
			throws SuperCodeTraceException, SuperCodeException {
		String sql = querySqlBuilder(null,null,param.getFunctionId(), null,null, false,param);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,param.getFunctionId());
		List<Map<String, Object>> list = dao.select(sql);
		return list;
	}
    /**
     * 定制功能列表查询统计数量
     */
	@Override
	protected int count(DynamicTableRequestParam param) throws Exception {
		String sql = querySqlBuilder(null,null,param.getFunctionId(), null,null, true,param);
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
     * @param searchParam
     * @return
     * @throws SuperCodeTraceException
     * @throws SuperCodeException
     */
	private String querySqlBuilder(String traceBatchInfoId,String traceTemplateId,String functionId, String tableName,String nodeType, 
			boolean isCount,DaoSearch searchParam)
			throws SuperCodeTraceException, SuperCodeException {
		TraceFunTemplateconfigQueryParam traceFunTemplateconfigQueryParam = new TraceFunTemplateconfigQueryParam();

		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		if (StringUtils.isBlank(nodeType)) {
			traceFunTemplateconfigQueryParam.setTypeClass(1);
		} else {
			if (StringUtils.isBlank(traceTemplateId)) {
				throw new SuperCodeTraceException("节点业务数据查询必须传模板id", 500);
			}
			traceFunTemplateconfigQueryParam.setBusinessType(Integer.valueOf(nodeType));
			traceFunTemplateconfigQueryParam.setTypeClass(2);
			traceFunTemplateconfigQueryParam.setTraceTemplateId(traceTemplateId);
		}
		traceFunTemplateconfigQueryParam.setFunctionId(functionId);
		Map<String, TraceFunFieldConfig> fieldsMap = functionFieldManageService
				.getFunctionIdFields(traceFunTemplateconfigQueryParam);
		if (null == fieldsMap || fieldsMap.isEmpty()) {
			throw new SuperCodeTraceException("无此功能字段", 500);
		}
		if (StringUtils.isBlank(tableName)) {
			tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
			if (StringUtils.isBlank(tableName)) {
				throw new SuperCodeTraceException("动态表查询querySqlBuilder方法，无法根据功能id：" + functionId + "获取表名", 500);
			}
		}
		StringBuilder sqlFieldValueBuilder = new StringBuilder();
		
		//过滤掉已被隐藏或删除的记录
		sqlFieldValueBuilder.append(" where DeleteStatus!=1 or DeleteStatus is null");
		Integer flag=searchParam.getFlag();
		
		//防止查到其它组织添加的信息
		String organizationId = commonUtil.getOrganizationId();
		sqlFieldValueBuilder.append(" and  OrganizationId='").append(organizationId).append("'");
		
		//如果是生产管理里的查询则没有批次id
		if (StringUtils.isNotBlank(traceBatchInfoId)) {
			sqlFieldValueBuilder.append(" and  TraceBatchInfoId='").append(traceBatchInfoId).append("'");
		}
		if (null == flag) {
			// 无查询
			logger.info("flag为空，无参数查询，使用默认的模板id和组织id过滤");
		} else {
	
			int count = 0;
			if (1 == flag) {
				// 普通搜索--拼装所有字段匹配
				String search=searchParam.getSearch();
				if (null == search) {
					throw new SuperCodeTraceException("普通搜索 search不能为空", 500);
				}
				sqlFieldValueBuilder.append(" and (");
				for (String fieldCode : fieldsMap.keySet()) {
					sqlFieldValueBuilder.append(fieldCode).append(" like ").append("'").append(search).append("'");
					++count;
					if (count < fieldsMap.size()) {
						sqlFieldValueBuilder.append(" or ");
					}
				}
				sqlFieldValueBuilder.append(")");

			} else {
				// 高级搜索
				Map<String,String> parmsMap=searchParam.getParams();
				if (null == parmsMap || parmsMap.isEmpty()) {
					throw new SuperCodeTraceException("高级搜索搜索 params不能为空", 500);
				}
				sqlFieldValueBuilder.append(" and ");
				for (String fieldCode : parmsMap.keySet()) {
					String fieldValue = parmsMap.get(fieldCode);
					sqlFieldValueBuilder.append(fieldCode).append("=").append("'").append(fieldValue).append("'");
					++count;
					if (count < parmsMap.size()) {
						sqlFieldValueBuilder.append(" and ");
					}
				}
			}
		}
		String sql = null;
		if (isCount) {
			sql = "select count(*) from " + tableName + sqlFieldValueBuilder.toString();
		} else {
			if (null !=searchParam.getStartNumber() && null != searchParam.getPageSize()) {
				sqlFieldValueBuilder.append(" limit ").append(searchParam.getStartNumber()).append(",")
						.append(searchParam.getPageSize());
			}
			// 返回的字段已经过fieldWeight排序
			StringBuilder fieldNameBuilder = new StringBuilder();
			for (String key : fieldsMap.keySet()) {
				fieldNameBuilder.append(key).append(",");
			}
			String fields = fieldNameBuilder.substring(0, fieldNameBuilder.length() - 1);
			sql = "select " + fields + " from " + tableName + sqlFieldValueBuilder.toString();
		}
		return sql;
	}

	public RestResult<String> hideOrDelete(DynamicDeleteParam param) throws SuperCodeTraceException {
		return dynamicCommonService.hideOrDelete(param);
	}
    
	public List<Map<String, Object>> queryTemplateNodeBatchData(String traceBatchInfoId, String traceTemplateId, String functionId,String tableName,String businessType) throws SuperCodeTraceException, SuperCodeException {
		String sql = querySqlBuilder(traceBatchInfoId,traceTemplateId,functionId, tableName, businessType,false,new DaoSearch());
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		List<Map<String, Object>> list = dao.select(sql);
		return list;
		
	}

}
