package com.jgw.supercodeplatform.trace.common.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public 	class Field implements Comparable<Field>{
	String fieldCode;
	String fieldName;
	String fieldType;
	Object fieldValue;
	Integer typeClass;
    private Integer fieldWeight;//字段权重用于字段排序
    
	public Integer getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(Integer typeClass) {
		this.typeClass = typeClass;
	}
	public Integer getFieldWeight() {
		return fieldWeight;
	}
	public void setFieldWeight(Integer fieldWeight) {
		this.fieldWeight = fieldWeight;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldName() {
		return fieldName;
	}
	
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public int compareTo(Field o) {
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
		   Field rhs = (Field) obj;
		   return new EqualsBuilder()
		   //这里调用父类的equals()方法，一般情况下不需要使用
		                 .appendSuper(super.equals(obj))
		                 .append("fieldCode", rhs.fieldCode)
		                 .append("fieldName", rhs.fieldName)
		                 .isEquals();
	}

}
