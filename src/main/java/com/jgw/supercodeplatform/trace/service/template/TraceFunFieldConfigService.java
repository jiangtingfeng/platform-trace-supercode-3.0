package com.jgw.supercodeplatform.trace.service.template;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.common.cache.ObjectConfigCache;
import com.jgw.supercodeplatform.trace.common.model.RestResult;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

@Service
public class TraceFunFieldConfigService {
	private static Logger logger = LoggerFactory.getLogger(TraceFunFieldConfigService.class);
	@Autowired
	private TraceFunFieldConfigMapper dao;

    @Autowired
    private TraceFunFieldConfigDelegate traceFunFieldConfigDelegate;
    
	@Autowired
	private ObjectConfigCache objectConfigCache;
	
	
	@Transactional
	public RestResult<List<String>> add(List<TraceFunFieldConfigParam> param) throws Exception {
		RestResult<List<String>> restResult=new RestResult<List<String>>();
		//获取定制功能的字段信息
		List<TraceFunFieldConfig> list=dao.selectDZFPartFieldsByFunctionId(param.get(0).getFunctionId());
		if (null!=list && !list.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该功能已经存在如果要增加字段请通过编辑入口");
			return restResult;
		}
		//动态创建定制功能表和保存字段
		traceFunFieldConfigDelegate.createTableAndGerenteOrgFunRouteAndSaveFields(param, null,true,false,null,null,null,null);
		restResult.setState(200);
		restResult.setMsg("操作成功");
		return restResult;
	}
	
    
	
    /**
     * 修改时只能新增字段也可以对字段顺序就行修改
     * @param param
     * @return
     * @throws SuperCodeTraceException 
     */
	public RestResult<String> update(List<TraceFunFieldConfigParam> param) throws SuperCodeTraceException {
		RestResult<String> restResult=new RestResult<String>();
		String tableName=param.get(0).getEnTableName();
		if (StringUtils.isBlank(tableName)) {
			restResult.setState(500);
			restResult.setMsg("字段对应的动态表名必传");
			return restResult;
		}
		return traceFunFieldConfigDelegate.updateAbstract(param, tableName,false,false,null,null,null,null);
		 
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
	public Set<TraceFunFieldConfig> query(@Valid TraceFunTemplateconfigQueryParam param) throws SuperCodeException, SuperCodeTraceException, IOException {
		List<TraceFunFieldConfig> list=dao.selectNodeOrFunAllFields(param);
		if (null!=list && !list.isEmpty()) {
			for (TraceFunFieldConfig traceFunFieldConfig : list) {
				String objectType=traceFunFieldConfig.getObjectType();
				traceFunFieldConfig.setServiceAddress(objectConfigCache.getServiceUrl(objectType));
			}
		}
		Set<TraceFunFieldConfig> set=new TreeSet<TraceFunFieldConfig>();
		if (null!=list && !list.isEmpty()) {
			set.addAll(list);
		}
		return set;
	}

	public void deleteByTraceTemplateIdAndFunctionId(String traceTemplateId, String functionId) {
		dao.deleteByTraceTemplateIdAndFunctionId(traceTemplateId,functionId);
	}


	public void deleteByTraceTemplateIdAndFunctionIds(String templateConfigId, String strFunctionIds) {
		dao.deleteByTraceTemplateIdAndFunctionIds(templateConfigId,strFunctionIds);		
	}

}
