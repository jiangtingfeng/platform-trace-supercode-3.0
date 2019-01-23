package com.jgw.supercodeplatform.trace.dto.template.query;

import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 溯源功能-查询追溯模板
 */
@ApiModel(value = "溯源功能-查询追溯模板")
public class TraceFunTemplateconfigListParam extends DaoSearch {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "溯源模板号")
    private String traceTemplateId;//溯源模板号
    @ApiModelProperty(value = "溯源模板名称")
    private String traceTemplateName;//溯源模板名称
    @ApiModelProperty(value = "组织id",hidden = true)
    private String organizationId;//组织id
    @ApiModelProperty(value = "节点数量")
    private Integer nodeCount;//节点数
    @ApiModelProperty(value = "使用批次数量")
    private String traceBatchNum;//使用批次数量
    @ApiModelProperty(value = "创建人")
    private String createMan;//创建人
    @ApiModelProperty(value = "创建时间")
    private String createTime;//创建时间
    @ApiModelProperty(value = "业务类型")
    private String businessType;        //业务类型 1、自动节点 2、手动节点 3、默认节点
    @ApiModelProperty(value = "功能ID号")
    private String nodeFunctionId;           //功能ID号
    @ApiModelProperty(value = "功能名称")
    private String nodeFunctionName;         //功能名称
    @ApiModelProperty(value = "节点顺序")
    private Integer nodeWeight;          //节点顺序

    public TraceFunTemplateconfigListParam() {
    }

    public TraceFunTemplateconfigListParam(Long id, String traceTemplateId, String traceTemplateName, String organizationId, Integer nodeCount, String traceBatchNum, String createMan, String createTime, String businessType, String nodeFunctionId, String nodeFunctionName, Integer nodeWeight) {
        this.id = id;
        this.traceTemplateId = traceTemplateId;
        this.traceTemplateName = traceTemplateName;
        this.organizationId = organizationId;
        this.nodeCount = nodeCount;
        this.traceBatchNum = traceBatchNum;
        this.createMan = createMan;
        this.createTime = createTime;
        this.businessType = businessType;
        this.nodeFunctionId = nodeFunctionId;
        this.nodeFunctionName = nodeFunctionName;
        this.nodeWeight = nodeWeight;
    }

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

    public String getTraceBatchNum() {
        return traceBatchNum;
    }

    public void setTraceBatchNum(String traceBatchNum) {
        this.traceBatchNum = traceBatchNum;
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

    public String getNodeFunctionId() {
        return nodeFunctionId;
    }

    public void setNodeFunctionId(String nodeFunctionId) {
        this.nodeFunctionId = nodeFunctionId;
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
}
