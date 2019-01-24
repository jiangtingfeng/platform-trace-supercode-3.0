package com.jgw.supercodeplatform.trace.constants;

import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;

public enum ObjectTypeEnum {
	USER(13001,"UserId", "员工"),PRODUCT(13002, "ProductId","产品"),  TRACE_BATCH(13003,"TraceBatchInfoId", "批次");

	public static ObjectTypeEnum getType(Integer codeTypeId) throws SuperCodeTraceException {
		if (null==codeTypeId) {
			throw new SuperCodeTraceException("ObjectTypeEnum.getType参数codeTypeId不能为空", 500);
		}
		switch (codeTypeId) {
		case 13001:
			return USER;
		case 13002:
			return PRODUCT;
		case 13003:
			return TRACE_BATCH;
		default:
			throw new SuperCodeTraceException("无法根据对象类型codeTypeId="+codeTypeId+"获取到对象", 500);
		}
	}
	private int code;
    private String fieldCode;
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ObjectTypeEnum(int code, String fieldCode, String desc) {
		this.code = code;
		this.fieldCode = fieldCode;
		this.desc = desc;
	}


}
