package com.jgw.supercodeplatform.trace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回结果公共类
 * @author liujianqiang
 * @date 2018年1月12日
 */
@ApiModel(description = "返回结果")
public class RestResult <T>{
	
	private static final long serialVersionUID = 5331794418863519060L;
	@ApiModelProperty(value = "返回成功标识：200-成功；非200-失败",position = 0)
	private Integer state;
	@ApiModelProperty(value = "成功或者失败的描述",position = 1)
	private String msg;
	@ApiModelProperty(value = "返回的业务数据，如没有为null",position = 2)
	private T results;
	public RestResult() {}


	public RestResult(Integer state, String msg) {
		this.state = state;
		this.msg = msg;
	}


	public RestResult(Integer state, String msg, T results) {
		this.state = state;
		this.msg = msg;
		this.results = results;
	}

	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public T getResults() {
		return results;
	}


	public void setResults(T results) {
		this.results = results;
	}


	
	
}
