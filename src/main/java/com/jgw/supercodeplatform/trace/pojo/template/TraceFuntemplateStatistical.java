package com.jgw.supercodeplatform.trace.pojo.template;

import java.io.Serializable;

/**
 * 溯源模板统计类
 * @author czm
 *
 */
public class TraceFuntemplateStatistical implements Serializable{

	private static final long serialVersionUID = -9066661547769828605L;
	
	private Long id;
	private String traceTemplateId;//溯源模板号
	private String traceTemplateName;//溯源模板名称
	private String organizationId;//组织id
	private Integer nodeCount;//节点数
	private Integer batchCount;
	private String productCount;//产品数量
	private String createId;//创建人id
	private String createMan;//创建人
	private String createTime;//创建时间
	private String updateTime;//修改时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTraceTemplateId() {
		return traceTemplateId;
	}
	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}
	public String getTraceTemplateName() {
		return traceTemplateName;
	}
	public void setTraceTemplateName(String traceTemplateName) {
		this.traceTemplateName = traceTemplateName;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getNodeCount() {
		return nodeCount;
	}
	public void setNodeCount(Integer nodeCount) {
		this.nodeCount = nodeCount;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
}
