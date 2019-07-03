package com.jgw.supercodeplatform.trace.service.standardtemplate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate.TemplateTypeExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate.TemplateTypeMapper;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.TemplateTypeParam;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TemplateTypeService extends CommonUtil implements AbstractPageService {

    @Autowired
    private TemplateTypeExMapper templateTypeMapper;

    @Override
    public Object searchResult(DaoSearch searchParams) {
        return templateTypeMapper.listTemplateType(searchParams);
    }

    @Override
    public int count(DaoSearch searchParams) {
        return templateTypeMapper.countTemplateType(searchParams);
    }

    public String insert(TemplateTypeParam param) throws Exception{
        TemplateType record = JSONObject.parseObject( JSONObject.toJSONString(param),TemplateType.class);
        AccountCache userAccount = getUserLoginCache();

        updateSort(record);

        record.setCreateMan(userAccount.getUserName());
        record.setCreateId(userAccount.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setTemplateTypeId(getUUID());

        templateTypeMapper.insert(record);
        return record.getTemplateTypeId();
    }

    private void updateSort(TemplateType record){
        DaoSearch searchParams =new DaoSearch();
        searchParams.setParams(null);
        Map params= searchParams.getParams();
        params.put("categoryId",record.getCategoryId());
        params.put("parentId",record.getParentId());
        params.put("levelId",record.getLevelId());
        List<TemplateType> templateTypes= templateTypeMapper.listTemplateType(searchParams);
        Integer recordSort= record.getSort();

        int total=templateTypes.size();
        if(total==0){
            recordSort=1;
        }else {
            if(recordSort>total){
                recordSort=total+1;
            } else {
                for(int i=recordSort-1; i<total;i++){
                    TemplateType templateType= templateTypes.get(i);
                    templateType.setSort(templateType.getSort()+1);
                    templateTypeMapper.updateByPrimaryKey(templateType);
                }
            }
        }
        record.setSort(recordSort);
    }

    public void delete(Long id)
    {
        templateTypeMapper.deleteByPrimaryKey(id);
    }

    public void update(TemplateType templateType){
        updateSort(templateType);

        if(templateType.getLevelId()==1){
            templateTypeMapper.updateParentName(templateType.getId(),templateType.getTypeName());
        }

        templateTypeMapper.updateByPrimaryKey(templateType);
    }

    public void updateDisableFlag(Integer disableFlag, Long id){
        templateTypeMapper.updateDisableFlag(disableFlag,id);
    }


}
