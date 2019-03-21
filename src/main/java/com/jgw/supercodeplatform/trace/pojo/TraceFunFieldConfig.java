package com.jgw.supercodeplatform.trace.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * 溯源功能-配置字段表
 * @author czm
 *
 */
public class TraceFunFieldConfig implements Comparable<TraceFunFieldConfig>{
    private Long id;  //序列id
    private String enTableName;   //动态生成的表名
    private String functionId;  //功能ID号
    private String FunctionName; //功能名称
    private String fieldType;  // 字段类型,1、表示文本，2、多行文本，3 表示单选 4、多选 5、表示金额，6 表示日期，7表示时间，8表示日期和时间，9 表示图片，10 表示附件，11 表示邮箱，12 表示网址，13 表示对象，14表示手机，15数字
    private String fieldName;  //字段名称
    private String fieldCode;  //字段Code
    private Integer fieldWeight;//字段权重用于字段排序
    private String defaultValue;  //默认值
    private String traceTemplateId;      //溯源模板号---定制功能节点业务数据放在一张定制功能表里
    private String objectType;  //对象类型
    private Integer typeClass;   //1功能2节点
    private Integer isRequired;  //是否必填
    private String validateFormat;  //验证格式
    private Integer minSize;  //最少长度
    private Integer maxSize;  //最多长度
    private Integer requiredNumber;  //默认张数
    private Integer minNumber;  //最少张数
    private Integer maxNumber;  //最多张数
    private String dataValue;  //选项值
    private Integer isRemarkEnable;  //启用说明
    private Integer showHidden;  //显示隐藏
    private String createBy;  //创建人
    private Integer extraCreate;  //是否是后台额外新增的字段而不是页面创建的 1表示是
    private String createTime;  //创建时间
    private String lastUpdateBy;  //修改人
    private String lastUpdateTime;  //修改时间
    private String objectFieldId;//对象字段表主键id
	private String componentId;

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}


    
	public String getObjectFieldId() {
		return objectFieldId;
	}

	public void setObjectFieldId(String objectFieldId) {
		this.objectFieldId = objectFieldId;
	}

	/**
     * 不存数据库
     * 对象属性业务数据请求地址
     */
    private String serviceAddress;
    
    public TraceFunFieldConfig() {
    }

	public Long getId() {
		return id;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFunctionId() {
		return functionId;
	}

	public Integer getTypeClass() {
		return typeClass;
	}

	public Integer getExtraCreate() {
		return extraCreate;
	}

	public void setExtraCreate(Integer extraCreate) {
		this.extraCreate = extraCreate;
	}

	public void setTypeClass(Integer typeClass) {
		this.typeClass = typeClass;
	}

	public String getFunctionName() {
		return FunctionName;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}

	public Integer getFieldWeight() {
		return fieldWeight;
	}

	public void setFieldWeight(Integer fieldWeight) {
		this.fieldWeight = fieldWeight;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getEnTableName() {
		return enTableName;
	}

	public void setEnTableName(String enTableName) {
		this.enTableName = enTableName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	public String getValidateFormat() {
		return validateFormat;
	}

	public void setValidateFormat(String validateFormat) {
		this.validateFormat = validateFormat;
	}

	public Integer getMinSize() {
		return minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getRequiredNumber() {
		return requiredNumber;
	}

	public void setRequiredNumber(Integer requiredNumber) {
		this.requiredNumber = requiredNumber;
	}

	public Integer getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Integer minNumber) {
		this.minNumber = minNumber;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public Integer getIsRemarkEnable() {
		return isRemarkEnable;
	}

	public void setIsRemarkEnable(Integer isRemarkEnable) {
		this.isRemarkEnable = isRemarkEnable;
	}

	public Integer getShowHidden() {
		return showHidden;
	}

	public void setShowHidden(Integer showHidden) {
		this.showHidden = showHidden;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public int compareTo(TraceFunFieldConfig o) {
		if (o==null || null==o.getFieldWeight()) {
			return 1;
		}
		if (null==this.fieldWeight) {
			return 0;
		}
		if (this.fieldWeight<o.getFieldWeight()) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
	     return new HashCodeBuilder(17, 37).
	       append(fieldCode).
	       append(fieldName).
	       toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false;}
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   TraceFunFieldConfig rhs = (TraceFunFieldConfig) obj;
		   return new EqualsBuilder()
		   //这里调用父类的equals()方法，一般情况下不需要使用
		                 .appendSuper(super.equals(obj))
		                 .append("fieldCode", rhs.fieldCode)
		                 .append("fieldName", rhs.fieldName)
		                 .isEquals();
	}

}
