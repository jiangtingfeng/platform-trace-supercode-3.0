package com.jgw.supercodeplatform.trace.service.tracefun;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
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
 * 物料信息
 *
 * @author wzq
 * @date: 2019-03-29
 */
@Service
public class MaterialService  extends CommonUtil {


    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;

    /**
     * 从功能组件数据中获取物料字段信息
     * @param fields
     * @param fieldCode
     * @return
     */
    private String getMaterialInfoFieldValue(List<FieldBusinessParam> fields,String fieldCode){
        String fieldValue=null;
        List<FieldBusinessParam> fieldBusinessParams= fields.stream().filter(e->e.getFieldCode().equals(fieldCode)).collect(Collectors.toList());
        if(fieldBusinessParams!=null && fieldBusinessParams.size()>0){
            fieldValue= fieldBusinessParams.get(0).getFieldValue();
        }
        return fieldValue;
    }

    private String getCode(Map<String, String> headerMap) throws Exception{
        String code=null;
        ResponseEntity<String> rest =  restTemplateUtil.getRequestAndReturnJosn(restUserUrl + "/material/warehouse/code", null, headerMap);
        if (rest.getStatusCode().value() == 200) {
            String body = rest.getBody();
            JsonNode node = new ObjectMapper().readTree(body);
            if (200 == node.get("state").asInt()) {
                code= node.get("results").asText();
            }
        }
        return code;
    }

    /**
     * 添加物料出库记录
     * @param fields
     * @return
     */
    public JsonNode insertOutOfStockInfo(String publicMaterialId,String outboundNum,String materialBatch ) {

        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> headerMap = new HashMap<String, String>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        try {
            params.put("publicMaterialId", publicMaterialId);
            params.put("materialBatch", materialBatch);
            params.put("outboundNum", outboundNum);
            params.put("outboundTime", time.format(formatter));
            params.put("outboundTypeName", "生产出库");
            params.put("outboundType", "022001");

            headerMap.put("super-token", getSuperToken());
            String outboundCode=getCode(headerMap);
            params.put("outboundCode", outboundCode);

            ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restUserUrl + "/material/warehouse/out", JSONObject.toJSONString( params), headerMap);

            if (rest.getStatusCode().value() == 200) {
                String body = rest.getBody();
                JsonNode node = new ObjectMapper().readTree(body);
                if (200 == node.get("state").asInt()) {
                    return node.get("results");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
