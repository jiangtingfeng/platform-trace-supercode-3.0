package com.jgw.supercodeplatform.tracebatch;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;

/**
 * 溯源批次测试
 * @author liujianqiang
 * @date 2018年12月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TraceBatchInfoControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	/**
	 * 新增溯源批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @throws Exception
	 */
	@Test
	public void insertTraceBatchInfo() throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("productId", "77bdb343edcc46f598d629e2b7940eeb");
		obj.put("productName", "蘑菇");
		obj.put("traceBatchName", "农垦58号2018009");
		obj.put("traceBatchId", "6");
		obj.put("listedTime", "2018-12-19");
		obj.put("traceTemplateId", "add0ac717ea144a19d152c53d7335aaa");
		obj.put("traceTemplateName", "模板2");
		obj.put("h5TrancePageId", "5456d43asdvmfhtjlf598d4564rgd98945");
		obj.put("h5TempleteName", "H5模板1");
		
		this.mockMvc.perform(post("/trace/batch/info")
				.content(obj.toJSONString())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "ad36118772a547f7898e7e1652dbe356"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 新增溯源批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @throws Exception
	 */
	@Test
	public void updateTraceBatchInfo() throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("traceBatchInfoId", "d31ac4dd3d064654a448e3f940f424fb");
		obj.put("productId", "77bdb343edcc46f598d629e2b7940345");
		obj.put("productName", "蘑菇2");
		obj.put("traceBatchName", "农垦58号2018008");
		obj.put("traceBatchId", "5");
		obj.put("listedTime", "2018-12-19");
		obj.put("traceTemplateId", "34bdb4356edcc46f598d4564rgd940e343");
		obj.put("traceTemplateName", "溯源模板2");
		obj.put("h5TrancePageId", "5456d43asdvmfhtjlf598d4564rgd989234");
		obj.put("h5TempleteName", "H5模板2");
		
		this.mockMvc.perform(put("/trace/batch/info")
				.content(obj.toJSONString())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "64b379cd47c843458378f479a115c322"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 删除批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @throws Exception
	 */
	@Test
	public void deleteTraceBatchInfo() throws Exception{
		
		this.mockMvc.perform(delete("/trace/batch/info")
				.param("traceBatchInfoId", "d77f5cfc77654dd78bd8078533be2199")
				.param("traceTemplateId", "add0ac717ea144a19d152c53d7335aaa")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "64b379cd47c843458378f479a115c322"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 获取分页溯源批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @throws Exception
	 */
	@Test
	public void listTraceBatchInfoByOrgPage() throws Exception{
		
		this.mockMvc.perform(get("/trace/batch/info/page")
				.param("search", "1")
//				.param("", "")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "64b379cd47c843458378f479a115c322"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	/**
	 * 获取溯源批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @throws Exception
	 */
	@Test
	public void listTraceBatchInfoByOrg() throws Exception{
		
		this.mockMvc.perform(get("/trace/batch/info")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("super-token", "64b379cd47c843458378f479a115c322"))
		.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
}
