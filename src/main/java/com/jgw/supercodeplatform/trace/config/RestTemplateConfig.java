package com.jgw.supercodeplatform.trace.config;

import java.nio.charset.StandardCharsets;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * restTempalte配置类
 * @author liujianqiang
 * @date 2018年9月13日
 */
@Configuration
public class RestTemplateConfig {
	
	@Bean(name="restTemplate")
	@LoadBalanced
	public RestTemplate restTemplateBalance() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
	
}
