package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BatchStoragePlaceRelationMapper {

    @Insert("INSERT INTO `zaoyang_batchstorageplace_relation` (`storagePlaceId`,`traceBatchInfoId`)VALUES(#{storagePlaceId},#{traceBatchInfoId})")
    int insertBatchStoragePlaceRelation(BatchStoragePlaceRelation batchStoragePlaceRelation);


    @Select("SELECT * FROM `zaoyang_batchstorageplace_relation` where storagePlaceId=#{storagePlaceId}  order by id desc limit 0,1 ")
    BatchStoragePlaceRelation selectBatchStoragePlaceRelation(@Param("storagePlaceId") String storagePlaceId);
}
