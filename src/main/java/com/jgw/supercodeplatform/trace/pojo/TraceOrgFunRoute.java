package com.jgw.supercodeplatform.trace.pojo;

/**
 * 企业功能路由关系表
 * @author czm
 *
 */
public class TraceOrgFunRoute {

    private Integer id;  //序列Id
    private String organizationId;  //组织Id
    private String functionId;  //功能ID号(节点Id)
    private String databaseAddress;  //数据库地址
    private String tableName;  //表名
	private String traceTemplateId;      //溯源模板号
    public TraceOrgFunRoute() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public String getTraceTemplateId() {
		return traceTemplateId;
	}

	public void setTraceTemplateId(String traceTemplateId) {
		this.traceTemplateId = traceTemplateId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getDatabaseAddress() {
		return databaseAddress;
	}

	public void setDatabaseAddress(String databaseAddress) {
		this.databaseAddress = databaseAddress;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
