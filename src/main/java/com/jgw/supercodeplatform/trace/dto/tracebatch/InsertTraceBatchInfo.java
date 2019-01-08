package com.jgw.supercodeplatform.trace.dto.tracebatch;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增时swagger溯源批次实体类
 * @author liujianqiang
 * @date 2018年12月12日
 */
@ApiModel(value = "新增溯源批次实体类")
public class InsertTraceBatchInfo implements Serializable{

	private static final long serialVersionUID = 8234608197205452547L;
	
	@ApiModelProperty(name = "productId", value = "产品id", example = "0067a46e580e4b6cbc79b8c55576f617", required = true)
	private String productId;//产品id
	
	@ApiModelProperty(name = "productName", value = "产品名称", example = "苹果", required = true)
	private String productName;//产品名称
	
	@ApiModelProperty(name = "traceBatchId", value = "批次号", example = "13124", required = false)
	private String traceBatchId;//批次号
	
	@ApiModelProperty(name = "traceBatchName", value = "批次名称", example = "农垦58号2018003", required = true)
	private String traceBatchName;//批次名称
	
	@ApiModelProperty(name = "listedTime", value = "上市时间", example = "2018-10-11", required = false)
	private String listedTime;//上市时间
	
	@ApiModelProperty(name = "traceTemplateId", value = "溯源模板号", example = "0067a46e580e4b6cbc79b8c55576f617", required = true)
	private String traceTemplateId;//溯源模板号
	
	@ApiModelProperty(name = "traceTemplateName", value = "溯源模板名称", example = "溯源模板1", required = true)
	private String traceTemplateName;//溯源模板名称
	
	@ApiModelProperty(name = "h5TrancePageId", value = "H5溯源页模板Id号", example = "0067a46e580e4b6cbc79b8c55576f617", required = true)
	private String h5TrancePageId;//H5溯源页模板Id号
	
	@ApiModelProperty(name = "h5TempleteName", value = "H5溯源页模板名称", example = "模板1", required = true)
	private String h5TempleteName;//H5溯源页模板名称
	
	@ApiModelProperty(name = "productBarcode", value = "产品条形码", example = "2018113001", required = false)
	private String productBarcode;//产品条形码
	
	@ApiModelProperty(name = "productSpecificationsCode", value = "产品规格", example = "10斤/箱", required = true)
	private String productSpecificationsCode;//产品条规格
	
	@ApiModelProperty(name = "productModel", value = "产品型号", example = "一级", required = true)
	private String productModel;//产品型号
	
	@ApiModelProperty(name = "productUrl", value = "产品图片url,格式前端定义", example = "djiui21sdjf", required = true)
	private String productUrl;//产品图片url
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTraceBatchName() {
		return traceBatchName;
	}
	public void setTraceBatchName(String traceBatchName) {
		this.traceBatchName = traceBatchName;
	}
	public String getTraceBatchId() {
		return traceBatchId;
	}
	public void setTraceBatchId(String traceBatchId) {
		this.traceBatchId = traceBatchId;
	}
	public String getListedTime() {
		return listedTime;
	}
	public void setListedTime(String listedTime) {
		this.listedTime = listedTime;
	}
	public String getTraceTemplateId() {
		return traceTemplateId;
	}
	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}
	public String getH5TrancePageId() {
		return h5TrancePageId;
	}
	public void setH5TrancePageId(String h5TrancePageId) {
		this.h5TrancePageId = h5TrancePageId;
	}
	public String getTraceTemplateName() {
		return traceTemplateName;
	}
	public void setTraceTemplateName(String traceTemplateName) {
		this.traceTemplateName = traceTemplateName;
	}
	public String getH5TempleteName() {
		return h5TempleteName;
	}
	public void setH5TempleteName(String h5TempleteName) {
		this.h5TempleteName = h5TempleteName;
	}
	public String getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}
	public String getProductSpecificationsCode() {
		return productSpecificationsCode;
	}
	public void setProductSpecificationsCode(String productSpecificationsCode) {
		this.productSpecificationsCode = productSpecificationsCode;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	
}
