package com.jgw.supercodeplatform.trace.constants;

import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;

/**
 * 对象类型
 *
 * @author wzq
 * @date: 2019-03-28
 */
public enum ObjectTypeEnum {
	USER(13001,"UserId", "员工"),PRODUCT(13002, "ProductId","产品"),  TRACE_BATCH(13003,"TraceBatchInfoId", "产品批次"),

	MassifInfo(13012,"MassIfId","地块"),
	MassifBatch(13013,"TraceBatchId","地块批次"),
	/*RecoveryBatch(13014,"TraceBatchId","采收批次"),
	PurchaseBatch(13015,"TraceBatchId","收购批次"),
	SortingBatch(13016,"TraceBatchId","分拣批次"),
	PackingBatch(13017,"TraceBatchId","包装批次"),*/

	Device(13018,"DeviceId","设备"),
	CodeAssociate(13010,"AssociateType","码关联方式");

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

		case 13012:
			return MassifInfo;
		case 13013:
			return MassifBatch;
		/*case 13014:
			return RecoveryBatch;
		case 13015:
			return PurchaseBatch;
		case 13016:
			return SortingBatch;
		case 13017:
			return PackingBatch;*/

		case 13018:
			return Device;
		case 13010:
			return CodeAssociate;
		default:
			return null;
			//throw new SuperCodeTraceException("无法根据对象类型codeTypeId="+codeTypeId+"获取到对象", 500);
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
