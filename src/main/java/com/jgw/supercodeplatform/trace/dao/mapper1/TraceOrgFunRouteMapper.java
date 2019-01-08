package com.jgw.supercodeplatform.trace.dao.mapper1;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;

@Mapper
public interface TraceOrgFunRouteMapper extends CommonSql{
    
	@Insert(startScript
			+ " INSERT into trace_fun "
			+ " <trim prefix='(' suffix=')' suffixOverrides=','>"
				+ "<if test='organizationId != null and organizationId != &apos;&apos;'>OrganizationId,</if> "
				+ "<if test='functionId != null and functionId != &apos;&apos;'>FunctionId,</if> "
				+ "<if test='databaseAddress != null and databaseAddress !=  &apos;&apos; '>DatabaseAddress,</if> "
				+ "<if test='traceTemplateId != null and traceTemplateId !=  &apos;&apos; '>TraceTemplateId,</if> "
				+ "<if test='tableName != null and tableName !=  &apos;&apos; '>TableName</if> "
			+ "</trim>"
			+ "<trim prefix='values (' suffix=')' suffixOverrides=','>"
				+ "<if test='organizationId != null and organizationId != &apos;&apos;'>#{organizationId},</if> "
				+ "<if test='functionId != null and functionId != &apos;&apos;'>#{functionId},</if> "
				+ "<if test='databaseAddress != null and databaseAddress !=  &apos;&apos; '>#{databaseAddress},</if> "
				+ "<if test='traceTemplateId != null and traceTemplateId !=  &apos;&apos; '>#{traceTemplateId},</if> "
				+ "<if test='tableName != null and tableName !=  &apos;&apos; '>#{tableName}</if> "
			+ "</trim>"
			+ endScript)
	void insert(TraceOrgFunRoute tofr);

//	@Select("select Id id,OrganizationId organizationId,FunctionId functionId,DatabaseAddress databaseAddress,TableName tableName from trace_fun where FunctionId=#{functionId} limit 1")
//	TraceOrgFunRoute selectOneByFunctionId(@Param("functionId")String functionId);
	
	@Select(startScript
			+ "select Id id,OrganizationId organizationId,FunctionId functionId,DatabaseAddress databaseAddress,TableName tableName ,TraceTemplateId traceTemplateId from trace_fun "
			+startWhere
			+ "<if test='traceTemplateId != null and traceTemplateId !=  &apos;&apos; '>and TraceTemplateId=#{traceTemplateId}</if> "
			+ "<if test='traceTemplateId == null or traceTemplateId ==  &apos;&apos; '>and TraceTemplateId is null</if> "
			+ "<if test='functionId != null and functionId !=  &apos;&apos; '>and FunctionId=#{functionId}</if> "
			+endWhere
			+endScript
			)
	TraceOrgFunRoute selectByTraceTemplateIdAndFunctionId(@Param("traceTemplateId")String traceTemplateId, @Param("functionId")String functionId);

	@Delete("delete from trace_fun where FunctionId=#{nodeFunctionId} and TraceTemplateId=#{traceTemplateId}")
	void deleteByFunctionId(@Param("nodeFunctionId")String nodeFunctionId,@Param("traceTemplateId")String orgnazitionId);

	@Delete("delete from trace_fun where FunctionId in (${strFunctionIds}) and TraceTemplateId=#{traceTemplateId}")
	void deleteByFunctionIdsAndTemplateConfigId(@Param("strFunctionIds")String strFunctionIds, @Param("traceTemplateId")String templateConfigId);

}
