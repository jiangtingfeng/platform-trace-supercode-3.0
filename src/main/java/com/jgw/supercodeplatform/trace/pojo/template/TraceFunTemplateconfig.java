package com.jgw.supercodeplatform.trace.pojo.template;

import java.io.Serializable;

/**
 * 追溯功能模板配置表
 * @author czm
 *
 */
public class TraceFunTemplateconfig implements Serializable{

	private static final long serialVersionUID = -5307297682287665966L;
	
	private Integer id;  //序列ID
    private String traceTemplateId;      //溯源模板号
    private String businessTypes;        //业务类型 1、自动节点 2、手动节点 3、默认节点
    private String nodeFunctionId;           //功能ID号
    private String organizationId;
    private String nodeFunctionName;         //功能名称
    private Integer nodeWeight;          //节点顺序
    private String createTime;//创建时间
	private String updateTime;//修改时间

    public TraceFunTemplateconfig() {
    }

    public TraceFunTemplateconfig(Integer id, String traceTemplateId, String businessTypes, String nodeFunctionId, String nodeFunctionName, Integer nodeWeight, String createTime, String updateTime) {
        this.id = id;
        this.traceTemplateId = traceTemplateId;
        this.businessTypes = businessTypes;
        this.nodeFunctionId = nodeFunctionId;
        this.nodeWeight = nodeWeight;
        this.createTime = createTime;
        this.nodeFunctionName=nodeFunctionName;
        this.updateTime = updateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTraceTemplateId() {
        return traceTemplateId;
    }

    public void setTraceTemplateId(String traceTemplateId) {
        this.traceTemplateId = traceTemplateId;
    }

    public String getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(String businessTypes) {
        this.businessTypes = businessTypes;
    }

    public String getNodeFunctionId() {
        return nodeFunctionId;
    }

    public void setNodeFunctionId(String nodeFunctionId) {
        this.nodeFunctionId = nodeFunctionId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getNodeFunctionName() {
		return nodeFunctionName;
	}

	public void setNodeFunctionName(String nodeFunctionName) {
		this.nodeFunctionName = nodeFunctionName;
	}

	public Integer getNodeWeight() {
        return nodeWeight;
    }

    public void setNodeWeight(Integer nodeWeight) {
        this.nodeWeight = nodeWeight;
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
}
