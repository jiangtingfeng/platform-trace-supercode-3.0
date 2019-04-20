package com.jgw.supercodeplatform.trace.service.producttesting;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.model.ReturnParamsMap;
import com.jgw.supercodeplatform.trace.common.model.page.AbstractPageService;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.producttesting.ProductTestingItemMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.producttesting.ProductTestingMapper;
import com.jgw.supercodeplatform.trace.dto.producttesting.ProductTestingParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItem;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.tracebatch.TraceBatchInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductTestingService extends AbstractPageService {

    @Autowired
    private ProductTestingMapper productTestingMapper;

    @Autowired
    private ProductTestingItemMapper productTestingItemMapper;

    @Autowired
    private CommonUtil commonUtil;


    public String insert(ProductTestingParam productTestingParam) throws Exception{
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(productTestingParam), Map.class);

        ProductTesting productTesting = JSONObject.parseObject(JSONObject.toJSONString(map), ProductTesting.class);

        AccountCache userAccount = getUserLoginCache();
        //String organizationId = getOrganizationId();

        //productTesting.setOrganizationId(organizationId);
        productTesting.setCreateId(userAccount.getUserId());
        productTesting.setCreateMan(userAccount.getUserName());
        productTesting.setProductTestingId(getUUID());
        productTesting.setCreateTime(new Date());
        productTestingMapper.insert(productTesting);
        String productTestingId=productTesting.getProductTestingId();

        for(ProductTestingItem productTestingItem: productTestingParam.getProductTestingItems()){

            productTestingItem.setProductTestingId(productTestingId);
            productTestingItem.setProductTestingItemId(getUUID());
            productTestingItemMapper.insert(productTestingItem);
        }

        return productTesting.getProductTestingId();
    }

    public List<ProductTesting> list()
    {
        return productTestingMapper.selectAll();
    }


    public Map<String, Object> getById(Integer productTestingId ) throws Exception {
        ProductTesting productTesting= productTestingMapper.selectByPrimaryKey(productTestingId);
        String prdTestId = productTesting.getProductTestingId();
        List<ProductTestingItem> productTestingItems= productTestingItemMapper.selectByProductTestingId(String.format("'%s'",prdTestId));
        Map<String, Object> testingMap= beanToMap(productTesting);
        testingMap.put("testingItems",productTestingItems);
        return testingMap;
    }

    public Map<String, Object> listProductTesting(Map<String, Object> map) throws Exception {
        String organizationId=commonUtil.getOrganizationId();
        //map.put("organizationId", getOrganizationId());

        ReturnParamsMap returnParamsMap=null;
        Map<String, Object> dataMap=null;
        Integer total=null;
        total=productTestingMapper.getCountByCondition(map);
        returnParamsMap = getPageAndRetuanMap(map, total);

        List<ProductTesting> productTestings= productTestingMapper.selectProductTesting(map);
        List<Map<String, Object>> productTestingList=new ArrayList<Map<String, Object>>();
        if(productTestings.size()>0){
            List<String> productTestingIds= productTestings.stream().map(e->"'"+e.getProductTestingId()+"'").collect(Collectors.toList());
            String ids= StringUtils.join(productTestingIds,",");
            List<ProductTestingItem> productTestingItems= productTestingItemMapper.selectByProductTestingId(ids);

            for(ProductTesting productTesting:productTestings){
                Map<String, Object> testingMap= beanToMap(productTesting);
                productTestingList.add(testingMap);
                String productTestingId= productTesting.getProductTestingId();
                List<ProductTestingItem> productTestingItems1= productTestingItems.stream().filter(e->e.getProductTestingId().equals(productTestingId)).collect(Collectors.toList());
                testingMap.put("testingItems",productTestingItems1);
            }
        }

        dataMap = returnParamsMap.getReturnMap();
        getRetunMap(dataMap, productTestingList);

        return dataMap;
    }

    public void delete(int id)
    {
        productTestingMapper.deleteByPrimaryKey(id);
    }

    public void update(ProductTestingParam productTestingParam){
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(productTestingParam), Map.class);

        ProductTesting productTesting = JSONObject.parseObject(JSONObject.toJSONString(map), ProductTesting.class);

        productTestingMapper.updateByPrimaryKey(productTesting);
        String productTestingId=productTesting.getProductTestingId();

        for(ProductTestingItem productTestingItem: productTestingParam.getProductTestingItems()){

            if(productTestingItem.getId()==null){
                productTestingItem.setProductTestingId(productTestingId);
                productTestingItem.setProductTestingItemId(getUUID());
                productTestingItemMapper.insert(productTestingItem);
            }else {
                productTestingItemMapper.updateByPrimaryKey(productTestingItem);
            }
        }
    }

}
