package com.jgw.supercodeplatform.dynamic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.jgw.supercodeplatform.trace.dto.dynamictable.DynamicTableRequestParam;

/**
 * 溯源批次测试
 * @author liujianqiang
 * @date 2018年12月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DynamicTableControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void insert() throws Exception{
		DynamicTableRequestParam param=new DynamicTableRequestParam();
		param.setFunctionId("022d1d34eb1f47d2b25d4a8d35f9df36");
//		param.setData("[{\"sname\":\"测试\",\"sage\":\"15\"}]");
		String data=JSONObject.toJSONString(param);
		this.mockMvc.perform(post("/trace/dynamic/add")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3").header("functionId", "022d1d34eb1f47d2b25d4a8d35f9df36"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	@Test
	public void query() throws Exception{
		DynamicTableRequestParam param=new DynamicTableRequestParam();
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("id", "1");
//		String idstr=JSONObject.toJSONString(map);
		param.setParams("{\\\"Id\\\":\\\"1\\\"}");
		param.setFlag(0);
		String data=JSONObject.toJSONString(param);
		this.mockMvc.perform(post("/trace/dynamic/query")
				.content(data)                //"{\"params\": \"{\\\"Id\\\":\\\"1\\\"}\",\"flag\":\"0\"}";  "\"{\\\"Id\\\":\\\"1\\\"}\""
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3").header("functionId", "022d1d34eb1f47d2b25d4a8d35f9df36"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void delete() throws Exception{
		this.mockMvc.perform(post("/trace/dynamic/delete")
				.content("{\"params\": \"{\"Id\":\"1\"}\"")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3").header("functionId", "022d1d34eb1f47d2b25d4a8d35f9df36"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void update() throws Exception{
		
		this.mockMvc.perform(post("/trace/dynamic/update")
				.content("{\"data\":\"[{\\\"sname\\\":\\\"测试2\\\",\\\"sage\\\":\\\"152\\\"}]\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "db3130cc05f24799a2dc4fd7eacc80f3").header("functionId", "022d1d34eb1f47d2b25d4a8d35f9df36"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
}
