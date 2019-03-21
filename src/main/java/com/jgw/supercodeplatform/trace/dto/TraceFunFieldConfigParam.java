package com.jgw.supercodeplatform.trace.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 溯源功能-配置字段表
 * @author czm
 *
 */
@ApiModel(value = "注意前面带**-字段，溯源功能-配置字段表，")
public class TraceFunFieldConfigParam implements Comparable<TraceFunFieldConfigParam>{
	@ApiModelProperty(value = "**-主键id--编辑功能或节点时必传")
    private Long id;  //主键id
	
	@ApiModelProperty(value = "**-动态生成的表名，在编辑时必传")
	private String enTableName;   //动态生成的表名
	
	@ApiModelProperty(value = "**-功能id，新增及编辑定制功能时必传，新增模板节点时使用节点功能id",notes="新增定制功能时必传，新增模板节点时使用节点功能id")
    private String functionId;  //功能ID号
    
	@ApiModelProperty(value = "**-功能名称，新增及编辑定制功能时必传，新增模板节点时使用节点功能名",notes="新增定制功能时必传，新增模板节点时使用节点功能名")
	private String functionName; //功能名称
	
	@NotEmpty
	@ApiModelProperty(value = "**-字段类型,1、表示文本，2、多行文本，3 表示单选 4、多选 5、表示金额，6 表示日期，7表示时间，8表示日期和时间，9 表示图片，10 表示附件，11 表示邮箱，12 表示网址，13_表示对象，14表示手机，15数字",notes="注意如果选择的是13对象类型传递的字段类型为13_具体类型，如：13_varchar(10)", required=true)
    private String fieldType;  //字段类型 1、表示文本，2、多行文本，3 表示单选 4、多选 5、表示金额，6 表示日期，7表示时间，8表示日期和时间，8表示手机，9 表示图片，10 表示附件，11 表示邮箱，12 表示网址，13 表示对象
	
	@NotNull
	@ApiModelProperty(value = "字段排序权重,数字越小排在前面",required=true)
	private Integer fieldWeight;//字段权重用于字段排序
	
	@NotEmpty
	@ApiModelProperty(value = "字段名称",required=true)
    private String fieldName;  //字段名称
	
	@NotEmpty
   	@ApiModelProperty(value = "字段英文名称",required=true)
    private String fieldCode;  //字段Code
	
	@ApiModelProperty(value = "选择的对象codeId",required=false)
	private String objectType;  //对象类型
	
    @NotNull
   	@ApiModelProperty(value = "1表示新增功能字段，2表示新增节点",required=true)
    private Integer typeClass;
    
    @ApiModelProperty(value = "默认值",required=false)
    private String defaultValue;  //默认值
    
    @ApiModelProperty(value = "是否必填,1必填，0非必填",example="1")
    private Integer isRequired;  //是否必填
    
    @ApiModelProperty(value = "验证格式")
    private String validateFormat;  //验证格式
    
    @ApiModelProperty(value = "最少长度")
    private Integer minSize;  //最少长度
    
    @ApiModelProperty(value = "**-除时间及金额其它类型默认为字符类型，字符类型必传--最大长度")
    private Integer maxSize;  //最多长度
    
    @ApiModelProperty(value = "默认张数")
    private Integer requiredNumber;  //默认张数
    
    @ApiModelProperty(value = "最少张数")
    private Integer minNumber;  //最少张数
    
    @ApiModelProperty(value = "最多张数")
    private Integer maxNumber;  //最多张数
    
    @ApiModelProperty(value = "选项值")
    private String dataValue;  //选项值
    
    @ApiModelProperty(value = "字段表id主键")
    private String objectFieldId;
    
    @ApiModelProperty(value = "启用")
    private Integer isRemarkEnable;  //启用说明
    
    @ApiModelProperty(value = "显示隐藏，1显示 0隐藏 不传默认显示")
    private Integer showHidden;  //显示隐藏

	public TraceFunFieldConfigParam(){}

	public TraceFunFieldConfigParam(String fieldCode,String fieldName,String fieldType, Integer fieldWeight,Integer isRequired,Integer maxSize,Integer minSize,Integer typeClass){
		this.fieldCode=fieldCode;
		this.fieldName=fieldName;
		this.fieldType=fieldType;
		this.fieldWeight=fieldWeight;
		this.isRequired=isRequired;
		this.maxSize=maxSize;
		this.minSize=minSize;
		this.typeClass=typeClass;

	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getFieldWeight() {
		return fieldWeight;
	}

	public String getObjectFieldId() {
		return objectFieldId;
	}

	public void setObjectFieldId(String objectFieldId) {
		this.objectFieldId = objectFieldId;
	}

	public void setFieldWeight(Integer fieldWeight) {
		this.fieldWeight = fieldWeight;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnTableName() {
		return enTableName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(Integer typeClass) {
		this.typeClass = typeClass;
	}

	public void setEnTableName(String enTableName) {
		this.enTableName = enTableName;
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

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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

	@Override
	public int compareTo(TraceFunFieldConfigParam o) {
		if (null==o ||null== o.getFieldWeight()) {
			return 1;
		}
		if (null==this.fieldWeight) {
			return 0;
		}
		if (this.fieldWeight>o.fieldWeight) {
			return 0;
		}
		return 1;
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
		   TraceFunFieldConfigParam rhs = (TraceFunFieldConfigParam) obj;
		   return new EqualsBuilder()
		   //这里调用父类的equals()方法，一般情况下不需要使用
		                 .appendSuper(super.equals(obj))
		                 .append("fieldCode", rhs.fieldCode)
		                 .append("fieldName", rhs.fieldName)
		                 .isEquals();
	}

}
