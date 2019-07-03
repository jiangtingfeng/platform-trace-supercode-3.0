package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.MassIfBatchDto;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BatchStoragePlaceRelationMapper {

    @Insert("INSERT INTO `zaoyang_batchstorageplace_relation` (`storagePlaceId`,`traceBatchInfoId`)VALUES(#{storagePlaceId},#{traceBatchInfoId})")
    int insertBatchStoragePlaceRelation(BatchStoragePlaceRelation batchStoragePlaceRelation);


    @Select("SELECT * FROM `zaoyang_batchstorageplace_relation` where storagePlaceId=#{storagePlaceId} AND  DeleteStatus=0  order by id desc limit 0,1 ")
    BatchStoragePlaceRelation selectBatchStoragePlaceRelation(@Param("storagePlaceId") String storagePlaceId);

    @Update("UPDATE zaoyang_batchstorageplace_relation SET DeleteStatus=1 WHERE storagePlaceId=#{storagePlaceId} ")
    void setDeleteStatus(@Param("storagePlaceId") String storagePlaceId);

    @Select("SELECT b.*, r.storagePlaceId \n" +
            "FROM zaoyang_batchstorageplace_relation r\n" +
            "INNER JOIN trace_batchinfo b on r.traceBatchInfoId=b.TraceBatchInfoId \n" +
            "WHERE storagePlaceId in ( ${storagePlaceIds} )  And  DeleteStatus=0 " +
            "AND not Exists( SELECT 1 FROM trace_batchrelation t where t.ParentBatchId = b.TraceBatchInfoId )" +
            "and b.FunctionId=#{functionId} ")
    List<MassIfBatchDto> selectBatchByStoragePlaceIds(@Param("storagePlaceIds")String storagePlaceIds,@Param("functionId")String functionId);

    @Select("SELECT b.* \n" +
            "FROM zaoyang_batchstorageplace_relation r\n" +
            "INNER JOIN trace_batchinfo b on r.traceBatchInfoId=b.TraceBatchInfoId \n" +
            "WHERE storagePlaceId=#{storagePlaceId} And  DeleteStatus=0  order by id desc limit 0,1  ")
    TraceBatchInfo selectBatchByStoragePlaceId(@Param("storagePlaceId") String storagePlaceId);

    @Select("SELECT * FROM `zaoyang_batchstorageplace_relation` WHERE traceBatchInfoId=#{traceBatchInfoId}  limit 0,1")
    BatchStoragePlaceRelation selectByTraceBatchInfoId(@Param("traceBatchInfoId")String traceBatchInfoId);
}
