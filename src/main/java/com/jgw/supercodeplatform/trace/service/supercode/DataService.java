package com.jgw.supercodeplatform.trace.service.supercode;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class DataService {

    @Value("${rest.supercode.url}")
    private String superCodeUrl;

    @Value("${rest.supercode.token}")
    private String superCodeToken;

    @Autowired
    private RestTemplate restTemplate2;

    public String getForObject(String functionId,String queryString) throws Exception {
        //restTemplate=new RestTemplate();
        String url=String.format( "%s/JsonDataService.ashx?Token=%s&function=%s",superCodeUrl,superCodeToken,functionId);
        if(StringUtils.isNotEmpty(queryString)){
            url+="&"+queryString;
        }
        String responseText = restTemplate2.getForObject(url,String.class);
        return responseText;
    }


    /*
        if (StringUtils.isEmpty(token)){
            String responseText= restTemplate.getForObject("http://interface.kf315.net/JsonDataService.ashx?function=CreateToken",String.class);

            JSONObject jsonObject= JSONObject.parseObject(responseText, JSONObject.class);
            JSONObject data= (JSONObject)jsonObject.get("Data");
            token= data.get("Token").toString();

            String url=String.format( "http://interface.kf315.net/JsonDataService.ashx?function=BindUserAndToken&Token=%s&LoginName=%s&Password=%s",token,"ceshi","123456");
            responseText= restTemplate.getForObject(url,String.class);

        }
    * */

}
