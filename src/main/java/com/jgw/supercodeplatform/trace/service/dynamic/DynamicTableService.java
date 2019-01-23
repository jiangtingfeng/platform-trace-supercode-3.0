package com.jgw.supercodeplatform.trace.service.dynamic;

import java.util.LinkedHashMap;
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
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.model.NodeOrFunFields;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.AddBusinessDataModel;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.DynamicHideParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.LineBusinessData;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicAddFunParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.fun.DynamicDeleteFunParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicAddNodeParam;
import com.jgw.supercodeplatform.trace.dto.dynamictable.node.DynamicDeleteNodeParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.blockchain.BlockChainService;
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
	private BlockChainService blockChainService;

	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	/**
	 * 新增定制功能数据无法让前端直接传模板id和批次id需要自己找
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public RestResult<String> addFunData(DynamicAddFunParam param)
			throws Exception {
		String functionId = param.getFunctionId();
		RestResult<String> backResult = new RestResult<String>();
		//校验参数
		AddBusinessDataModel addBusinessDataModel = dynamicServiceDelegate.addMethodSqlBuilderParamValidate(param.getFunctionId(),param.getLineData(),false,null,null);

		//校验批次记录存不存在
		String traceBatchInfoId=addBusinessDataModel.getTraceBatchInfoId();
		TraceBatchInfo traceBatchInfo=traceBatchInfoService.selectByTraceBatchInfoId(traceBatchInfoId);
		if (null==traceBatchInfo) {
			backResult.setState(500);
			backResult.setMsg("无法根据traceBatchInfoId="+traceBatchInfoId+"查询到记录");
			return backResult;
		}
		//获取到表名
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		
		
		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		StringBuilder sqlFieldValueBuilder=new StringBuilder();
		
		boolean containObj=dynamicServiceDelegate.funAddOrUpdateSqlBuilder(param.getLineData(), 1,sqlFieldNameBuilder,sqlFieldValueBuilder,false);
		
		String organizationId=null;
		try {
			 organizationId=commonUtil.getOrganizationId();
		} catch (Exception e) {
		}
		
		String insertSql = addDefaultField(tableName, null,organizationId, sqlFieldNameBuilder,
				sqlFieldValueBuilder);
		
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,functionId);
		dao.insert(insertSql);
		
		
		//更新批次节点数据条数
		try {
			blockChainService.coChain(param.getLineData(),containObj);
			Integer nodeDataCount=traceBatchInfo.getNodeDataCount();
			if (null==nodeDataCount) {
				traceBatchInfo.setNodeDataCount(1);
			}else {
				traceBatchInfo.setNodeDataCount(nodeDataCount+1);
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
		boolean containObj=dynamicServiceDelegate.funAddOrUpdateSqlBuilder(adDataModel.getLineData(), 1,sqlFieldNameBuilder,sqlFieldValueBuilder,true);
		
		//判断当前sql里是否包含批次信息
		int batchInfoId=sqlFieldNameBuilder.indexOf("TraceBatchInfoId");
		if (batchInfoId<0) {
			sqlFieldNameBuilder.append(" TraceBatchInfoId,");
			sqlFieldValueBuilder.append("'").append(adDataModel.getTraceBatchInfoId()).append("',");
		}
		
		String organizationId=commonUtil.getOrganizationId();
		//组装插入sql
		String insertSql = addDefaultField(tableName, traceTemplateId,organizationId, sqlFieldNameBuilder,
				sqlFieldValueBuilder);
		
		//根据模板id和功能id获取对应的库mapper
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		//插入
		dao.insert(insertSql);
		
		//数据上链
		blockChainService.coChain(param.getLineData(),containObj);
		//插入成功更新批次节点数据条数
		Integer nodeDataCount = traceBatchInfo.getNodeDataCount();
		if (null == nodeDataCount) {
			traceBatchInfo.setNodeDataCount(1);
		} else {
			traceBatchInfo.setNodeDataCount(nodeDataCount + 1);
		}
		traceBatchInfoService.updateTraceBatchInfo(traceBatchInfo);
		
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

	public RestResult<String> update(String functionId,LineBusinessData lineData,boolean isNode,String traceTemplateId)
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
		StringBuilder sqlFieldNameBuilder=new StringBuilder();
		StringBuilder sqlFieldValueBuilder=new StringBuilder();
		String tableName = traceFunFieldConfigService.getEnTableNameByFunctionId(functionId);
		DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(traceTemplateId,functionId);
		
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
		
		try {
			
			//获取当前节点或功能的字段
			NodeOrFunFields nodeOrFunFields=dynamicServiceDelegate.selectFields(functionId, isNode, traceTemplateId);
			String querySQL="select "+nodeOrFunFields.getFields()+" from "+tableName+sqlFieldValueBuilder.toString();
			List<LinkedHashMap<String, Object>> listDdata=dao.select(querySQL);
			blockChainService.coChain(listDdata,nodeOrFunFields.isContainObj());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		backResult.setMsg("操作成功");
		backResult.setState(200);
		return backResult;
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
		return list;
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
			if (null !=searchParam.getStartNumber() && null != searchParam.getPageSize()) {
				sqlFieldValueBuilder.append(" limit ").append(searchParam.getStartNumber()).append(",")
						.append(searchParam.getPageSize());
			}
			
			NodeOrFunFields nodeOrFunFields =dynamicServiceDelegate.selectFields(fieldsMap);
					
			sql = "select " + nodeOrFunFields.getFields() + " from " + tableName + sqlFieldValueBuilder.toString();
		}
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
	public RestResult<Map<String, Object>> getById(Long id, String functionId) throws SuperCodeTraceException {
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
		if (null!=list && !list.isEmpty()) {
			restResult.setResults(list.get(0));
		}
		restResult.setMsg("成功");
		restResult.setState(200);
		return restResult;
	}

}
