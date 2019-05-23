package com.jgw.supercodeplatform.trace.service.zaoyangpeach;

import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchStoragePlaceRelationMapper;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoragePlaceService {

    @Autowired
    private BatchStoragePlaceRelationMapper batchStoragePlaceRelationMapper;

    public void insertBatchStoragePlaceRelation(String storagePlaceId, String traceBatchInfoId){
        BatchStoragePlaceRelation batchStoragePlaceRelation=new BatchStoragePlaceRelation();
        batchStoragePlaceRelation.setStoragePlaceId(storagePlaceId);
        batchStoragePlaceRelation.setTraceBatchInfoId(traceBatchInfoId);
        batchStoragePlaceRelationMapper.insertBatchStoragePlaceRelation(batchStoragePlaceRelation);
    }

    public String selectBatchStoragePlaceRelation( String storagePlaceId){
        String traceBatchInfoId=null;
        BatchStoragePlaceRelation batchStoragePlaceRelation= batchStoragePlaceRelationMapper.selectBatchStoragePlaceRelation(storagePlaceId);
        if(batchStoragePlaceRelation!=null){
            traceBatchInfoId=batchStoragePlaceRelation.getTraceBatchInfoId();
        }
        return traceBatchInfoId;
    }

}
