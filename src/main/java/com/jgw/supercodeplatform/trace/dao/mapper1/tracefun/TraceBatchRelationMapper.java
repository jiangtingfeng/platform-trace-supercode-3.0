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
            "(`BatchRelationId`,`CurrentBatchId`,`ParentBatchId`,`CreateDate`,`UpdateDate`,`BusinessTableName`,`ParentBusinessTableName`,`ParentBatchType`,`SysId`)" +
            "VALUES" +
            "( #{batchRelationId},#{currentBatchId},#{parentBatchId},now(),now(),#{businessTableName},#{parentBusinessTableName},#{parentBatchType}, #{sysId})" +
            "")
    int insertTraceBatchRelation(TraceBatchRelation traceBatchRelation);

    /**
     * 根据批次id获取父级批次关联数据
     * @param batchId
     * @return
     */
    @Select("SELECT * FROM trace_batchrelation where CurrentBatchId=#{batchId}")
    List<TraceBatchRelation> selectByBatchId(@Param("batchId") String batchId);

    @Select("SELECT b.CurrentBatchId, t.CurrentBatchId ParentBatchId, t.CreateDate FROM trace_batchrelation t \n" +
            "INNER JOIN trace_batchrelation b on b.ParentBatchId=t.CurrentBatchId\n" +
            "WHERE t.ParentBatchId IS NULL AND b.CurrentBatchId in (${ids})")
    List<TraceBatchRelation> selectParentBatch(@Param("ids") String ids);

    @Select("SELECT CurrentBatchId,ParentBatchId, CreateDate FROM trace_batchrelation\n" +
            "WHERE ParentBatchId in (${ids})")
    List<TraceBatchRelation> selectChildBatch(@Param("ids") String ids);
}
