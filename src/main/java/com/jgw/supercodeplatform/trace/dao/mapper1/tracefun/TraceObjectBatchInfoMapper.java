package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceObjectBatchInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
            + "H5TempleteName,CreateId,CreateMan,NodeDataCount)"
            + "VALUES"
            + "(#{traceBatchInfoId},#{organizationId},#{traceBatchName},"
            + "#{traceBatchId},#{traceTemplateId},#{traceTemplateName},#{h5TrancePageId},"
            + "#{h5TempleteName},#{createId},#{createMan},#{nodeDataCount}) ")
    int insertTraceObjectBatchInfo(TraceObjectBatchInfo traceBatchInfo);

}
