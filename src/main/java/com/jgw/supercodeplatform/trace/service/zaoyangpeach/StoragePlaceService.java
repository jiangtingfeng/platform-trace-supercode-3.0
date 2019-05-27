package com.jgw.supercodeplatform.trace.service.zaoyangpeach;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchStoragePlaceRelationMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.SortingPlaceExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.StoragePlaceExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.StoragePlaceMapper;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.BatchStoragePlaceRelation;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.SortingPlace;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.commons.collections.CollectionUtils;
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


    public String insert(StoragePlace record) throws Exception{

        String organizationId= getOrganizationId();

        List<StoragePlace> storagePlaces=storagePlaceMapper.selectByPlaceName(record.getPlaceName(),organizationId);
        if(CollectionUtils.isNotEmpty(storagePlaces)){
            throw new SuperCodeException(String.format("存放点名称已存在：%s",record.getPlaceName()));
        }

        if(StringUtils.isEmpty(record.getSortingPlaceId())){
            SortingPlace sortingPlace=new SortingPlace();
            sortingPlace.setSortingPlaceName(record.getSortingPlaceName());
            sortingPlace.setOrganizationId(organizationId);
            sortingPlaceExMapper.insert(sortingPlace);
            String sortingPlaceId= sortingPlaceExMapper.selectIdentity();

            record.setSortingPlaceId(sortingPlaceId);
        }
        if (StringUtils.isEmpty(record.getPlaceNumber())){
            String incrKey=String.format("%s", RedisKey.StoragePlaceSerialNumber);
            long incr = redisUtil.generate(incrKey);
            String serial= StringUtils.leftPad(String.valueOf(incr),5,"0");
            String placeNumber=String.format("PL%s",serial);
            record.setPlaceNumber(placeNumber);
        }else {
            storagePlaces=storagePlaceMapper.selectByPlaceNumber(record.getPlaceNumber(),organizationId);
            if(CollectionUtils.isNotEmpty(storagePlaces)){
                throw new SuperCodeException(String.format("存放点编号已存在：%s",record.getPlaceNumber()));
            }
        }

        record.setOrganizationId(organizationId);
        record.setDisableFlag(0);
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
        map.put("organizationId", getOrganizationId());

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
        map.put("organizationId", getOrganizationId());

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
