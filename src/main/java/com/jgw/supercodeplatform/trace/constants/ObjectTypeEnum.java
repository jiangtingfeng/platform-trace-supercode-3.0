package com.jgw.supercodeplatform.trace.constants;

import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;

public enum ObjectTypeEnum {
	USER(13001, "员工"),PRODUCT(13002, "产品"),  TRACE_BATCH(13003, "批次");

	public static ObjectTypeEnum getType(Integer codeTypeId) throws SuperCodeTraceException {
		if (null==codeTypeId) {
			return null;
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

	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ObjectTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
