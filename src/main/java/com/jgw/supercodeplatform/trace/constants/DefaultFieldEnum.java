package com.jgw.supercodeplatform.trace.constants;

public enum DefaultFieldEnum {
	ORGANIZATION_ID("OrganizationId","组织id","1"),
	TRACETEMPLATE_ID("TraceTemplateId","溯源模板id","1"),
	TRACEBATCHINFO_ID("TraceBatchInfoId","批次唯一id","1"),
	SYSId_ID("SysId","系统id","1"),
	SORTDATETIME("SortDateTime","排序时间",""),
	DELETE_STATUS("DeleteStatus","隐藏删除状态",""),
	ID("Id","主键",""),
	USERID("UserId","用户id","1"),
	PRODUCTID("ProductId","产品id","1");
	private DefaultFieldEnum(String fieldCode, String fieldName, String fieldType) {
		this.fieldCode = fieldCode;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}

	private String fieldCode;
	
	private String fieldName;
	
	private String fieldType;

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	
}
