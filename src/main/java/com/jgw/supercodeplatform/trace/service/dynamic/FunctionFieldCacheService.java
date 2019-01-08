package com.jgw.supercodeplatform.trace.service.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

@Component
public class FunctionFieldCacheService {
	private static Logger logger = LoggerFactory.getLogger(FunctionFieldCacheService.class);
	@Autowired
	private TraceFunFieldConfigMapper traceFunFieldConfigDao;
	/**
	 * @author czm |fieldCode1-->{fieldType,....} | the map pattern functionId
	 *         :|fieldCode2-->{fieldType,....} | |fieldCode3-->{fieldType,....}
	 * 
	 */
	private static volatile Map<String, Map<String, TraceFunFieldConfig>> functionFieldMap = new HashMap<String, Map<String, TraceFunFieldConfig>>();

	public Map<String, TraceFunFieldConfig> getFunctionIdFields(TraceFunTemplateconfigQueryParam param)
			throws SuperCodeTraceException {
		String functionId = param.getFunctionId();
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		String mapKey = functionId;
		//如果节点类型不为空则表示请求节点字段属性
		if (null != param.getBusinessType()) {
			param.setTypeClass(2);
			String traceTemplateId = param.getTraceTemplateId();
			if (StringUtils.isBlank(param.getTraceTemplateId())) {
				throw new SuperCodeTraceException("节点获取功能字段必须传模板id", 500);
			}
			mapKey = traceTemplateId + functionId;
		} else {
			//否则表示请求定制功能字段
			param.setTypeClass(1);
		}
		Map<String, TraceFunFieldConfig> fieldsMap = functionFieldMap.get(mapKey);
		if (null == fieldsMap) {
			synchronized (this) {
				// volatile语义保证functionFieldMap写入操作会立即写入主存线程读取也直接从主存读取，当前线程释放锁之后保证functionFieldMap有对应functionId数据除非数据库种无数据
				fieldsMap = functionFieldMap.get(functionId);
				if (null == fieldsMap) {
					fieldsMap = new HashMap<String, TraceFunFieldConfig>();
					List<TraceFunFieldConfig> fieldConfigList = traceFunFieldConfigDao.selectPartNodeOrFunFields(param);
					if (null == fieldConfigList || fieldConfigList.isEmpty()) {
						logger.info("获取功能字段信息无法更加功能id：" + functionId + "查询到字段信息");
						return null;
					}
					for (TraceFunFieldConfig traceFunFieldConfig : fieldConfigList) {
						fieldsMap.put(traceFunFieldConfig.getFieldCode(), traceFunFieldConfig);
					}
					functionFieldMap.put(functionId, fieldsMap);
				}
			}
		}
		return fieldsMap;
	}

	public void flush(TraceFunTemplateconfigQueryParam param) throws SuperCodeTraceException {
		String functionId = param.getFunctionId();
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		String mapKey = functionId;
		if (null != param.getBusinessType()) {
			param.setTypeClass(2);
			String traceTemplateId = param.getTraceTemplateId();
			if (StringUtils.isBlank(param.getTraceTemplateId())) {
				throw new SuperCodeTraceException("节点获取功能字段必须传模板id", 500);
			}
			mapKey = traceTemplateId + functionId;
		} else {
			param.setTypeClass(1);
		}
		synchronized (this) {
			Map<String, TraceFunFieldConfig> fieldsMap = functionFieldMap.get(mapKey);
			if (null == fieldsMap) {
				fieldsMap = new HashMap<String, TraceFunFieldConfig>();
			}
			List<TraceFunFieldConfig> fieldConfigList = traceFunFieldConfigDao.selectPartNodeOrFunFields(param);
			if (null == fieldConfigList || fieldConfigList.isEmpty()) {
				logger.info("获取功能字段信息无法更加功能id：" + functionId + "查询到字段信息");
				return;
			}
			for (TraceFunFieldConfig traceFunFieldConfig : fieldConfigList) {
				fieldsMap.put(traceFunFieldConfig.getFieldCode(), traceFunFieldConfig);
			}
			functionFieldMap.put(functionId, fieldsMap);
		}
	}

}
