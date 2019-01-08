package com.jgw.supercodeplatform.trace.common.model;

import java.io.Serializable;
import java.util.Map;

/**
 * paramsMap和returnMap实体类
 * @author liujianqiang
 * @date 2018年11月12日
 */
public class ReturnParamsMap implements Serializable{
	
	private static final long serialVersionUID = 8548957882896575027L;
	
	private Map<String,Object> paramsMap;//参数map
	private Map<String,Object> returnMap;//返回map
	
	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public Map<String, Object> getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}
	
}
