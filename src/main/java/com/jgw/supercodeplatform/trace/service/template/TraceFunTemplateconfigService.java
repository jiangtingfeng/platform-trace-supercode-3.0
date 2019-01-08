package com.jgw.supercodeplatform.trace.service.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFunTemplateconfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFuntemplateStatisticalMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigDeleteParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigListParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigUpdateParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigUpdateSubParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFuntemplateStatistical;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;
import com.jgw.supercodeplatform.trace.vo.TemplateconfigAndNodeVO;
import com.jgw.supercodeplatform.trace.vo.TraceFunTemplateconfigVO;

@Service
public class TraceFunTemplateconfigService extends AbstractPageService {
	private static Logger logger = LoggerFactory.getLogger(TraceFunTemplateconfigService.class);
	@Autowired
	private TraceFuntemplateStatisticalMapper traceFuntemplateStatisticalDao;
	
    @Autowired
    private TraceFunTemplateconfigMapper traceFunTemplateconfigDao;
    
    @Autowired
    private TraceFunFieldConfigService traceFunFieldConfigService;
    
    @Autowired
    private TraceOrgFunRouteMapper traceOrgFunRouteDao;
    
    @Autowired
    private DynamicTableService dynamicTableService;
    
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private TraceFunFieldConfigDelegate traceFunFieldConfigDelegate;
    
    @Transactional
	public RestResult<String> add(List<TraceFunTemplateconfigParam> templateList) throws Exception {
		RestResult<String> restResult=new RestResult<String>();
		String templateId = getUUID();
		String templateName = templateList.get(0).getTraceTemplateName();
		String organizationId = commonUtil.getOrganizationId();
		List<TraceFunTemplateconfig> existTemplateList=traceFunTemplateconfigDao.selectByTempNameAndOrgId(templateName,organizationId);
		if (null!=existTemplateList && !existTemplateList.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该组织下已有此名称模板不能重复创建");
			return restResult;
		}
		
		save(templateList, templateId, organizationId);
		
		//新增或更新统计表
		saveOrUpdateTemplateStatistical(templateId,templateName,organizationId,templateList.size());
		
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}
    /**
     * 修改溯源模板
     * @param traceFunTemplateconfigUpdateParam
     * @return
     * @throws Exception 
     */
    @Transactional
	public RestResult<String> update(TraceFunTemplateconfigUpdateParam traceFunTemplateconfigUpdateParam) throws Exception {
		RestResult<String> restResult=new RestResult<>();
		if (null==traceFunTemplateconfigUpdateParam) {
			restResult.setState(500);
			restResult.setMsg("无任何数据传入");
			return restResult;
		}
		String templateConfigName=traceFunTemplateconfigUpdateParam.getTraceTemplateName();
		String templateConfigId=traceFunTemplateconfigUpdateParam.getTraceTemplateId();
		if (StringUtils.isBlank(templateConfigId) || StringUtils.isBlank(templateConfigName)) {
			restResult.setState(500);
			restResult.setMsg("模板id和模板名称不能为空");
			return restResult;
		}
		
		//批量更新模板配置
        List<TraceFunTemplateconfigVO> templateConfigList=traceFunTemplateconfigDao.selectByTemplateId(templateConfigId);
        if (null==templateConfigList || templateConfigList.isEmpty()) {
        	restResult.setState(500);
			restResult.setMsg("模板id错误不存在此模板");
			return restResult;
		}
		//新增节点数量
		int addNodeCount=0;
		//删除节点数量
		int deleteFunctionNum=0;
		
		//获取新增节点或新增节点字段数据
		List<TraceFunTemplateconfigUpdateSubParam> templateList=traceFunTemplateconfigUpdateParam.getTemplateList();
		if (null!=templateList && !templateList.isEmpty()) {
			String organizationId=commonUtil.getOrganizationId();
			List<String> functionIds=new ArrayList<String>();
			
			List<TraceFunTemplateconfigParam> templateList2=new ArrayList<TraceFunTemplateconfigParam>();
			for (TraceFunTemplateconfigUpdateSubParam traceFunTemplateconfigUpdateSubParam : templateList) {
				Integer operateType=traceFunTemplateconfigUpdateSubParam.getOperateType();
				switch (operateType) {
				case 1:
					//新增节点--新增节点配置，新建企业路由关系，动态创建功能表，新增模板配置表记录
					TraceFunTemplateconfigParam tftf=new TraceFunTemplateconfigParam();
					tftf.setBusinessTypes(traceFunTemplateconfigUpdateSubParam.getBusinessTypes());
					tftf.setFieldConfigList(traceFunTemplateconfigUpdateSubParam.getFieldConfigList());
					tftf.setNodeFunctionName(traceFunTemplateconfigUpdateSubParam.getNodeFunctionName());
					tftf.setTraceTemplateId(templateConfigId);
					tftf.setNodeFunctionId(traceFunTemplateconfigUpdateSubParam.getNodeFunctionId());
					tftf.setTraceTemplateName(templateConfigName);
					templateList2.add(tftf);
					break;
		        case 2:
		        	//新增节点字段--更新节点配置表及修改节点表结构
		        	String tableName=traceFunTemplateconfigUpdateSubParam.getFieldConfigList().get(0).getEnTableName();
		        	String businessType=traceFunTemplateconfigUpdateSubParam.getBusinessTypes();
		        	String templateId=traceFunTemplateconfigUpdateParam.getTraceTemplateId();
		        	String nodeFunctionId=traceFunTemplateconfigUpdateSubParam.getNodeFunctionId();
		        	String nodeFunctionName=traceFunTemplateconfigUpdateSubParam.getNodeFunctionName();
		        	RestResult<String> result=traceFunFieldConfigDelegate.updateAbstract(traceFunTemplateconfigUpdateSubParam.getFieldConfigList(), tableName, false, true,nodeFunctionId,nodeFunctionName,templateId ,businessType);
					if (result.getState()!=200) {
						return result;
					}
		        	break;
		        case 3:
		        	//删除节点
		        	functionIds.add(traceFunTemplateconfigUpdateSubParam.getNodeFunctionId());
					break;
				default:
					break;
				}
			}
			
			//如果删除节点集合不为空则删除模板对应节点集合
			if (null!=functionIds && !functionIds.isEmpty()) {
				String strFunctionIds=buildStrIds(functionIds);
				int num=traceFunTemplateconfigDao.deleteByTemplateIdAndFunctionIds(templateConfigId,strFunctionIds);
				if (functionIds.size()==num) {
					//更新组织功能路由信息
					traceOrgFunRouteDao.deleteByFunctionIdsAndTemplateConfigId(strFunctionIds,templateConfigId);
					
					//删除节点对应的字段属性
					traceFunFieldConfigService.deleteByTraceTemplateIdAndFunctionIds(templateConfigId,strFunctionIds);
				}else {
					throw new SuperCodeTraceException("如有节点更新操作则已完成，但删除操作只删除成功部分节点，成功数："+num+"没有删除成功不继续执行节点新增操作，请重试", 500);
				}
				//TODO是否删除企业路由关系记录待确认--毕竟添加的时候是在这里添加的
				deleteFunctionNum=num;
			}
			
			//新增模板节点
			if (null!=templateList2 && !templateList2.isEmpty()) {
				save(templateList2, templateConfigId, organizationId);
				addNodeCount=templateList2.size();
			}
			
			//由于更新时不能更改字段属性所以这个方法不需要，否则执行更改时应修改对应的表结构
//			for (TraceFunTemplateconfig traceFunTemplateconfig : templateConfigList) {
//				traceFunTemplateconfigDao.update(traceFunTemplateconfig);
//			}
			
			//更新统计表
			saveOrUpdateTemplateStatistical(templateConfigId, templateConfigName,organizationId,addNodeCount-deleteFunctionNum);
		}else {
			//没有模板节点数据则默认跟新模板名称
			TraceFuntemplateStatistical traceFuntemplateStatistical=traceFuntemplateStatisticalDao.selectByTemplateId(templateConfigId);
			traceFuntemplateStatistical.setTraceTemplateName(templateConfigName);
			traceFuntemplateStatisticalDao.update(traceFuntemplateStatistical);
		}
		restResult.setState(200);
		restResult.setMsg("操作成功");
		return restResult;
	}
	
	/**
	 * 执行保存操作
	 * @param templateList
	 * @param templateId
	 * @param organizationId
	 * @return
	 * @throws Exception 
	 */
	private void save(List<TraceFunTemplateconfigParam> templateList, String templateId,String organizationId) throws Exception {
		for (TraceFunTemplateconfigParam traceFunTemplateconfigParam : templateList) {
			String nodeFunctionId=null;
			//如果是自动节点，那么节点功能id使用定制功能的功能id否则生成新的节点功能id
			if ("1".equals(traceFunTemplateconfigParam.getBusinessTypes())) {
				nodeFunctionId=templateList.get(0).getFieldConfigList().get(0).getFunctionId();
			}else {
				nodeFunctionId=commonUtil.StringFilter(getUUID());
			}
			
			//节点对应马平台创建出来的功能id
			//创建字段及新建功能表，创建企业路由关系，动态创建表
			List<TraceFunFieldConfigParam> fieldConfigList=traceFunTemplateconfigParam.getFieldConfigList();
			traceFunFieldConfigDelegate.createTableAndGerenteOrgFunRouteAndSaveFields(fieldConfigList,organizationId,true,true,nodeFunctionId,traceFunTemplateconfigParam.getNodeFunctionName(),traceFunTemplateconfigParam.getBusinessTypes(),templateId);
			
			//保存模板配置表
			saveTraceTemplateConfig(traceFunTemplateconfigParam,templateId,organizationId,nodeFunctionId);
		}
	}
    /**
     * 更新或新增模板统计表
     * @param templateId
     * @param templateName
     * @param createMan
     * @param size
     * @throws SuperCodeException 
     */
	private void saveOrUpdateTemplateStatistical(String templateId, String templateName,String organizationId, int size) throws SuperCodeException {
		TraceFuntemplateStatistical traceFuntemplateStatistical=traceFuntemplateStatisticalDao.selectByTemplateId(templateId);
		if (null==traceFuntemplateStatistical) {
			//保存他、追溯模板统计表
			AccountCache accountCache=commonUtil.getUserLoginCache();
			traceFuntemplateStatistical=new TraceFuntemplateStatistical();
			traceFuntemplateStatistical.setNodeCount(size);
			traceFuntemplateStatistical.setTraceTemplateId(templateId);
			traceFuntemplateStatistical.setTraceTemplateName(templateName);
			traceFuntemplateStatistical.setOrganizationId(organizationId);
			traceFuntemplateStatistical.setCreateMan(accountCache.getUserName());
			traceFuntemplateStatistical.setCreateId(accountCache.getUserId());
			traceFuntemplateStatisticalDao.insert(traceFuntemplateStatistical);
		}else {
			traceFuntemplateStatistical.setNodeCount(traceFuntemplateStatistical.getNodeCount()+size);
			traceFuntemplateStatistical.setTraceTemplateName(templateName);
			traceFuntemplateStatisticalDao.update(traceFuntemplateStatistical);
		}
	}

	/**
	 * 新增追溯模板配置
	 * @param traceFunTemplateconfigParam
	 * @param templateId
	 * @param organizationId 
	 * @param functionId ：新增节点功能id
	 */
	private void saveTraceTemplateConfig(TraceFunTemplateconfigParam traceFunTemplateconfigParam, String templateId, String organizationId, String functionId) {
		TraceFunTemplateconfig tftc=new TraceFunTemplateconfig();
		tftc.setBusinessTypes(traceFunTemplateconfigParam.getBusinessTypes());
		tftc.setNodeFunctionId(functionId);
		tftc.setNodeWeight(traceFunTemplateconfigParam.getNodeWeight());
		tftc.setNodeFunctionName(traceFunTemplateconfigParam.getNodeFunctionName());
		tftc.setTraceTemplateId(templateId);
		traceFunTemplateconfigDao.insert(tftc);		
	}
	

    /**
     * 根据集合生成字符串id加逗号
     * @param functionIds
     * @return
     */
	private String buildStrIds(List<String> functionIds) {
		if (null!=functionIds && !functionIds.isEmpty()) {
			StringBuilder builder=new StringBuilder();
			for (String functionId : functionIds) {
				builder.append("'").append(functionId).append("'").append(",");
			}
			return builder.substring(0, builder.length()-1);
		}
		
		return null;
	}


	/**
	 * 普通搜索和高级搜索
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	@Override
	protected List<TraceFunTemplateconfigListParam>  searchResult(DaoSearch searchParams) throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		int startNumber = (searchParams.getCurrent()-1)*searchParams.getPageSize();
		searchParams.setStartNumber(startNumber);
		return traceFunTemplateconfigDao.selectTemplateReturnDataTypeByTemplateId(searchParams,organizationId);
	}

	@Override
	protected int count(DaoSearch searchParams)throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		return traceFunTemplateconfigDao.count(searchParams,organizationId);
	}

	/**
	 * 不分页模板列表
	 * @return
	 * @throws Exception
	 */
	public List<TraceFunTemplateconfigListParam> selectTemplateByTemplateId() throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		return traceFunTemplateconfigDao.selectTemplateByTemplateId(organizationId);
	}


	public List<TraceFunTemplateconfigVO> listNodes(String traceTemplateId) {
		return traceFunTemplateconfigDao.selectByTemplateId(traceTemplateId);
	}
	/**
	 * 该方法仅限于手动功能节点id使用，自动节点会被多个模板关联不能根据功能id查出唯一一条记录
	 * @param functionId
	 * @return
	 */
	public TraceFunTemplateconfig selectByNodeFunctionId(String functionId) {
		return traceFunTemplateconfigDao.selectByNodeFunctionId(functionId);
	}
	/**
	 * 根据模板id和节点id删除节点信息
	 * @param param
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional
	@Deprecated
	public RestResult<String> delete(TraceFunTemplateconfigDeleteParam param) throws SuperCodeException {
		RestResult<String> restResult=new RestResult<String>();
		String organizationId=commonUtil.getOrganizationId();
		param.setOrgnazitionId(organizationId);
		int num=traceFunTemplateconfigDao.deleteByTemplateIdAndFunctionId(param);
		if (num==1) {
			//更新溯源模板统计信息
			TraceFuntemplateStatistical traceFuntemplateStatistical=traceFuntemplateStatisticalDao.selectByTemplateId(param.getTraceTemplateId());
			if (null!=traceFuntemplateStatistical) {
				int nodeCount=traceFuntemplateStatistical.getNodeCount();
				//如果该模板下只有一个节点则删除统计数据否则节点数减一更新模板统计
				if (1==nodeCount) {
					traceFuntemplateStatisticalDao.deleteById(traceFuntemplateStatistical.getId());
				}else {
					traceFuntemplateStatistical.setNodeCount(nodeCount-1);
					traceFuntemplateStatisticalDao.update(traceFuntemplateStatistical);
				}
			}
			//更新组织功能路由信息
			traceOrgFunRouteDao.deleteByFunctionId(param.getNodeFunctionId(),param.getTraceTemplateId());
			
			//删除节点对应的字段属性
			traceFunFieldConfigService.deleteByTraceTemplateIdAndFunctionId(param.getTraceTemplateId(),param.getNodeFunctionId());
			restResult.setState(200);
			restResult.setMsg("删除成功");
		}else {
			restResult.setState(500);
			restResult.setMsg("删除失败");
		}
		return restResult;
	}
	/**
	 * 根据追溯模板id排序查询模板下节点业务数据
	 * @param traceTemplateId
	 * @throws Exception 
	 */
	public RestResult<List<TemplateconfigAndNodeVO>> queryNodeInfo(String traceBatchInfoId,String traceTemplateId) throws Exception {
		RestResult<List<TemplateconfigAndNodeVO>> restResult=new RestResult<List<TemplateconfigAndNodeVO>>();
		//查询顺序节点信息拼装对应动态表名用于查询节点业务数据
		List<TraceFunTemplateconfigVO> templateConfigList=traceFunTemplateconfigDao.getTemplateAndFieldInfoByTemplateId(traceTemplateId);
		if (null==templateConfigList || templateConfigList.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("模板id："+traceTemplateId+"对应的节点信息不存在");
			return restResult;
		}
		
		List<TemplateconfigAndNodeVO> nodeDataList=new ArrayList<TemplateconfigAndNodeVO>();
		for (TraceFunTemplateconfigVO traceFunTemplateconfigVO : templateConfigList) {
			List<Map<String, Object>> nodeData=dynamicTableService.queryTemplateNodeBatchData(traceBatchInfoId,traceTemplateId,traceFunTemplateconfigVO.getNodeFunctionId(),traceFunTemplateconfigVO.getEnTableName(),traceFunTemplateconfigVO.getBusinessTypes());
			if (null!=nodeData && !nodeData.isEmpty()) {
				TemplateconfigAndNodeVO tn=new TemplateconfigAndNodeVO();
				tn.setBusinessTypes(traceFunTemplateconfigVO.getBusinessTypes());
				tn.setNodeFunctionId(traceFunTemplateconfigVO.getNodeFunctionId());
				tn.setNodeFunctionName(traceFunTemplateconfigVO.getFunctionName());
				tn.setNodeData(nodeData);
				nodeDataList.add(tn);
			}
		}
		//判断节点数据集合是否为空，为空则表示该模板下的节点无业务数据
		if (nodeDataList.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该模板下的节点无数据");
		}else {
			restResult.setState(200);
			restResult.setResults(nodeDataList);
			restResult.setMsg("成功");
		}
		return restResult;
	}
	@Transactional
	public RestResult<String> deleteById(Long id) throws SuperCodeException {
		RestResult<String> restResult=new RestResult<String>();
		String organizationId=commonUtil.getOrganizationId();
		TraceFunTemplateconfig traceFunTemplateconfig=traceFunTemplateconfigDao.selectById(id);
		if (null==traceFunTemplateconfig) {
			restResult.setState(500);
			restResult.setMsg("不存在该id记录");
			return restResult;
		}
		int num=traceFunTemplateconfigDao.deleteById(id);
		if (num==1) {
			//更新溯源模板统计信息
			TraceFuntemplateStatistical traceFuntemplateStatistical=traceFuntemplateStatisticalDao.selectByTemplateId(traceFunTemplateconfig.getTraceTemplateId());
			if (null!=traceFuntemplateStatistical) {
				int nodeCount=traceFuntemplateStatistical.getNodeCount();
				//如果该模板下只有一个节点则删除统计数据否则节点数减一更新模板统计
				if (1==nodeCount) {
					traceFuntemplateStatisticalDao.deleteById(traceFuntemplateStatistical.getId());
				}else {
					traceFuntemplateStatistical.setNodeCount(nodeCount-1);
					traceFuntemplateStatisticalDao.update(traceFuntemplateStatistical);
				}
			}
			//更新组织功能路由信息
			traceOrgFunRouteDao.deleteByFunctionId(traceFunTemplateconfig.getNodeFunctionId(),traceFunTemplateconfig.getTraceTemplateId());
			
			//删除节点对应的字段属性
			traceFunFieldConfigService.deleteByTraceTemplateIdAndFunctionId(traceFunTemplateconfig.getTraceTemplateId(),traceFunTemplateconfig.getNodeFunctionId());
			restResult.setState(200);
			restResult.setMsg("删除成功");
		}else {
			restResult.setState(500);
			restResult.setMsg("删除失败");
		}
		return restResult;
	}

}
