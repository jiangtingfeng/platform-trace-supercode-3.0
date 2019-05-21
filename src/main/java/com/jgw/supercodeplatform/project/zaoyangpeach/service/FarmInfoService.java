package com.jgw.supercodeplatform.project.zaoyangpeach.service;

import com.jgw.supercodeplatform.trace.common.model.ObjectUniqueValueResult;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.FarmInfoMapper;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.FarmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FarmInfoService extends AbstractPageService {

    @Autowired
    private FarmInfoMapper farmInfoMapper;

    public Map<String, Object> listFarmInfo(Map<String, Object> map) throws Exception {

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=farmInfoMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<FarmInfo> testingTypes= farmInfoMapper.selectFarmInfo(map);
        List<ObjectUniqueValueResult> objectUniqueValueResults= testingTypes.stream().map(e->new ObjectUniqueValueResult(e.getFarmName(),String.valueOf(e.getId()))).collect(Collectors.toList());

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, objectUniqueValueResults);

        return dataMap;
    }
}
