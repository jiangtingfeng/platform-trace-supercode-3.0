package com.jgw.supercodeplatform.trace.service.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceBatchNamedMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceFunComponentMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.tracefun.TraceFunRegulationMapper;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.CustomizeFun;
import com.jgw.supercodeplatform.trace.dto.PlatformFun.FunComponent;
import com.jgw.supercodeplatform.trace.enums.ComponentTypeEnum;
import com.jgw.supercodeplatform.trace.enums.TraceUseSceneEnum;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunComponent;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.cache.ObjectConfigCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldSortCaram;
import com.jgw.supercodeplatform.trace.dto.template.query.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;
import com.jgw.supercodeplatform.trace.enums.TraceUseSceneEnum;

@Service
public class TraceFunFieldConfigService {
	private static Logger logger = LoggerFactory.getLogger(TraceFunFieldConfigService.class);
	@Autowired
	private TraceFunFieldConfigMapper dao;

    @Autowired
    private TraceFunFieldConfigDelegate traceFunFieldConfigDelegate;
    
	@Autowired
	private ObjectConfigCache objectConfigCache;
	
	@Autowired
	private TraceOrgFunRouteMapper traceOrgFunRouteDao;
	
	@Autowired
	private TraceApplicationContextAware traceApplicationContextAware;


	@Autowired
	private TraceFunComponentMapper traceFunComponentMapper;

	@Autowired
	private TraceFunRegulationMapper traceFunRegulationMapper;

	@Autowired
	private TraceBatchNamedMapper traceBatchNamedMapper;

	private void addGroupField(CustomizeFun customizeFun){
		List<FunComponent> funComponentModels=customizeFun.getFunComponentModels();
		if (funComponentModels!=null && funComponentModels.size()>0){
			for (FunComponent funComponent:funComponentModels){
				if(!ComponentTypeEnum.isNestComponent(funComponent.getComponentType())){
					List<TraceFunFieldConfigParam> fieldConfigParams=funComponent.getTraceFunFieldConfigModel();
					if(fieldConfigParams!=null && fieldConfigParams.size()>0){
						customizeFun.getTraceFunFieldConfigModel().addAll(fieldConfigParams);
					}
				}
			}
		}
	}

	private boolean  checkAddField(CustomizeFun customizeFun,RestResult<List<String>> restResult){
		boolean result=true;
		List<TraceFunFieldConfigParam> param=customizeFun.getTraceFunFieldConfigModel();

		List<TraceFunFieldConfigParam> batchParams= param.stream().filter(e->e.getFieldCode().equals("TraceBatchInfoId")).collect(Collectors.toList());
		if(batchParams==null || batchParams.size()==0){
			if(customizeFun.getUseSceneType() == TraceUseSceneEnum.CreateBatch.getKey()){


			} else {
				result=false;
				restResult.setState(500);
				restResult.setMsg("新增定制功能必须选择产品或批次对象");
			}
		}



		return result;
	}

	@Transactional
	public RestResult<List<String>> add(CustomizeFun customizeFun) throws Exception {
		RestResult<List<String>> restResult=new RestResult<List<String>>();

		List<TraceFunFieldConfigParam> param=customizeFun.getTraceFunFieldConfigModel();

		addGroupField(customizeFun);

		 if(!checkAddField(customizeFun,restResult)){
		 	return  restResult;
		 }

		boolean containsBatch=TraceFunFieldConfigDelegate.checkAddParam(param);
		/*if (!containsBatch || true) {
			restResult.setState(500);
			restResult.setMsg("新增定制功能必须选择产品和批次对象");
			return restResult;
		}*/
		//获取定制功能的字段信息
		List<TraceFunFieldConfig> list=dao.selectDZFPartFieldsByFunctionId(param.get(0).getFunctionId());
		if (null!=list && !list.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该功能已经存在如果要增加字段请通过编辑入口");
			return restResult;
		}

		//创建定制功能使用规则、功能组件、字段
		traceFunFieldConfigDelegate.saveFunComponentAndRegulation(customizeFun);

		//动态创建定制功能表和保存字段
		traceFunFieldConfigDelegate.createTableAndGerenteOrgFunRouteAndSaveFields(param, true,param.get(0).getFunctionId(),param.get(0).getFunctionName());

		restResult.setState(200);
		restResult.setMsg("操作成功");
		return restResult;
	}
	
    
	
    /**
     * 修改时只能新增字段也可以对字段顺序就行修改
     * @param param
     * @return
     * @throws Exception 
     */
	public RestResult<String> update(CustomizeFun customizeFun) throws Exception {
		RestResult<String> restResult=new RestResult<String>();


		List<TraceFunFieldConfigParam> param=customizeFun.getTraceFunFieldConfigModel();

		List<TraceFunFieldConfigParam> addConfigLlist=new ArrayList<TraceFunFieldConfigParam>();
		boolean isJustAddField=traceFunFieldConfigDelegate.checkupdateFunParam(param, addConfigLlist);
		if (isJustAddField) {
			String tableName=null;
			String functionId=null;
			String functionName=null;
			for (TraceFunFieldConfigParam traceFunFieldConfigParam : param) {
				if (StringUtils.isNotBlank(traceFunFieldConfigParam.getEnTableName())) {
					tableName=traceFunFieldConfigParam.getEnTableName();
				}
				if (StringUtils.isNotBlank(traceFunFieldConfigParam.getFunctionId())) {
					functionId=traceFunFieldConfigParam.getFunctionId();
				}
				
				if (StringUtils.isNotBlank(traceFunFieldConfigParam.getFunctionName())) {
					functionName=traceFunFieldConfigParam.getFunctionName();
				}
			}
			if (StringUtils.isBlank(tableName) || StringUtils.isBlank(functionId) || StringUtils.isBlank(functionName)) {
				restResult.setState(500);
				restResult.setMsg("新增定制功能字段更新操作，无法获取表名");
				return restResult;
			}
			
			if (null!=addConfigLlist && !addConfigLlist.isEmpty()) {
				return traceFunFieldConfigDelegate.addNewFields(addConfigLlist, tableName,false,false,functionId,functionName,null,null);
			}
			dao.updateDzGnFunctionNameByFunctionId(param.get(0).getFunctionName(),param.get(0).getFunctionId());
			restResult.setState(200);
			restResult.setMsg("成功");
			return restResult;
		}else {
			//如果是任意修改的话就校验所有非空参数 且校验是否有产品和批次对象
			boolean containsBatch=TraceFunFieldConfigDelegate.checkAddParam(param);
			if (!containsBatch) {
				restResult.setState(500);
				restResult.setMsg("ry修改定制功能必选产品和批次对象");
				return restResult;
			}
			return arbitraryUpdate(customizeFun);
		}
		 
	}
	
    
	public String getEnTableNameByFunctionId(String functionId) throws SuperCodeTraceException {
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("getEnTableNameByFunctionId方法传入的功能id为空", 500);
		}
		String tableName=dao.getEnTableNameByFunctionId(functionId);
		if (StringUtils.isBlank(tableName)) {
			throw new SuperCodeTraceException("getEnTableNameByFunctionId方法无法根据功能id："+functionId+"查找到表名", 500);
		}
		return tableName;
	}
    /**
     *  根据功能id排序查询所有字段信息--已不适用// TODO 
     * @param param
     * @return
     * @throws IOException 
     * @throws SuperCodeTraceException 
     * @throws SuperCodeException 
     */
	public List<TraceFunFieldConfig> query(@Valid TraceFunTemplateconfigQueryParam param) throws SuperCodeException, SuperCodeTraceException, IOException {
		List<TraceFunFieldConfig> list=dao.selectNodeOrFunAllFields(param);
		if (null!=list && !list.isEmpty()) {
			for (TraceFunFieldConfig traceFunFieldConfig : list) {
				String objectType=traceFunFieldConfig.getObjectType();
				traceFunFieldConfig.setServiceAddress(objectConfigCache.getServiceUrl(objectType));
			}
		}
		return list;
	}

	public List<TraceFunComponent> selectFunComponentByFunId(String funId)
	{
		return traceFunComponentMapper.selectByFunId(funId);
	}

	public TraceFunRegulation selectTraceFunRegulation(String funId)
	{
		return traceFunRegulationMapper.selectByFunId(funId);
	}

	public List<TraceBatchNamed> selectTraceBatchNamed(String funId)
	{
		return traceBatchNamedMapper.selectByFunId(funId);
	}


	public void deleteByTraceTemplateIdAndFunctionId(String traceTemplateId, String functionId) {
		dao.deleteByTraceTemplateIdAndFunctionId(traceTemplateId,functionId);
	}


	public void deleteByTraceTemplateIdAndFunctionIds(String templateConfigId, String strFunctionIds) {
		dao.deleteByTraceTemplateIdAndFunctionIds(templateConfigId,strFunctionIds);		
	}


	public RestResult<String> fielSort(List<TraceFunFieldSortCaram> param) throws SuperCodeTraceException {
		List<TraceFunFieldConfig> fields=new ArrayList<TraceFunFieldConfig>();
		RestResult<String> restResult=new RestResult<String>();
		for (TraceFunFieldSortCaram traceFunFieldConfigParam : param) {
			if (null==traceFunFieldConfigParam.getId()) {
				throw new SuperCodeTraceException("主键id不能为空", 500);
			}
			TraceFunFieldConfig field=new TraceFunFieldConfig();
			field.setFieldWeight(traceFunFieldConfigParam.getFieldWeight());
			field.setId(traceFunFieldConfigParam.getId());
			fields.add(field);
		}
		if (null==fields || fields.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("参数不能为空");
		}else {
			dao.batchUpdate(fields);
			restResult.setState(200);
			restResult.setMsg("操作成功");
		}
		return restResult;
	}

	RestResult<String> updateWithAddField(CustomizeFun customizeFun,String tableName) throws Exception
	{
		RestResult<String> restResult=new RestResult<String>();

		String functionId=customizeFun.getFunId(),functionName=null;
		List<TraceFunFieldConfigParam> traceFunFieldConfigParams=null,addConfigLlist=null;

		List<FunComponent> funComponents=customizeFun.getFunComponentModels();
		if(funComponents!=null && funComponents.size()>0){
			for(FunComponent funComponent:funComponents){
				if(StringUtils.isEmpty(funComponent.getComponentId())){
					traceFunFieldConfigDelegate.saveFunComponent(funComponent,functionId);
				} else {
					String componentId=funComponent.getComponentId();
					functionName = funComponent.getComponentName();
					traceFunFieldConfigParams= funComponent.getTraceFunFieldConfigModel();
					addConfigLlist= traceFunFieldConfigParams.stream().filter(e->e.getId()==null).collect(Collectors.toList());
					if(addConfigLlist.size()>0){
						if(ComponentTypeEnum.isNestComponent(funComponent.getComponentType())){
                            TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(null, componentId);
                            tableName=traceOrgFunRoute.getTableName();
							traceFunFieldConfigDelegate.addNewFields(addConfigLlist, tableName,false,false,componentId,functionName,null,null);
						} else {
							for(TraceFunFieldConfigParam fieldConfigParam:addConfigLlist){
								fieldConfigParam.setComponentId(componentId);
							}
						}
					}
				}

			}
		}

		functionId=customizeFun.getFunId();
		functionName=customizeFun.getFunName();
		traceFunFieldConfigParams= customizeFun.getTraceFunFieldConfigModel();
		addConfigLlist= traceFunFieldConfigParams.stream().filter(e->e.getId()==null).collect(Collectors.toList());
		if(addConfigLlist.size()>0){
			restResult=traceFunFieldConfigDelegate.addNewFields(addConfigLlist, tableName,false,false,functionId,functionName,null,null);
		}

		traceFunRegulationMapper.deleteTraceFunRegulation(functionId);
		traceBatchNamedMapper.deleteTraceBatchNamed(functionId);
		traceFunFieldConfigDelegate.saveFunRegulation(customizeFun);

		restResult.setState(200);
		restResult.setMsg("操作成功");
		return restResult;
	}


    /**
     * 任意修改定制功能表
     * @param param
     * @return
     * @throws Exception 
     */
	@Transactional(rollbackFor = Exception.class)
	public RestResult<String> arbitraryUpdate(CustomizeFun customizeFun) throws Exception {
		RestResult<String> restResult=new RestResult<String>();
		List<TraceFunFieldConfigParam> param=customizeFun.getTraceFunFieldConfigModel();
		String functionId=param.get(0).getFunctionId();

        addGroupField(customizeFun);

		TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(null, functionId);
		/*if (null==traceOrgFunRoute) {
			restResult.setState(500);
			restResult.setMsg("该定制功能未建立企业功能路由记录无法删除");
			return restResult;
		}*/
		DynamicBaseMapper baseMapper=traceApplicationContextAware.getDynamicMapperByFunctionId(null, functionId);
		String querySQL="select * from "+traceOrgFunRoute.getTableName()+" limit 1";

		List<LinkedHashMap<String, Object>> data=baseMapper.select(querySQL);
		if (null!=data && !data.isEmpty()) {
			restResult = updateWithAddField(customizeFun,traceOrgFunRoute.getTableName());
			//restResult.setState(500);
			//restResult.setMsg("该定制功能表已有数据不能修改");
		} else {
			String trunkSQL="DROP TABLE "+traceOrgFunRoute.getTableName();
			try{
				//删除已建立的定制功能表
				baseMapper.update(trunkSQL);
			}catch (Exception e){
				e.printStackTrace();
				logger.error(e.toString());
			}

			//删除企业路由关系
			traceOrgFunRouteDao.deleteByDzFunctionId(param.get(0).getFunctionId());

			//删除字段
			dao.deleteDzFieldsByFunctionId(functionId);

			traceFunComponentMapper.deleteTraceFunComponent(functionId);
			traceFunRegulationMapper.deleteTraceFunRegulation(functionId);
			traceBatchNamedMapper.deleteTraceBatchNamed(functionId);

			//创建新的定制功能时依然校验

			TraceFunFieldConfigDelegate.checkAddParam(param);

			traceFunFieldConfigDelegate.saveFunComponentAndRegulation(customizeFun);

			//动态创建定制功能表和保存字段
			traceFunFieldConfigDelegate.createTableAndGerenteOrgFunRouteAndSaveFields(param, true,param.get(0).getFunctionId(),param.get(0).getFunctionName());
		}

        restResult.setState(200);
        restResult.setMsg("操作成功");
		return restResult;
	}



	public List<TraceFunFieldConfig> selectPartTraceTemplateIdAndNodeFunctionId(String templateConfigId,
			String nodeFunctionId) {
		return dao.selectPartTraceTemplateIdAndNodeFunctionId(templateConfigId, nodeFunctionId);
	}

}
