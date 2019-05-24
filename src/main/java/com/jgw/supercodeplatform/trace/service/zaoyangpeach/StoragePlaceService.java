package com.jgw.supercodeplatform.trace.service.zaoyangpeach;

import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchStoragePlaceRelationMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.SortingPlaceExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.StoragePlaceExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.StoragePlaceMapper;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.SortingPlace;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoragePlaceService extends AbstractPageService {
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoragePlaceExMapper storagePlaceMapper;

    @Autowired
    private SortingPlaceExMapper sortingPlaceExMapper;

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


    public String insert(StoragePlace record){

        if(StringUtils.isNotEmpty(record.getSortingPlaceName())){
            SortingPlace sortingPlace=new SortingPlace();
            sortingPlace.setSortingPlaceName(record.getSortingPlaceName());
            sortingPlaceExMapper.insert(sortingPlace);

            record.setSortingPlaceId(sortingPlace.getId().toString());
        }

        record.setPlaceId(getUUID());
        storagePlaceMapper.insert(record);
        return record.getPlaceId();
    }

    public void delete(Integer id){
        storagePlaceMapper.deleteByPrimaryKey(id);
    }

    public void update(StoragePlace record){
        storagePlaceMapper.updateByPrimaryKey(record);
    }

    public Map<String, Object> listStoragePlace(Map<String, Object> map) throws Exception {

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=storagePlaceMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<StoragePlace> list= storagePlaceMapper.selectSortingPlace(map);

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, list);

        return dataMap;
    }

    public Map<String, Object> listSortingPlace(Map<String, Object> map) throws Exception {

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=sortingPlaceExMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<SortingPlace> list= sortingPlaceExMapper.selectSortingPlace(map);

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, list);

        return dataMap;
    }

    public void updateDisableFlag(Integer id, Integer disableFlag){
        storagePlaceMapper.updateDisableFlag(id,disableFlag);
    }
}
