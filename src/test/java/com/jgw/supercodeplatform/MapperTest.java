package com.jgw.supercodeplatform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.cache.FunctionFieldCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.dynamicMapper1.DynamicCreateTableMapper1;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFunTemplateconfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFuntemplateStatisticalMapper;
import com.jgw.supercodeplatform.trace.dto.template.query.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFuntemplateStatistical;
import com.jgw.supercodeplatform.trace.vo.TraceFunTemplateconfigVO;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeTraceApplication.class) // 指定我们SpringBoot工程的Application启动类
public class MapperTest {
	 private static transient Map<Integer, String> objectServiceUrlCache=new HashMap<Integer, String>();
	@Autowired
	private TraceFunFieldConfigMapper traceFunFieldConfigDao;
	@Autowired
	private TraceFunTemplateconfigMapper traceFunTemplateconfigDao;
	@Autowired
	private TraceFuntemplateStatisticalMapper traceFuntemplateStatisticalDao;
	@Autowired
	private TraceOrgFunRouteMapper traceOrgFunRouteDao;
	@Autowired
	private DynamicCreateTableMapper1 dynamicCreateTableDao;
	
	@Autowired
	private RestTemplateUtil restTemplateUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TraceApplicationContextAware applicationAware;
	
	@Autowired
	private TraceBatchInfoMapper  traceBatchInfoDao;
	private static volatile Map<String, Map<String, TraceFunFieldConfig>> functionFieldMap = new HashMap<String, Map<String, TraceFunFieldConfig>>();
	
	@Autowired
	private FunctionFieldCache  functionFieldCache;
	
	 @Value("${rest.user.url}")
	 private String restUserUrl;
		@Test
		public void geturl() throws SuperCodeException, SuperCodeTraceException, IOException {
			DynamicBaseMapper baseDao=applicationAware.getDynamicMapperByFunctionId("c7d3f896194c49d18241564d2d3e2845", "f343b78d433146d18939b951ad3f3169");
			Map<String, TraceFunFieldConfig> fieldsMap = functionFieldCache
					.getFunctionIdFields("c7d3f896194c49d18241564d2d3e2845","f343b78d433146d18939b951ad3f3169",2);
			
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
			String sql="select "+fields+" from trace_dynamic_0110_1013_node_f343b78d433146d18939b951ad3f3169 ";
			List<LinkedHashMap<String, Object>> data=baseDao.select(sql);
			
            for (LinkedHashMap<String, Object> map : data) {
    			for (String key : map.keySet()) {
    				Object v=map.get(key);
    				System.out.println(v);
    			}
			}
		}
	 
		@Test
		public void fielSort() throws SuperCodeException, SuperCodeTraceException, IOException {
			List<TraceFunFieldConfig> fields=new ArrayList<TraceFunFieldConfig>();
			TraceFunFieldConfig field=new TraceFunFieldConfig();
			field.setId(1055l);
			field.setFieldWeight(55);
			
			TraceFunFieldConfig field1=new TraceFunFieldConfig();
			field1.setId(1054l);
			field1.setFieldWeight(52);
			
			fields.add(field1);
			fields.add(field);
			
			traceFunFieldConfigDao.batchUpdate(fields);
		}
	public void aware() {
		DynamicBaseMapper baseMapper=applicationAware.getDynamicMapperByTableNum();
		baseMapper.dynamicDDLTable("create table zc_test(id int primary key ,name varchar(10))");
		System.out.println(baseMapper);
	}
	@Test
	public void selectOrg() {
		TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId("1", "1");
		System.out.println(traceOrgFunRoute);
	}
	
	@Test
	public void fieldMap() throws SuperCodeTraceException {
		 Map<String, TraceFunFieldConfig> map=getFunctionIdFields("e5aa3b38afcc418eb1b99a5ab386edc7", "90f6434c456b45d4b90465bc43160536", 2);
	     System.out.println(map);
	}
	public Map<String, TraceFunFieldConfig> getFunctionIdFields(String traceTemplateId,String functionId,int typeClass)
			throws SuperCodeTraceException {
		if (StringUtils.isBlank(functionId)) {
			throw new SuperCodeTraceException("functionId不能为空", 500);
		}
		String mapKey = functionId;
		//如果节点类型不为空则表示请求节点字段属性
		if (2==typeClass) {
			if (StringUtils.isBlank(traceTemplateId)) {
				throw new SuperCodeTraceException("节点获取功能字段必须传模板id", 500);
			}
			mapKey = traceTemplateId + functionId;
		}
		Map<String, TraceFunFieldConfig> fieldsMap = functionFieldMap.get(mapKey);
		if (null == fieldsMap) {
			synchronized (this) {
				// volatile语义保证functionFieldMap写入操作会立即写入主存线程读取也直接从主存读取，当前线程释放锁之后保证functionFieldMap有对应functionId数据除非数据库种无数据
				fieldsMap = functionFieldMap.get(functionId);
				if (null == fieldsMap) {
					fieldsMap = new LinkedHashMap <String, TraceFunFieldConfig>();
					List<TraceFunFieldConfig> fieldConfigList =null;
					if (1==typeClass) {
						fieldConfigList =traceFunFieldConfigDao.selectDZFPartFieldsByFunctionId(functionId);
					}else {
						fieldConfigList = traceFunFieldConfigDao.selectPartTraceTemplateIdAndNodeFunctionId(traceTemplateId,functionId);
					}
					if (null == fieldConfigList || fieldConfigList.isEmpty()) {
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
	
	@Test
	public void selectTraceFun() throws SuperCodeTraceException {
//		Map<String, Object> params=new HashMap<String, Object>();
//		List<String> list=new ArrayList<>();
//		list.add("1001");
//		params.put("traceTemplateIdss", list);
//		
//		Map<String, String> headerMap=new HashMap<String, String>();
//		headerMap.put("super-token", "ad36118772a547f7898e7e1652dbe356");
//		ResponseEntity<String> rest=restTemplateUtil.getRequestAndReturnJosn("http://PLATFORM-USER-SUPERCODE-TEST/platform/h5/Ids", params, headerMap);
//		System.out.println(rest);
		
//		List<TraceFunTemplateconfigVO> list2=traceFunTemplateconfigDao.getTemplateAndFieldInfoByTemplateId("98f30caa5e7e4206ad66d5fbf89454c0");
//		System.out.println(list2);
		
		TraceFunTemplateconfigQueryParam param=new TraceFunTemplateconfigQueryParam();
//		param.setBusinessType(1);
		param.setFunctionId("dzf2");
		param.setTraceTemplateId("a9297d9943a74f3e833d7aaaacd0641f");
		param.setTypeClass(1);
		List<TraceFunFieldConfig> list=traceFunFieldConfigDao.selectNodeOrFunAllFields(param);
		System.out.println(list);
	}
	
	@Test
	public void deleteFieldConfig() {
//		traceFunFieldConfigDao.deleteByFunctionId("1");
		int num=traceFunTemplateconfigDao.deleteByTemplateIdAndFunctionIds("1","'1','2'");
		System.out.println(num);
	}
	@Test
	public void batchFunField() {
		List<TraceFunFieldConfig> list=new ArrayList<TraceFunFieldConfig>();
		
		TraceFunFieldConfig tffc=new TraceFunFieldConfig();
		tffc.setCreateBy("zc");
		tffc.setFieldType("13");
		tffc.setFieldName("测试1");
		tffc.setId(122l);
		TraceFunFieldConfig tffc1=new TraceFunFieldConfig();
		tffc1.setCreateBy("jay");
		tffc1.setFieldType("13");
		tffc1.setFieldName("测试2");
		tffc1.setId(123l);
		list.add(tffc);
		list.add(tffc1);
		traceFunFieldConfigDao.batchUpdate(list);
	}
	
	@Test
	public void insertTemplatConfig() {
		TraceFunTemplateconfig tftc=new TraceFunTemplateconfig();
		tftc.setBusinessType("1");
		tftc.setNodeFunctionId("1");
		tftc.setNodeFunctionName("播种");
		traceFunTemplateconfigDao.insert(tftc);
	}
	
	@Test
	public void insertTemplatConfigStatistical() {
		TraceFuntemplateStatistical tfts=new TraceFuntemplateStatistical();
		tfts.setCreateMan("zc");
		tfts.setNodeCount(2);
		tfts.setTraceTemplateName("template1");
		traceFuntemplateStatisticalDao.insert(tfts);
	}
	
	@Test
	public void batchUpdate() {
		
		List<TraceFunTemplateconfigVO>templateConfigList=traceFunTemplateconfigDao.selectByTemplateId("58b47b8c-d7e8-4f88-9cd5-9f80ca07bb83");
		for (TraceFunTemplateconfigVO traceFunTemplateconfig : templateConfigList) {
			traceFunTemplateconfig.setNodeFunctionName("修改后的名称1");
//			traceFunTemplateconfigDao.update(traceFunTemplateconfig);
		}
		
		List<TraceFunTemplateconfigVO>templateConfigList2=new ArrayList<>();
		List<TraceFunTemplateconfigVO>templateConfigList3=templateConfigList2;
		
		TraceFunTemplateconfigVO v1=new TraceFunTemplateconfigVO();
		templateConfigList3.add(v1);
		System.out.println(templateConfigList.size());
	}
	
	public static void main(String[] args) {
		if ("https://account.aliyun.com/login/login.htm".equals("https://account.aliyun.com/login/login.htm")) {
			System.out.println(1);
		}
	}
}
