package com.jgw.supercodeplatform;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.dynamicMapper1.DynamicCreateTableMapper1;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFunTemplateconfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFuntemplateStatisticalMapper;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
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
	
	 @Value("${rest.user.url}")
	 private String restUserUrl;
		@Test
		public void geturl() throws SuperCodeException, SuperCodeTraceException, IOException {
			TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(null, "504");
			System.out.println(traceOrgFunRoute);
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
		tftc.setBusinessTypes("1");
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
			traceFunTemplateconfig.setFunctionName("修改后的名称1");
//			traceFunTemplateconfigDao.update(traceFunTemplateconfig);
		}
	}
}
