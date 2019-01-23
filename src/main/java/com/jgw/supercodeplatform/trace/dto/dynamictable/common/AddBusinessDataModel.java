package com.jgw.supercodeplatform.trace.dto.dynamictable.common;

public class AddBusinessDataModel {
	private LineBusinessData lineData;
	
	private String functionId;
	
	private String traceTemplateId;      //溯源模板号
	
	private String traceBatchInfoId;

	public LineBusinessData getLineData() {
		return lineData;
	}

	public void setLineData(LineBusinessData lineData) {
		this.lineData = lineData;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public String getTraceBatchInfoId() {
		return traceBatchInfoId;
	}

	public void setTraceBatchInfoId(String traceBatchInfoId) {
		this.traceBatchInfoId = traceBatchInfoId;
	}

	
}
