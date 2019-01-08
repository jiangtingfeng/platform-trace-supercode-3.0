package com.jgw.supercodeplatform.trace.dao.mapper1.template;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigDeleteParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigListParam;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.vo.TraceFunTemplateconfigVO;

@Mapper
public interface TraceFunTemplateconfigMapper extends CommonSql{
	static String allFields="Id id,TraceTemplateId traceTemplateId,NodeWeight nodeWeight,"
			              + "BusinessTypes businessTypes,NodeFunctionId nodeFunctionId,NodeFunctionName nodeFunctionName,"
			              + "CreateTime createTime,UpdateTime updateTime";
	
	static String allFieldsWithPrefix="t.Id id,t.TraceTemplateId traceTemplateId,t.NodeWeight nodeWeight,"
            + "t.BusinessTypes businessTypes,t.NodeFunctionId nodeFunctionId,t.NodeFunctionName nodeFunctionName,"
            + "t.CreateTime createTime,t.UpdateTime updateTime";

	static String whereSelectTraceFunTemplate =
			"<where>" +
					"<choose>" +
					//高级搜索
					"<when test='daoSearch.flag != null and daoSearch.flag == 0'>" +
					"<if test='daoSearch.params.traceTemplateName != null and daoSearch.params.traceTemplateName != &apos;&apos;'> AND  trace_funtemplatestatistical.TraceTemplateName like &apos;%${daoSearch.params.traceTemplateName}%&apos; </if>" +
					"<if test='daoSearch.params.nodeCount != null and daoSearch.params.nodeCount != &apos;&apos;'> AND trace_funtemplatestatistical.nodeCount like &apos;%${daoSearch.params.nodeCount}%&apos; </if>" +
					"<if test='daoSearch.params.traceBatchId != null and daoSearch.params.traceBatchId != &apos;&apos;'> AND trace_funtemplatestatistical.traceBatchId like &apos;%${daoSearch.params.traceBatchId}%&apos; </if>" +
					"<if test='daoSearch.params.createMan != null and daoSearch.params.createMan != &apos;&apos;'> AND trace_funtemplatestatistical.createMan like &apos;%${daoSearch.params.createMan}%&apos; </if>" +
					"</when>" +
					//普通搜索
					"<when test='daoSearch.flag != null and daoSearch.flag == 1'>" +
					"<if test='daoSearch.search !=null and daoSearch.search != &apos;&apos;'>" +
					" AND (" +
					" trace_funtemplatestatistical.TraceTemplateName LIKE CONCAT('%',#{daoSearch.search},'%')  " +
					" OR NodeCount LIKE CONCAT('%',#{daoSearch.search},'%') " +
					" OR TraceBatchId LIKE CONCAT('%',#{daoSearch.search},'%') " +
					" OR trace_funtemplatestatistical.CreateMan LIKE CONCAT('%',#{daoSearch.search},'%')" +
					")" +
					"</if>" +
					"</when>" +
					//其他，则为默认所有，不进行筛选
					"<otherwise>" +
					"</otherwise>" +
					"</choose>" +
					"<if test='organizationId !=null and organizationId != &apos;&apos; '> AND trace_funtemplatestatistical.organizationId = #{organizationId}</if>"+
					"</where>";

	static String AllFieldsWithTemplate = "trace_fun_templateconfig.Id id,trace_funtemplatestatistical.TraceTemplateId traceTemplateId,trace_funtemplatestatistical.TraceTemplateName traceTemplateName,NodeCount nodeCount,SUM(TraceBatchId) AS traceBatchNum,trace_funtemplatestatistical.CreateMan createMan,DATE_FORMAT(trace_funtemplatestatistical.CreateTime,'%Y-%m-%d %H:%i:%S') createTime,NodeWeight nodeWeight,BusinessTypes businessTypes, NodeFunctionId nodeFunctionId,NodeFunctionName nodeFunctionName ";

	static String TableWithTemplate = "trace_funtemplatestatistical left join trace_batchinfo on trace_funtemplatestatistical.TraceTemplateId=trace_batchinfo.TraceTemplateId left join trace_fun_templateconfig on trace_funtemplatestatistical.TraceTemplateId=trace_fun_templateconfig.TraceTemplateId";


	@Insert(startScript
			+ " INSERT into trace_fun_templateconfig "
			+ " <trim prefix='(' suffix=')' suffixOverrides=','>"
				+ "<if test='traceTemplateId != null and traceTemplateId != &apos;&apos;'>TraceTemplateId,</if> "
				+ "<if test='businessTypes != null and businessTypes !=  &apos;&apos; '>BusinessTypes,</if> "
				+ "<if test='nodeFunctionId != null and nodeFunctionId != &apos;&apos;'>NodeFunctionId,</if> "
				+ "<if test='nodeWeight != null and nodeWeight != &apos;&apos;'>NodeWeight,</if> "
				+ "<if test='nodeFunctionName != null and nodeFunctionName != &apos;&apos;'>NodeFunctionName,</if> "
				+" CreateTime,"
				+" UpdateTime"
			+ "</trim>"
				+ "<trim prefix='values (' suffix=')' suffixOverrides=','>"
				+ "<if test='traceTemplateId != null and traceTemplateId != &apos;&apos;'>#{traceTemplateId},</if> "
				+ "<if test='businessTypes != null and businessTypes !=  &apos;&apos; '>#{businessTypes},</if> "
				+ "<if test='nodeFunctionId != null and nodeFunctionId != &apos;&apos;'>#{nodeFunctionId},</if> "
				+ "<if test='nodeWeight != null and nodeWeight != &apos;&apos;'>#{nodeWeight},</if> "
				+ "<if test='nodeFunctionName != null and nodeFunctionName != &apos;&apos;'>#{nodeFunctionName},</if> "
				+"now(),"
				+"now()"
			+ "</trim>"
			+ endScript)
	void insert(TraceFunTemplateconfig tftc);
    
	@Delete("delete from trace_fun_templateconfig where TraceTemplateId=#{traceTemplateId} and NodeFunctionId in (${strFunctionIds})")
	int deleteByTemplateIdAndFunctionIds(@Param("traceTemplateId")String traceTemplateId,@Param("strFunctionIds")String strFunctionIds);

	@Select("select "+allFieldsWithPrefix+",ts.TraceTemplateName from trace_fun_templateconfig t left join trace_funtemplatestatistical ts on t.TraceTemplateId=ts.TraceTemplateId where t.TraceTemplateId=#{templateConfigId}")
	List<TraceFunTemplateconfigVO> selectByTemplateId(@Param("templateConfigId")String templateConfigId);

	@Update("<script>"+
			"update trace_fun_templateconfig"
				+ "<set>"
					+ "<if test='traceTemplateId != null and traceTemplateId != &apos;&apos;'>TraceTemplateId=#{traceTemplateId},</if> "
					+ "<if test='businessTypes != null and businessTypes !=  &apos;&apos; '>BusinessTypes=#{businessTypes},</if> "
					+ "<if test='nodeFunctionId != null and nodeFunctionId != &apos;&apos;'>NodeFunctionId=#{nodeFunctionId},</if> "
					+ "<if test='nodeFunctionName != null and nodeFunctionName != &apos;&apos;'>NodeFunctionName=#{nodeFunctionName},</if> "
					+ "<if test='nodeWeight != null and nodeWeight != &apos;&apos;'>NodeWeight=#{nodeWeight},</if> "
					+"UpdateTime=now()"
				+ "</set>"
				+ "where Id=#{id}"
				+" </script>"
			)
	void update(TraceFunTemplateconfig templateConfigList);


	/**
	 * 普通搜索和高级搜索
	 * @param daoSearch
	 * @param organizationId
	 * @return
	 */
	@Select(" <script>"
			+ " select "+AllFieldsWithTemplate+"  from "
			+ TableWithTemplate
			+ whereSelectTraceFunTemplate
			+ "  GROUP BY trace_funtemplatestatistical.TraceTemplateId"
			+ " <if test='daoSearch.startNumber != null and daoSearch.pageSize != null and daoSearch.pageSize != 0'> LIMIT #{daoSearch.startNumber},#{daoSearch.pageSize}</if>"
			+ " </script>")
	List<TraceFunTemplateconfigListParam> selectTemplateReturnDataTypeByTemplateId(@Param("daoSearch")DaoSearch daoSearch,@Param("organizationId")String organizationId);

	@Select(" <script>"
			+ "SELECT count(1) FROM( SELECT count(1) FROM  "
			+ TableWithTemplate
			+ whereSelectTraceFunTemplate
			+ "  GROUP BY trace_funtemplatestatistical.TraceTemplateId"
			+ ")T </script>")
	int count(@Param("daoSearch") DaoSearch daoSearch, @Param("organizationId")String organizationId);

	/**
	 * 不分页查询模板
	 * @param organizationId
	 * @return
	 */
	@Select(" select "+AllFieldsWithTemplate+" from "+TableWithTemplate+" where trace_funtemplatestatistical.organizationId = #{organizationId}  GROUP BY trace_funtemplatestatistical.TraceTemplateId ")
	List<TraceFunTemplateconfigListParam> selectTemplateByTemplateId(@Param("organizationId")String organizationId);

	/**
	 * 普通搜索只根据模板名称
	 * @param daoSearch
	 * @param organizationId
	 * @return
	 */
	@Select(" <script>"
			+ " select "+AllFieldsWithTemplate+"  from "
			+ TableWithTemplate
			+ "<where><if test='daoSearch.search !=null and daoSearch.search != &apos;&apos;'>trace_funtemplatestatistical.TraceTemplateName LIKE CONCAT('%',#{daoSearch.search},'%')</if>"
			+ "<if test='organizationId !=null and organizationId != &apos;&apos; '> AND trace_funtemplatestatistical.organizationId = #{organizationId}</if></where>"
			+ "  GROUP BY trace_funtemplatestatistical.TraceTemplateId"
			+ " <if test='daoSearch.startNumber != null and daoSearch.pageSize != null and daoSearch.pageSize != 0'> LIMIT #{daoSearch.startNumber},#{daoSearch.pageSize}</if>"
			+ " </script>")
	List<TraceFunTemplateconfigListParam> selectTemplateByTemplateName(@Param("daoSearch") DaoSearch daoSearch, @Param("organizationId")String organizationId);

	@Select(" <script>"
			+ "SELECT count(1) FROM( SELECT count(1) FROM  "
			+ TableWithTemplate
			+ "<where><if test='daoSearch.search !=null and daoSearch.search != &apos;&apos;'>trace_funtemplatestatistical.TraceTemplateName LIKE CONCAT('%',#{daoSearch.search},'%')</if>"
			+ "<if test='organizationId !=null and organizationId != &apos;&apos; '> AND trace_funtemplatestatistical.organizationId = #{organizationId}</if></where>"
			+ "  GROUP BY trace_funtemplatestatistical.TraceTemplateId"
			+ ")T </script>")
	int countByTemplateName(@Param("daoSearch") DaoSearch daoSearch, @Param("organizationId")String organizationId);

	@Select("select "+allFields+" from trace_fun_templateconfig where NodeFunctionId=#{nodeFunctionId}")
	TraceFunTemplateconfig selectByNodeFunctionId(@Param("nodeFunctionId")String nodeFunctionId);

	@Select("select "+allFieldsWithPrefix+" from trace_fun_templateconfig t left join trace_funtemplatestatistical tc on t.TraceTemplateId=tc.TraceTemplateId where tc.OrganizationId= #{organizationId} and tc.TraceTemplateName= #{templateName}")
	List<TraceFunTemplateconfig> selectByTempNameAndOrgId(@Param("templateName")String templateName, @Param("organizationId")String organizationId);
    /**
     * 根据模板id和节点id删除节点
     * @param param
     * @return
     */
	@Delete("delete from trace_fun_templateconfig where  NodeFunctionId =#{nodeFunctionId} and TraceTemplateId=#{traceTemplateId}")
	int deleteByTemplateIdAndFunctionId(TraceFunTemplateconfigDeleteParam param);

    /**
     * 根据模板id查询模板数据及对应的字段数据
     * @param traceTemplateId
     * @return
     */
	@Select("select "+allFieldsWithPrefix+",tc.EnTableName enTableName from trace_fun_templateconfig t left join trace_fun_config tc on t.NodeFunctionId=tc.FunctionId where t.TraceTemplateId=#{traceTemplateId} GROUP BY t.NodeFunctionId ORDER BY t.NodeWeight asc")
	List<TraceFunTemplateconfigVO> getTemplateAndFieldInfoByTemplateId(@Param("traceTemplateId")String traceTemplateId);
	
	@Delete("delete from trace_fun_templateconfig where  Id =#{id}")
	int deleteById(Long id);
    
	@Select("select "+allFields+" from trace_fun_templateconfig where Id =#{id}")
	TraceFunTemplateconfig selectById(Long id);

}