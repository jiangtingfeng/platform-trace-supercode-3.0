package com.jgw.supercodeplatform.trace.service.producttesting;

import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.model.page.DaoSearch;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.producttesting.TestingTypeMapper;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TestingTypeService extends AbstractPageService {

    @Autowired
    private TestingTypeMapper testingTypeMapper;

    @Autowired
    private CommonUtil commonUtil;


    public String insert(TestingType testingType) throws Exception{
        AccountCache userAccount = getUserLoginCache();
        String organizationId = getOrganizationId();

        testingType.setOrganizationId(organizationId);
        testingType.setCreateId(userAccount.getUserId());
        testingType.setCreateMan(userAccount.getUserName());
        testingType.setTestingTypeId(getUUID());
        testingType.setDisableFlag(false);
        testingType.setCreateTime(new Date());
        testingTypeMapper.insert(testingType);

        return testingType.getTestingTypeId();
    }

    public Map<String, Object> listTestingType(Map<String, Object> map) throws Exception {
        String organizationId=commonUtil.getOrganizationId();
        map.put("organizationId", getOrganizationId());

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=testingTypeMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<TestingType> testingTypes= testingTypeMapper.selectTestingType(map);

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, testingTypes);

        return dataMap;
    }

    public void delete(int id)
    {
        testingTypeMapper.deleteByPrimaryKey(id);
    }

    public void update(TestingType testingType){
        testingTypeMapper.updateByPrimaryKey(testingType);
    }

}
