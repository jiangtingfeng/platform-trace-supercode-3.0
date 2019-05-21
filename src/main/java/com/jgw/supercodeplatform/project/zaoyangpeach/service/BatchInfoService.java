package com.jgw.supercodeplatform.project.zaoyangpeach.service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchInfoMapper;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchInfoService extends CommonUtil {

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private TraceBatchInfoMapper traceBatchInfoMapper;

    @Autowired
    private TraceApplicationContextAware applicationAware;

    public Map<String,Object> getBatchInfo(String batchName) throws Exception{

        String organizationId = getOrganizationId();

        organizationId="5d4010983d914fa7901b389d6ddcd39a";

        TraceBatchInfo traceBatchInfo= traceBatchInfoMapper.selectByBatchName(batchName);
        if(traceBatchInfo==null){
            throw new SuperCodeException("未找到该批次");
        }
        String traceBatchInfoId =traceBatchInfo.getTraceBatchInfoId();

        Map<String,Object> result=new HashMap<String, Object>();
        result.put("traceBatchInfoId",traceBatchInfoId);
        result.put("productId",traceBatchInfo.getProductId());
        result.put("productName",traceBatchInfo.getProductName());
        result.put("traceBatchName",traceBatchInfo.getTraceBatchName());

        TraceFunFieldConfig packingSpecField = batchInfoMapper.selectByFieldCode(organizationId);
        String enTableName= packingSpecField.getEnTableName();

        DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,packingSpecField.getFunctionId());

        String selectSql = String.format("select * from %s where TraceBatchInfoId = '%s' ",enTableName,traceBatchInfoId);
        List<LinkedHashMap<String, Object>> batchList= dao.select(selectSql);
        if(CollectionUtils.isNotEmpty(batchList)){
            result.put("packingSpec", batchList.get(0).get("PackingSpec"));
            result.put("packingQuantity", batchList.get(0).get("PackingQuantity"));

        }

        return result;
    }
}
