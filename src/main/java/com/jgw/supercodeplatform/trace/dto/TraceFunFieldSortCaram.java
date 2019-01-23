package com.jgw.supercodeplatform.trace.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 溯源功能-配置字段表
 * @author czm
 *
 */
@ApiModel(value = "字段排序更新模板类")
public class TraceFunFieldSortCaram{
	@NotNull
	@ApiModelProperty(value = "**-主键id",required=true)
    private Long id;  //主键id
	
	@NotNull
	@ApiModelProperty(value = "字段排序权重,数字越小排在前面",required=true)
	private Integer fieldWeight;//字段权重用于字段排序

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFieldWeight() {
		return fieldWeight;
	}

	public void setFieldWeight(Integer fieldWeight) {
		this.fieldWeight = fieldWeight;
	}
	
	
	
}
