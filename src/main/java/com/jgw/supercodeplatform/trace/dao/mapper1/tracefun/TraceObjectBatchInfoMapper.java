package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.ReturnTraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceObjectBatchInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TraceObjectBatchInfoMapper extends CommonSql {

    String selectSql = " SELECT a.TraceBatchInfoId traceBatchInfoId,a.OrganizationId organizationId,"
            + "a.TraceBatchName traceBatchName,a.TraceTemplateName traceTemplateName,"
            + "a.TraceBatchId traceBatchId,a.TraceTemplateId traceTemplateId,"
            +"a.NodeDataCount nodeDataCount,"
            + "a.H5TrancePageId h5TrancePageId,a.H5TempleteName h5TempleteName,a.CreateId createId,a.CreateMan createMan, a.TraceBatchPlatformId traceBatchPlatformId"
            + createTime + updateTime ;


    @Insert(" INSERT INTO trace_objectbatchinfo"
            + "(TraceBatchInfoId,OrganizationId,TraceBatchName,"
            + "TraceBatchId,TraceTemplateId,TraceTemplateName,H5TrancePageId,"
            + "H5TempleteName,CreateId,CreateMan,NodeDataCount,ObjectId, SysId)"
            + "VALUES"
            + "(#{traceBatchInfoId},#{organizationId},#{traceBatchName},"
            + "#{traceBatchId},#{traceTemplateId},#{traceTemplateName},#{h5TrancePageId},"
            + "#{h5TempleteName},#{createId},#{createMan},#{nodeDataCount},#{objectId}, #{sysId) ")
    int insertTraceObjectBatchInfo(TraceObjectBatchInfo traceBatchInfo);

    @Select(selectSql+" from trace_objectbatchinfo a WHERE TraceBatchInfoId = #{traceBatchInfoId}")
    TraceObjectBatchInfo selectByTraceBatchInfoId(String traceBatchInfoId);

    @Select(selectSql+" from trace_objectbatchinfo a where ObjectId = #{objectId} order by id desc limit 0,1")
    TraceObjectBatchInfo selectByObjectId(String objectId);


    @Select(startScript + " SELECT COUNT(1) FROM trace_objectbatchinfo a "
            + startWhere
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '>  OrganizationId = #{organizationId} </if> "
            + " <if test='search !=null and search != &apos;&apos; '> AND ( a.TraceBatchName LIKE CONCAT('%',#{search},'%') "
            + " OR a.TraceTemplateName LIKE CONCAT('%',#{search},'%') OR a.H5TempleteName LIKE CONCAT('%',#{search},'%') OR a.CreateMan LIKE CONCAT('%',#{search},'%') )</if> "
            + endWhere
            + endScript)
    int getCountByCondition(Map<String,Object> map);

    /**
     * 获取地块批次列表数据
     * @param map
     * @return
     */
    @Select(startScript + selectSql
            + " FROM trace_objectbatchinfo a LEFT JOIN trace_funtemplatestatistical b ON a.TraceTemplateId = b.TraceTemplateId "
            + startWhere
            + " <if test='organizationId !=null and organizationId != &apos;&apos; '>  AND a.OrganizationId = #{organizationId} </if> "

            + " <if test='search !=null and search != &apos;&apos; '> AND (  a.TraceBatchName LIKE CONCAT('%',#{search},'%') "
            + " OR a.TraceTemplateName LIKE CONCAT('%',#{search},'%') OR a.H5TempleteName LIKE CONCAT('%',#{search},'%') OR a.CreateMan LIKE CONCAT('%',#{search},'%') )</if> "
            + endWhere
            + orderBy
            + page
            + endScript)
    List<TraceObjectBatchInfo> getTraceBatchInfo(Map<String,Object> map);


    /**
     * 根据地块id获取对应地块批次
     * @param map
     * @return
     */
    @Select(startScript+
            "SELECT * FROM trace_objectbatchinfo a \n" +
            "where objectid =#{objectid}"
            + orderBy
            + page
            + endScript)
    List<TraceObjectBatchInfo> getTraceBatchInfoByObjectId(Map<String,Object> map);

    @Select(startScript+"SELECT COUNT(1) FROM trace_objectbatchinfo\n"
            +"where objectid =#{objectid}"
            + endScript)
    int getCountByObjectId(String objectId);
}
