package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TraceBatchRelationMapper extends CommonSql {

    @Insert("INSERT INTO trace_batchrelation" +
            "(`BatchRelationId`,`CurrentBatchId`,`ParentBatchId`,`CreateDate`,`UpdateDate`,`BusinessTableName`,`ParentBusinessTableName`,`BatchType`)" +
            "VALUES" +
            "( #{batchRelationId},#{currentBatchId},#{parentBatchId},now(),now(),#{businessTableName},#{parentBusinessTableName},#{batchType})" +
            "")
    int insertTraceBatchRelation(TraceBatchRelation traceBatchRelation);

    @Select("SELECT * FROM jgw_trace.trace_batchrelation where CurrentBatchId=#{batchId}")
    List<TraceBatchRelation> selectByBatchId(@Param("batchId") String batchId);
}
