package com.jgw.supercodeplatform.trace.service.tracefun;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.dto.dynamictable.common.FieldBusinessParam;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.codemanager.url}")
    private String restCodeManagerUrl;


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

}
