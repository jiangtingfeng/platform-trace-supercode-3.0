package com.jgw.supercodeplatform.trace.service.tracefun;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.batchinfo.TraceBatchInfoMapper;
import com.jgw.supercodeplatform.trace.dto.code.CodeObjectRelationDto;
import com.jgw.supercodeplatform.trace.dto.code.ObjectPropertyDto;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import com.jgw.supercodeplatform.trace.service.dynamic.DynamicTableService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 码关联信息
 *
 * @author wzq
 * @date: 2019-03-29
 */
@Service
public class CodeRelationService extends CommonUtil {

    private static Logger logger = LoggerFactory.getLogger(CodeRelationService.class);

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.codemanager.url}")
    private String restCodeManagerUrl;

    @Value("${trace.h5page.url}")
    private String h5PageUrl;

    @Autowired
    private TraceBatchInfoMapper traceBatchInfoMapper;


    private String getCodeRelationFieldValue(List<FieldBusinessParam> fields, String fieldCode){
        String fieldValue=null;
        List<FieldBusinessParam> fieldBusinessParams= fields.stream().filter(e->e.getFieldCode().equals(fieldCode)).collect(Collectors.toList());
        if(fieldBusinessParams!=null && fieldBusinessParams.size()>0){
            fieldValue= fieldBusinessParams.get(0).getFieldValue();
        }
        return fieldValue;
    }

    /**
     * 添加码关联信息
     * @param fields
     * @return
     */
    public JsonNode insertCodeRelationInfo(List<FieldBusinessParam> fields) {

        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> headerMap = new HashMap<String, String>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        try {
            AccountCache userAccount = getUserLoginCache();
            params.put("globalBatchId", getCodeRelationFieldValue(fields,"globalBatchId"));
            params.put("relationType", getCodeRelationFieldValue(fields,"relationType"));
            params.put("singleCodes", getCodeRelationFieldValue(fields,"singleCodes"));
            params.put("startCode", getCodeRelationFieldValue(fields,"startCode"));
            params.put("endCode", getCodeRelationFieldValue(fields,"endCode"));

            headerMap.put("super-token", getSuperToken());
            ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restCodeManagerUrl + "/code/relation/addRelation", JSONObject.toJSONString( params), headerMap);

            if (rest.getStatusCode().value() == 200) {
                String body = rest.getBody();
                JsonNode node = new ObjectMapper().readTree(body);
                if (200 == node.get("state").asInt()) {
                    return node.get("results");
                }
            }
        } catch (SuperCodeTraceException | IOException | SuperCodeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JsonNode insertCodeRelationInfo(CodeObjectRelationDto codeObjectRelationDto) throws Exception{

        Map<String, String> headerMap = new HashMap<String, String>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        try {
            AccountCache userAccount = getUserLoginCache();

            headerMap.put("super-token", getSuperToken());
            ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restCodeManagerUrl + "/code/relation/addRelation", JSONObject.toJSONString( codeObjectRelationDto), headerMap);

            if (rest.getStatusCode().value() == 200) {
                String body = rest.getBody();

                logger.info("addRelation: "+body);

                JsonNode node = new ObjectMapper().readTree(body);
                if (200 == node.get("state").asInt()) {

                    List<ObjectPropertyDto> objectPropertyDtos= codeObjectRelationDto.getObjectPropertyDtoList().stream().filter(e->e.getObjectTypeId()==3).collect(Collectors.toList());
                    if(objectPropertyDtos!=null && objectPropertyDtos.size()>0){
                        String batchInfoId= objectPropertyDtos.get(0).getObjectId();
                        Integer codebatchId= Integer.parseInt(node.get("results").textValue());
                        if(codebatchId>0){
                            addSbatchUrl(codebatchId,batchInfoId);
                        }
                    }
                    return node.get("results");
                }else {
                    throw new Exception(node.get("msg").textValue());
                }
            }
        } catch (SuperCodeTraceException | IOException | SuperCodeException e) {
            e.printStackTrace();
            throw  e;
        }

        return null;
    }

    public JsonNode addSbatchUrl(Integer batchId,String batchInfoId) throws Exception{

        HashMap<String, Object> params = new HashMap<String, Object>();
        Map<String, String> headerMap = new HashMap<String, String>();
        List<HashMap<String, Object>> paramList=new ArrayList<HashMap<String, Object>>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        try {

            //TraceBatchInfo traceBatchInfo= traceBatchInfoMapper.selectByTraceBatchInfoId(batchId);
            String url = String.format("%s?traceBatchInfoId=%s",h5PageUrl,batchInfoId);
            params.put("url",url);
            params.put("businessType",2);
            params.put("batchId",batchId);
            paramList.add(params);

            AccountCache userAccount = getUserLoginCache();

            headerMap.put("super-token", getSuperToken());
            ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restCodeManagerUrl + "/code/sbatchUrl/addSbatchUrl", JSONObject.toJSONString( paramList), headerMap);

            String body = rest.getBody();

            logger.info("addSbatchUrl: "+body);

            JsonNode node = new ObjectMapper().readTree(body);
            if (rest.getStatusCode().value() == 200) {
                if (200 == node.get("state").asInt()) {
                    return node.get("results");
                }
            }else {
                throw new Exception(node.get("results").asText());
            }
        } catch (SuperCodeTraceException | IOException | SuperCodeException e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }
}
