package com.jgw.supercodeplatform.trace.common.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;

@Component
public class ObjectConfigCache {
 private static transient Map<String, String> objectServiceUrlCache=new HashMap<String, String>();
 
 @Autowired
 private RestTemplateUtil restTemplateUtil;
 
 @Autowired
 private CommonUtil commonUtil;
 
 @Value("${rest.user.url}")
 private String restUserUrl;
 
 public String getServiceUrl(String codeId) throws SuperCodeException, SuperCodeTraceException, IOException {
	 if (StringUtils.isBlank(codeId)) {
		return null;
	 }
	String url= objectServiceUrlCache.get(codeId);
	if (StringUtils.isBlank(url)) {
		synchronized(this) {
			url= objectServiceUrlCache.get(codeId);
			if (StringUtils.isNotBlank(url)) {
				return url;
			}
			
			Map<String,String> headerMap=new HashMap<String,String>();
			headerMap.put("super-token", commonUtil.getSuperToken());
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("pageSize", 100);
			params.put("current", 1);
			ResponseEntity<String> responseEntity=restTemplateUtil.getRequestAndReturnJosn(restUserUrl+"/platform/object/list", params, headerMap);
			if (responseEntity.getStatusCode().value()!=200) {
				throw new SuperCodeTraceException("请求新平台对象集合接口报错--"+responseEntity.getBody(), 500);
			}
			String body=responseEntity.getBody();
			JsonNode bodyNode=new ObjectMapper().readTree(body);
			int state=bodyNode.get("state").asInt();
			if (200!=state) {
				throw new SuperCodeTraceException("请求新平台对象集合接口返回结果报错"+responseEntity.getBody(), 500);
			}
			JsonNode list=bodyNode.get("results").get("list");
			Iterator<JsonNode> it=list.iterator();
			while(it.hasNext()) {
				JsonNode obj=it.next();
				String codeId2=obj.get("codeId").asText();
				String ServiceAddress=obj.get("serviceAddress").asText();
				objectServiceUrlCache.put(codeId2, ServiceAddress);
			}
			url=objectServiceUrlCache.get(codeId);
			if (StringUtils.isBlank(url)) {
				throw new SuperCodeTraceException("对象--"+codeId+"没有配置ServiceAddress", 500);
			}
		}
	}
	return url;
 }
}
