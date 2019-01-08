package com.jgw.supercodeplatform.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

/**
 * 溯源批次测试
 * @author liujianqiang
 * @date 2018年12月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TraceFunFieldConfigControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	@Test
	public void inser() throws Exception{
		List<TraceFunFieldConfigParam>field_list=new ArrayList<TraceFunFieldConfigParam>();
		TraceFunFieldConfigParam field=new TraceFunFieldConfigParam();
		field.setFieldCode("sname");
		field.setFieldName("学生姓名");
		field.setFieldType("1");
		field.setFunctionName("模板节点2");
		field.setFunctionId("dzgn1");
		field.setMaxSize(10);
		field.setTypeClass(1);
		field.setFieldWeight(1);
		field.setIsRequired(1);
		field_list.add(field);
		String data=JSONObject.toJSONString(field_list);
		this.mockMvc.perform(post("/trace/traceFieldFunConfig/add")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "f2b14ed19fc34635b92ce28d61bc329f"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void update() throws Exception{
		List<TraceFunFieldConfigParam>field_list=new ArrayList<TraceFunFieldConfigParam>();
		TraceFunFieldConfigParam field=new TraceFunFieldConfigParam();
		field.setFieldCode("haha");
		field.setFieldName("哈哈");
		field.setFieldType("1");
		field.setFunctionName("模板节点2");
		field.setFunctionId("dzgn1");
		field.setMaxSize(10);
		field.setTypeClass(1);
		field.setEnTableName("trace_dynamic_120_dzgn1");
		field.setFieldWeight(1);
		field.setIsRequired(1);
		field_list.add(field);
		String data=JSONObject.toJSONString(field_list);
		this.mockMvc.perform(post("/trace/traceFieldFunConfig/update")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "f2b14ed19fc34635b92ce28d61bc329f"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	
	/**
	 * 查询
	 * @throws Exception
	 */
	@Test
	public void query() throws Exception{
		this.mockMvc.perform(get("/trace/traceFieldFunConfig/query")
				.content("functionId=022d1d34eb1f47d2b25d4a8d35f9df36")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.header("super-token", "f2b14ed19fc34635b92ce28d61bc329f"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
}
