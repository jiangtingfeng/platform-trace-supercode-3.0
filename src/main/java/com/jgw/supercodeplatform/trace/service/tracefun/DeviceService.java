package com.jgw.supercodeplatform.trace.service.tracefun;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 设备信息
 *
 * @author wzq
 * @date: 2019-03-29
 */
@Service
public class DeviceService  extends CommonUtil {


    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;

    /**
     * 添加设备使用记录
     * @param deviceId
     * @return
     */
    public JsonNode insertUsageInfo(String deviceId,String operationalContent) {

        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> headerMap = new HashMap<String, String>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        try {
            AccountCache userAccount = getUserLoginCache();
            params.put("deviceId", deviceId);
            params.put("usageTime", time.format(formatter));
            params.put("userId", userAccount.getUserId());
            params.put("userName", userAccount.getUserName());
            params.put("operationalContent", operationalContent);

            headerMap.put("super-token", getSuperToken());
            ResponseEntity<String> rest = restTemplateUtil.postJsonDataAndReturnJosn(restUserUrl + "/device/usage-record", JSONObject.toJSONString( params), headerMap);

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
