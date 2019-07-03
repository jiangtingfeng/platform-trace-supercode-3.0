package com.jgw.supercodeplatform.trace.dao.mapper1;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;

@Mapper
public interface TraceOrgFunRouteMapper extends CommonSql{
    static String allFields="Id id,OrganizationId organizationId,FunctionId functionId,DatabaseAddress databaseAddress,TableName tableName ,TraceTemplateId traceTemplateId";
	
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

	@Select(startScript
			+ "select "+allFields
			+ " from trace_fun "
			+startWhere
			+ "<if test='traceTemplateId != null and traceTemplateId !=  &apos;&apos; '>and TraceTemplateId=#{traceTemplateId}</if> "
			+ "<if test='traceTemplateId == null or traceTemplateId ==  &apos;&apos; '>and TraceTemplateId is null</if> "
			+ "<if test='functionId != null and functionId !=  &apos;&apos; '>and FunctionId=#{functionId}</if> "
			+endWhere
			+endScript
			)
	TraceOrgFunRoute selectByTraceTemplateIdAndFunctionId(@Param("traceTemplateId")String traceTemplateId, @Param("functionId")String functionId);

	@Select("select * from trace_fun WHERE  FunctionId=#{functionId}  order by Id desc limit 0,1 ")
    TraceOrgFunRoute selectByFunctionId(@Param("functionId")String functionId);

	@Delete("delete from trace_fun where FunctionId=#{nodeFunctionId} and TraceTemplateId=#{traceTemplateId}")
	void deleteByFunctionId(@Param("nodeFunctionId")String nodeFunctionId,@Param("traceTemplateId")String traceTemplateId);

	@Delete("delete from trace_fun where FunctionId in (${strFunctionIds}) and TraceTemplateId=#{traceTemplateId}")
	void deleteByFunctionIdsAndTemplateConfigId(@Param("strFunctionIds")String strFunctionIds, @Param("traceTemplateId")String templateConfigId);

	@Select("select "+allFields+" from trace_fun where FunctionId=#{functionId} and TraceTemplateId is not null")
	List<TraceOrgFunRoute> selectByFunctionIdWithTempIdIsNotNull(@Param("functionId")String functionId);

	@Delete("delete from trace_fun where FunctionId=#{functionId} and TraceTemplateId is null")
	void deleteByDzFunctionId(@Param("functionId")String functionId);

	@Delete("update trace_fun set TraceTemplateId=#{traceTemplateId} where FunctionId=#{functionId}")
	void deleteFun(@Param("functionId")String functionId, @Param("traceTemplateId")String templateId);

}
