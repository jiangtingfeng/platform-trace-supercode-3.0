package com.jgw.supercodeplatform.trace.dao.mapper1.template;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFuntemplateStatistical;

@Mapper
public interface TraceFuntemplateStatisticalMapper extends CommonSql{
     static String allFields="a.Id id,a.TraceTemplateId traceTemplateId,a.TraceTemplateName traceTemplateName,"
     		+ "if(a.NodeCount IS NULL,0,a.NodeCount) nodeCount,if(a.BatchCount IS NULL ,0,a.BatchCount) batchCount,"
     		+ "a.ProductCount productCount,a.CreateId createId,a.CreateMan createMan,a.OrganizationId organizationId"
     		+ createTime + updateTime;
     
	@Insert(startScript
			+ " INSERT into trace_funtemplatestatistical "
			+ " <trim prefix='(' suffix=')' suffixOverrides=','>"
				+ "<if test='traceTemplateId != null and traceTemplateId != &apos;&apos;'>TraceTemplateId,</if> "
				+ "<if test='traceTemplateName != null and traceTemplateName !=  &apos;&apos; '>TraceTemplateName,</if> "
				+ "<if test='nodeCount != null '>NodeCount,</if> "
				+ "<if test='createMan != null and createMan != &apos;&apos;'>CreateMan,</if> "
				+ "<if test='organizationId != null and organizationId != &apos;&apos;'>OrganizationId,</if> "
				+" CreateTime,"
				+" UpdateTime"
			+ "</trim>"
			+ "<trim prefix='values (' suffix=')' suffixOverrides=','>"
				+ "<if test='traceTemplateId != null and traceTemplateId != &apos;&apos;'>#{traceTemplateId},</if> "
				+ "<if test='traceTemplateName != null and traceTemplateName !=  &apos;&apos; '>#{traceTemplateName},</if> "
				+ "<if test='nodeCount != null '>#{nodeCount},</if> "
				+ "<if test='createMan != null and createMan != &apos;&apos;'>#{createMan},</if> "
				+ "<if test='organizationId != null and organizationId != &apos;&apos;'>#{organizationId},</if> "
				+"now(),"
				+"now()"
			+ "</trim>"
			+ endScript)
	void insert(TraceFuntemplateStatistical traceFuntemplateStatistical);
    
	@Select("select "+allFields+" from trace_funtemplatestatistical a where a.TraceTemplateId=#{templateId}")
	TraceFuntemplateStatistical selectByTemplateId(@Param("templateId")String templateId);

	/**
	 * 修改模板统计数据
	 * @author liujianqiang
	 * @data 2018年12月20日
	 * @param traceFuntemplateStatistical
	 */
	@Update(" <script>"
			+ "update trace_funtemplatestatistical "
			+ "<set> "
				+ "<if test='traceTemplateName != null and traceTemplateName !=  &apos;&apos; '>TraceTemplateName=#{traceTemplateName},</if> "
				+ "<if test='nodeCount != null '> NodeCount=#{nodeCount}, </if> "
				+ "<if test='batchCount != null '> BatchCount=#{batchCount}, </if> "
				+ "<if test='createMan != null and createMan != &apos;&apos;'>CreateMan=#{createMan},</if> "
				+"UpdateTime=now()"
			+ "</set>"
			+ "where TraceTemplateId=#{traceTemplateId}"
			+ "</script>"
			)
	Integer update(TraceFuntemplateStatistical traceFuntemplateStatistical);
    
	@Delete("delete from trace_funtemplatestatistical where Id=#{id}")
	void deleteById(@Param("id")Long id);

	@Select("select count(*) from trace_funtemplatestatistical where TraceTemplateName= #{templateName} and TraceTemplateId !=#{traceTemplateId}")
	Integer countOtherTemplateNameByTemplateId(@Param("templateName")String templateConfigName, @Param("traceTemplateId")String templateConfigId);
}