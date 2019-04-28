package com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.ReturnTraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;

/**
 * 溯源批次mapper
 * @author liujianqiang
 * @date 2018年12月12日
 */
@Mapper
public interface TraceBatchInfoMapper extends CommonSql{
	
	String selectSql = " SELECT a.TraceBatchInfoId traceBatchInfoId,a.OrganizationId organizationId,a.ProductID productId,"
			+ "a.ProductName productName,a.TraceBatchName traceBatchName,a.TraceTemplateName traceTemplateName,"
			+ "a.TraceBatchId traceBatchId,a.ListedTime listedTime,a.TraceTemplateId traceTemplateId,"
			+"a.NodeDataCount nodeDataCount,"
			+ "a.H5TrancePageId h5TrancePageId,a.H5TempleteName h5TempleteName,a.CreateId createId,a.CreateMan createMan, a.TraceBatchPlatformId traceBatchPlatformId"
			+ createTime + updateTime ;
	
	/**
	 * 新增溯源批次
	 * @author liujianqiang
	 * @data 2018年12月12日
	 * @param traceBatchInfo
	 * @return
	 */
	@Insert(" INSERT INTO trace_batchinfo"
			+ "(TraceBatchInfoId,OrganizationId,ProductID,ProductName,TraceBatchName,"
			+ "TraceBatchId,ListedTime,TraceTemplateId,TraceTemplateName,H5TrancePageId,"
			+ "H5TempleteName,CreateId,CreateMan,NodeDataCount, SysId)"
			+ "VALUES"
			+ "(#{traceBatchInfoId},#{organizationId},#{productId},#{productName},#{traceBatchName},"
			+ "#{traceBatchId},#{listedTime},#{traceTemplateId},#{traceTemplateName},#{h5TrancePageId},"
			+ "#{h5TempleteName},#{createId},#{createMan},#{nodeDataCount}, #{sysId}) ")
	int insertTraceBatchInfo(TraceBatchInfo traceBatchInfo);
	
	/**
	 * 修改溯源批次
	 * @author liujianqiang
	 * @data 2018年12月18日
	 * @param traceBatchInfo
	 * @return
	 */
	@Update(startScript + " UPDATE trace_batchinfo "
            + " <set>"
            + " <if test='productId !=null and productId != &apos;&apos; '>  ProductID = #{productId} ,</if> "
            + " <if test='productName !=null and productName != &apos;&apos; '>  ProductName = #{productName} ,</if> "
            + " <if test='traceBatchName !=null and traceBatchName != &apos;&apos; '>  TraceBatchName = #{traceBatchName} ,</if> "
            + " <if test='traceBatchId !=null'>  TraceBatchId = #{traceBatchId} ,</if> "
            + " <if test='listedTime !=null and listedTime != &apos;&apos; '>  ListedTime = #{listedTime} ,</if> "
            + " <if test='traceTemplateId !=null and traceTemplateId != &apos;&apos; '>  TraceTemplateId = #{traceTemplateId} ,</if> "
            + " <if test='traceTemplateName !=null and traceTemplateName != &apos;&apos; '>  TraceTemplateName = #{traceTemplateName} ,</if> "
            + " <if test='h5TrancePageId !=null and h5TrancePageId != &apos;&apos; '>  H5TrancePageId = #{h5TrancePageId} ,</if> "
            + " <if test='h5TempleteName !=null and h5TempleteName != &apos;&apos; '>  H5TempleteName = #{h5TempleteName} ,</if> "
			+ " <if test='nodeDataCount !=null and nodeDataCount != &apos;&apos; '>  NodeDataCount = #{nodeDataCount} ,</if> "
			+ " <if test='traceBatchPlatformId !=null and traceBatchPlatformId != &apos;&apos; '>  TraceBatchPlatformId = #{traceBatchPlatformId} ,</if> "
            + " </set>"
            + " WHERE TraceBatchInfoId = #{traceBatchInfoId} "
            + endScript
			)
	int updateTraceBatchInfo(TraceBatchInfo traceBatchInfo);
	
	/**
	 * 获取该组织下的批次Id是否存在
	 * @author liujianqiang
	 * @data 2018年12月18日
	 * @param traceBatchInfo
	 * @return
	 */
	@Select(" SELECT COUNT(1) FROM trace_batchinfo WHERE TraceBatchId = #{param1} AND OrganizationId = #{param2} AND traceBatchInfoId != #{param3}")
	int getCountByOrgAndBatchId(String traceBatchId,String organizationId,String traceBatchInfoId);
	
	/**
	 * 获取该组织下的批次名称是否存在
	 * @author liujianqiang
	 * @data 2018年12月18日
	 * @param traceBatchInfo
	 * @return
	 */
	@Select(" SELECT COUNT(1) FROM trace_batchinfo WHERE TraceBatchName = #{param1} AND OrganizationId = #{param2} AND traceBatchInfoId != #{param3} ")
	int getCountByOrgAndBatchName(String traceBatchName,String organizationId,String traceBatchInfoId);
	
	/**
	 * 根据条件查询记录数
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @param traceBatchInfo
	 * @return
	 */
	@Select(startScript + " SELECT COUNT(1) FROM trace_batchinfo a "
			+ startWhere
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '>  OrganizationId = #{organizationId} </if> "
            + " <if test='search !=null and search != &apos;&apos; '> AND ( a.ProductName LIKE CONCAT('%',#{search},'%') OR a.TraceBatchName LIKE CONCAT('%',#{search},'%') "
            	+ " OR a.TraceTemplateName LIKE CONCAT('%',#{search},'%') OR a.H5TempleteName LIKE CONCAT('%',#{search},'%') OR a.CreateMan LIKE CONCAT('%',#{search},'%') )</if> "
            + endWhere
            + endScript)
	int getCountByCondition(Map<String,Object> map);
	
	/**
	 * 根据条件查询批次信息
	 * @author liujianqiang
	 * @data 2018年12月19日
	 * @param traceBatchInfo
	 * @return
	 */
	@Select(startScript + selectSql
			+ " FROM trace_batchinfo a LEFT JOIN trace_funtemplatestatistical b ON a.TraceTemplateId = b.TraceTemplateId "
			+ startWhere
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '>  AND a.OrganizationId = #{organizationId} </if> "
            + " <if test='productID !=null and productID != &apos;&apos; '>  AND a.ProductID = #{productID} </if> "
            + " <if test='search !=null and search != &apos;&apos; '> AND ( a.ProductName LIKE CONCAT('%',#{search},'%') OR a.TraceBatchName LIKE CONCAT('%',#{search},'%') "
            	+ " OR a.TraceTemplateName LIKE CONCAT('%',#{search},'%') OR a.H5TempleteName LIKE CONCAT('%',#{search},'%') OR a.CreateMan LIKE CONCAT('%',#{search},'%') )</if> "
            + endWhere
			+ orderBy
			+ page
            + endScript)
	List<ReturnTraceBatchInfo> getTraceBatchInfo(Map<String,Object> map);
	
	/**
	 * 删除溯源批次记录
	 * @author liujianqiang
	 * @data 2018年12月21日
	 * @param traceBatchInfoId
	 * @return
	 */
	@Delete(" DELETE FROM trace_batchinfo WHERE TraceBatchInfoId = #{traceBatchInfoId}  ")
	int deleteTraceBatchInfo(String traceBatchInfoId);

	@Select(selectSql+" from trace_batchinfo a WHERE TraceBatchInfoId = #{traceBatchInfoId}")
	TraceBatchInfo selectByTraceBatchInfoId(String traceBatchInfoId);
    
	/**
	 * 根据批次对象中某个字段查询唯一的批次记录
	 * @param plainSql
	 * @return
	 */
	@Select(selectSql+" from trace_batchinfo a WHERE ${plainSql}")
	TraceBatchInfo getOneByUnkonwnOneField(@Param("plainSql")String plainSql);


	@Update("update trace_batchinfo set TraceTemplateName=#{templateName} where TraceTemplateId = #{traceTemplateId}")
	void updateTemplateNameByTemplateId(@Param("templateName")String templateName, @Param("traceTemplateId")String templateConfigId);
	
}
