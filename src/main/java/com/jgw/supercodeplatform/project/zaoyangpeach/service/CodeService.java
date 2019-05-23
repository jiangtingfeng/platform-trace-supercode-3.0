package com.jgw.supercodeplatform.project.zaoyangpeach.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.trace.aware.TraceApplicationContextAware;
import com.jgw.supercodeplatform.trace.common.model.page.Page;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach.BatchInfoMapper;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.service.common.CommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CodeService extends CommonUtil {

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private TraceApplicationContextAware applicationAware;

    @Autowired
    private CommonService commonService;

    @Value("${rest.codemanager.url}")
    private String restCodeManagerUrl;

    public Map<String, Object> listRelationRecord(Map<String, Object> params) throws Exception{

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("super-token", getSuperToken());

        Map<String, Object> dataMap=new HashMap<String, Object>();

        String organizationId = getOrganizationId();

        ResponseEntity<String> rest =  restTemplateUtil.getRequestAndReturnJosn(restCodeManagerUrl + "/code/relation/list/relation/record", params, headerMap);
        if (rest.getStatusCode().value() == 200) {
            String body = rest.getBody();

            //body= commonService.txt2String(new File("G:\\body.txt"));

            JsonNode resultsNode=new ObjectMapper().readTree(body).get("results");
            List<JSONObject> relationViews= (List<JSONObject>)JSONObject.parseObject(resultsNode.get("list").toString(), ArrayList.class);
            Page page= (Page)JSONObject.parseObject(resultsNode.get("pagination").toString(), Page.class);

            List<String> batchIds= relationViews.stream().map(e->"'"+String.valueOf(e.get("productBatchId"))+"'").collect(Collectors.toList());

            TraceFunFieldConfig packingSpecField = batchInfoMapper.selectByNestCompentFieldCode(organizationId,"PackingSpec");
            if(packingSpecField!=null){
                String enTableName= packingSpecField.getEnTableName();
                DynamicBaseMapper dao=applicationAware.getDynamicMapperByFunctionId(null,packingSpecField.getFunctionId());
                String selectSql = String.format("select * from %s where TraceBatchInfoId in (%s) ",enTableName,StringUtils.join(batchIds,","));
                List<LinkedHashMap<String, Object>> batchList= dao.select(selectSql);
                if(CollectionUtils.isNotEmpty(batchList)){
                    for(LinkedHashMap<String, Object> batchMap:batchList){
                        String traceBatchInfoId= batchMap.get("TraceBatchInfoId").toString();
                        String packingSpec=String.valueOf( batchMap.get("PackingSpec"));
                        String packingQuantity=String.valueOf( batchMap.get("PackingQuantity"));

                        List<JSONObject> relations= relationViews.stream().filter(e->String.valueOf(e.get("productBatchId")).equals(traceBatchInfoId)).collect(Collectors.toList());
                        for(JSONObject relation:relations){
                            relation.put("packingSpec",packingSpec);
                            relation.put("packingQuantity",packingQuantity);
                        }
                    }
                }
            }

            dataMap.put("list",relationViews);
            dataMap.put("pagination",page);
        }
        return dataMap;
    }

}
