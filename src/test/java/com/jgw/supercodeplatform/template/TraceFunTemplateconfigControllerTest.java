package com.jgw.supercodeplatform.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigParam;
import com.jgw.supercodeplatform.trace.dto.template.query.TraceFunTemplateconfigListParam;
import com.jgw.supercodeplatform.trace.dto.template.update.TraceFunTemplateconfigUpdateParam;
import com.jgw.supercodeplatform.trace.dto.template.update.TraceFunTemplateconfigUpdateSubParam;

/**
 * 溯源批次测试
 * @author liujianqiang
 * @date 2018年12月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TraceFunTemplateconfigControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void inser() throws Exception{
		List<TraceFunTemplateconfigParam> templateList=new ArrayList<TraceFunTemplateconfigParam>();
		TraceFunTemplateconfigParam template=new TraceFunTemplateconfigParam();
		template.setBusinessType("1");
		template.setNodeFunctionName("模板节点名称1");
		template.setNodeWeight(1);
		template.setTraceTemplateName("模板名称11");
		List<TraceFunFieldConfigParam>field_list=new ArrayList<TraceFunFieldConfigParam>();
		TraceFunFieldConfigParam field=new TraceFunFieldConfigParam();
		field.setFieldCode("sname");
		field.setFieldName("学生姓名");
		field.setFieldType("1");
		field.setFunctionName("模板节点1");
		field.setMaxSize(10);
		field.setTypeClass(1);
		field.setFieldWeight(1);
		field.setIsRequired(1);
		field_list.add(field);
		template.setFieldConfigList(field_list);
		templateList.add(template);
		String data=JSONObject.toJSONString(templateList);
		this.mockMvc.perform(post("/trace/traceFunTemplateconfig/add")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "eba484319e2745f88c64b6f39e1a4993"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void update() throws Exception{
		TraceFunTemplateconfigUpdateParam traceFunTemplateconfigUpdateParam=new TraceFunTemplateconfigUpdateParam();
		traceFunTemplateconfigUpdateParam.setTraceTemplateId("e10cc051-d319-4896-9ebe-86f338f74a04");
		traceFunTemplateconfigUpdateParam.setTraceTemplateName("修改模板名称1");
		List<TraceFunTemplateconfigUpdateSubParam> sublist=new ArrayList<TraceFunTemplateconfigUpdateSubParam>();
		TraceFunTemplateconfigUpdateSubParam sub=new TraceFunTemplateconfigUpdateSubParam();
		sub.setBusinessTypes("2");
		sub.setNodeFunctionId("022d1d34eb1f47d2b25d4a8d35f9df36");
		sub.setNodeWeight(2);
		sub.setOperateType(2);
		List<TraceFunFieldConfigParam>field_list=new ArrayList<TraceFunFieldConfigParam>();
		TraceFunFieldConfigParam field=new TraceFunFieldConfigParam();
		field.setFieldCode("sage");
		field.setFieldName("学生姓名");
		field.setFieldType("1");
		field.setFunctionName("模板节点1");
		field.setEnTableName("trace_dynamic_118_022d1d34eb1f47d2b25d4a8d35f9df36");
		field.setMaxSize(10);
		field.setFunctionName("模板节点名称1");
		field.setTypeClass(1);
		field.setFieldWeight(1);
		field.setFunctionId("022d1d34eb1f47d2b25d4a8d35f9df36");
		field.setIsRequired(1);
		field_list.add(field);
		sub.setFieldConfigList(field_list);
		sublist.add(sub);
		traceFunTemplateconfigUpdateParam.setTemplateList(sublist);
		String data=JSONObject.toJSONString(traceFunTemplateconfigUpdateParam);
		this.mockMvc.perform(post("/trace/traceFunTemplateconfig/update")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 查询模板节点
	 * @throws Exception
	 */
	@Test
	public void listNodes() throws Exception{
		this.mockMvc.perform(post("/trace/traceFunTemplateconfig/listNodes")
				.content("traceTemplateId=e10cc051-d319-4896-9ebe-86f338f74a04")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 查询模板节点
	 * @throws Exception
	 */
	@Test
	public void query() throws Exception{
		TraceFunTemplateconfigListParam query=new TraceFunTemplateconfigListParam();
		query.setOrganizationId("");
		String data=JSONObject.toJSONString(query);
		this.mockMvc.perform(post("/trace/traceFunTemplateconfig/query")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
}
