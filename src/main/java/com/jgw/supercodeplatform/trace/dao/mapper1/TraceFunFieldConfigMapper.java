package com.jgw.supercodeplatform.trace.dao.mapper1;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.dao.sqlbuilder.TraceFunFieldConfigProvider;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigQueryParam;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

@Mapper
public interface TraceFunFieldConfigMapper extends CommonSql{
    String PARTFields="FieldType fieldType,ObjectFieldId objectFieldId,FieldCode fieldCode,ExtraCreate extraCreate,FieldName fieldName,EnTableName enTableName,ObjectType objectType";
    String ALLFields="Id id,FunctionId functionId,ObjectFieldId objectFieldId,ObjectType objectType,FunctionName functionName,ExtraCreate extraCreate,TraceTemplateId traceTemplateId,EnTableName enTableName,FieldType fieldType,FieldName fieldName,FieldWeight fieldWeight,FieldCode fieldCode,TypeClass typeClass,DefaultValue defaultValue,"
    		+ "IsRequired isRequired,ValidateFormat validateFormat,MinSize minSize,MaxSize maxSize,RequiredNumber requiredNumber,MinNumber minNumber,MaxNumber maxNumber,DataValue dataValue,IsRemarkEnable isRemarkEnable,ShowHidden showHidden,CreateBy createBy,CreateTime createTime,LastUpdateBy lastUpdateBy,LastUpdateTime lastUpdateTime";
    
	@InsertProvider(type = TraceFunFieldConfigProvider.class, method = "batchInsert")
	void batchInsert(@Param("list")List<TraceFunFieldConfig> tffcList);
	
	@Select("select EnTableName enTableName from trace_fun_config where FunctionId=#{functionId} limit 1")
	String getEnTableNameByFunctionId(@Param("functionId") String functionId);

	@Select("<script>"
			+ "select "+ALLFields+" from trace_fun_config where FunctionId=#{functionId}"
			     + "<if test='businessType !=null and businessType==1'> and TraceTemplateId = #{traceTemplateId} </if> "
				 + "and TypeClass=#{typeClass}"
				 + " order by FieldWeight"
			+"</script>"
			)
	List<TraceFunFieldConfig> selectNodeOrFunAllFields(TraceFunTemplateconfigQueryParam param);

	@Update({
		"<script>"
		   +"update trace_fun_config "
		   +"set FieldType ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.fieldType}"
				+"</foreach>"
				
				+",FieldName ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.fieldName}"
				+"</foreach>"
				
				+",FieldWeight ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.fieldWeight}"
				+"</foreach>"
				
				+",DefaultValue ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.defaultValue}"
				+"</foreach>"
				
				+",IsRequired ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.isRequired}"
				+"</foreach>"
				
				+",ValidateFormat ="
				+"<foreach collection='list' item='item' index='index' separator=' ' open='case Id' close='end'>"
				+"when #{item.id} then #{item.validateFormat}"
				+"</foreach>"	
			
			
			+" where Id in"
				+"<foreach collection='list' index='index' item='item' separator=',' open='(' close=')'>"
				+"#{item.id,jdbcType=BIGINT}"
				+"</foreach>"
		+"</script>"
	})
	void batchUpdate(@Param("list")List<TraceFunFieldConfig> update_tffcList);
    
	@Select("select "+PARTFields+" from trace_fun_config where FunctionId=#{functionId} and TypeClass=2")
	List<TraceFunFieldConfig> selectDZFPartFieldsByFunctionId(@Param("functionId")String functionId);
    
	@Select("<script>"
			+ "select "+PARTFields+" from trace_fun_config where FunctionId=#{functionId}"
			     + "<if test='businessType !=null'> and TraceTemplateId = #{traceTemplateId} </if> "
				 + "and TypeClass=#{typeClass}"
				 + " order by FieldWeight"
			+"</script>"
			)
	List<TraceFunFieldConfig> selectPartNodeOrFunFields(TraceFunTemplateconfigQueryParam param);
    
	@Delete("delete from trace_fun_config where TraceTemplateId = #{traceTemplateId} and FunctionId=#{functionId}")
	void deleteByTraceTemplateIdAndFunctionId(@Param("traceTemplateId")String traceTemplateId, @Param("functionId")String functionId);
    
	@Delete("delete from trace_fun_config where TraceTemplateId = #{traceTemplateId} and FunctionId in (${strFunctionIds})")
	void deleteByTraceTemplateIdAndFunctionIds(@Param("traceTemplateId")String templateConfigId, @Param("strFunctionIds")String strFunctionIds);

}
