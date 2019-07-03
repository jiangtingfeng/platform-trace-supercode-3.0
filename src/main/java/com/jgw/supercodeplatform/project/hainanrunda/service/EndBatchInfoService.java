package com.jgw.supercodeplatform.project.hainanrunda.service;

import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.project.hainanrunda.dto.batchinfo.PlantingBatchDto;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchInfoMapper;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EndBatchInfoService extends CommonUtil implements AbstractPageService {

    private static Logger logger = LoggerFactory.getLogger(EndBatchInfoService.class);

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private TraceBatchInfoMapper traceBatchInfoMapper;

    @Autowired
    private PlantingBatchInfoService plantingBatchInfoService;

    private String pickingDateFieldCode="cssj";

    public void setSearchParam(DaoSearch searchParams) throws Exception{
        String organizationId = getOrganizationId();
        //PlantingTime
        TraceFunFieldConfig packingSpecField = batchInfoMapper.selectByFieldCode(organizationId,pickingDateFieldCode);
        String functionId= packingSpecField.getFunctionId();

        Map<String,String> map=new HashMap<>();
        map.put("functionId",functionId);
        map.put("organizationId",organizationId);
        searchParams.setOther(map);
    }

    @Override
    public Object searchResult(DaoSearch searchParams) {
        List<PlantingBatchDto> plantingBatchDtos= traceBatchInfoMapper.selectEndBatchByFunctionId(searchParams);
        //List<PlantingBatchDto> plantingBatchDtos= traceBatchInfos.stream().map(e->new PlantingBatchDto(e.getTraceBatchName(),e.getTraceBatchInfoId(),e.getProductName())).collect(Collectors.toList());

        try{
            plantingBatchInfoService.selectSowingInfo2(plantingBatchDtos);
            plantingBatchInfoService.selectPickingInfo(plantingBatchDtos);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return plantingBatchDtos;
    }

    @Override
    public int count(DaoSearch searchParams) {
        Integer total=traceBatchInfoMapper.countEndBatchByFunctionId(searchParams);
        return total;
    }

}
