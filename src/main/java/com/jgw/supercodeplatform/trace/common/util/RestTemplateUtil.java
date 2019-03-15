package com.jgw.supercodeplatform.trace.common.util;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;


@Component
public class RestTemplateUtil {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 发送get请求返回json数据
	 * @param url
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeTraceException 
	 */
	public ResponseEntity<String> getRequestAndReturnJosn(String url,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeTraceException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeTraceException("sendGetRequestAndReturnJosn参数url不能为空", 500);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url);
		if (null!=params && !params.isEmpty()) {
			for(String key:params.keySet()) {
				Object value=params.get(key);
				builder.queryParam(key,  value);
			}
		}
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        return result;
    }
	
	
	/**
	 * 发送post请求返回json数据
	 * @param url
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeTraceException 
	 */
	public ResponseEntity<String> postJsonDataAndReturnJosn(String url,String json,Map<String, String> headerMap) throws SuperCodeTraceException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeTraceException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}
		HttpHeaders headers = new HttpHeaders();
		//默认值 发送json数据
		headers.add("content-Type", "application/json");
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> result = restTemplate.exchange(url,
				HttpMethod.POST, requestEntity, String.class);
        return result;
    }

	public ResponseEntity<String> putJsonDataAndReturnJosn(String url,String json,Map<String, String> headerMap) throws SuperCodeTraceException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeTraceException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}
		HttpHeaders headers = new HttpHeaders();
		//默认值 发送json数据
		headers.add("content-Type", "application/json");
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> result = restTemplate.exchange(url,
				HttpMethod.PUT, requestEntity, String.class);
		return result;
	}


	public ResponseEntity<String> deleteJsonDataAndReturnJosn(String url,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeTraceException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeTraceException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}
		url+="?";
		for (Map.Entry<String, Object> entry: params.entrySet())
		{
			url+=entry.getKey()+"="+entry.getValue().toString()+"&";
		}
		HttpHeaders headers = new HttpHeaders();
		//默认值 发送json数据
		headers.add("content-Type", "application/json");
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(url,
				HttpMethod.DELETE, requestEntity, String.class);
		return result;
	}

    /**
     * 上传文件
     * @param url
     * @param fileParamName
     * @param params
     * @param headerMap
     * @return
     * @throws SuperCodeTraceException
     */
	public ResponseEntity<String> uploadFile(String url,String fileParamName,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeTraceException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeTraceException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}
		
		Object file=params.remove(fileParamName);	
		if (null==file) {
			throw new SuperCodeTraceException("文件不存在", 500);
		}
		FileSystemResource fs = new FileSystemResource((File) file);
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		param.add(fileParamName, fs);
		
		if (null!=params && !params.isEmpty()) {
			for(String key:params.keySet()) {
				Object value=params.get(key);
				param.add(key, value);
			}
		}
        
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("multipart/form-data");
		headers.setContentType(type);
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param,headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.POST, httpEntity, String.class);
        return responseEntity;
    }
	
}
