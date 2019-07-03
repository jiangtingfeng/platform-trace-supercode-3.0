package com.jgw.supercodeplatform.trace.service.standardtemplate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.cache.ObjectConfigCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceFunFieldConfigMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate.StandardTemplateExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.template.TraceFunTemplateconfigMapper;
import com.jgw.supercodeplatform.trace.dto.TraceFunFieldConfigParam;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.StandardTemplateParam;
import com.jgw.supercodeplatform.trace.dto.template.TraceFunTemplateconfigParam;
import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import com.jgw.supercodeplatform.trace.pojo.template.TraceFunTemplateconfig;
import com.jgw.supercodeplatform.trace.service.template.TraceFunTemplateconfigService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StandardTemplateService extends CommonUtil implements AbstractPageService {

    @Autowired
    private StandardTemplateExMapper standardTemplateExMapper;

    @Autowired
    private TraceFunTemplateconfigService traceFunTemplateconfigService;

    @Autowired
    private TraceFunTemplateconfigMapper traceFunTemplateconfigMapper;

    @Autowired
    private TraceFunFieldConfigMapper traceFunFieldConfigMapper;

    @Autowired
    private ObjectConfigCache objectConfigCache;

    @Override
    public Object searchResult(DaoSearch searchParams) {
        return standardTemplateExMapper.listStandardTemplate(searchParams);
    }

    @Override
    public int count(DaoSearch searchParams) {
        return standardTemplateExMapper.countStandardTemplate(searchParams);
    }


    @Transactional(rollbackFor = Exception.class)
    public String insert(StandardTemplateParam param) throws Exception{
        StandardTemplate record =JSONObject.parseObject( JSONObject.toJSONString(param),StandardTemplate.class);
        AccountCache userAccount = getUserLoginCache();

        List<StandardTemplate> standardTemplates= standardTemplateExMapper.selectByLevel2TypeId(record.getLevel2TypeId(),record.getId());
        if(CollectionUtils.isNotEmpty(standardTemplates)){
            throw new Exception("该类别下已存在标准模板");
        }

        record.setCreateMan(userAccount.getUserName());
        record.setCreateId(userAccount.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setTemplateId(getUUID());

        String incrKey=String.format("%s", RedisKey.StandardTemplateSerialNumber);
        long incr = redisUtil.generate(incrKey);
        String serial= StringUtils.leftPad(String.valueOf(incr),4,"0");
        String templateNumber=String.format("MP%s",serial);
        record.setTemplateNumber(templateNumber);

        standardTemplateExMapper.insert(record);
        String templateId= record.getTemplateId();

        traceFunTemplateconfigService.save(param.getTraceFunTemplateconfigParams(),templateId,null);

        return templateId;
    }

    public void delete(Long id)
    {
        standardTemplateExMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(StandardTemplateParam param) throws Exception{
        StandardTemplate record =JSONObject.parseObject( JSONObject.toJSONString(param),StandardTemplate.class);

        List<StandardTemplate> standardTemplates= standardTemplateExMapper.selectByLevel2TypeId(record.getLevel2TypeId(),record.getId());
        if(CollectionUtils.isNotEmpty(standardTemplates)){
            throw new Exception("该类别下已存在标准模板");
        }

        standardTemplateExMapper.updateByPrimaryKey(record);
        String templateId= record.getTemplateId();

        traceFunTemplateconfigMapper.deleteByTemplateId(templateId);
        traceFunFieldConfigMapper.deleteByTraceTemplateId(templateId);

        traceFunTemplateconfigService.save(param.getTraceFunTemplateconfigParams(),templateId,null);
    }

    public StandardTemplateParam selectByPrimaryKey(Long id) throws Exception
    {
        StandardTemplate standardTemplate=standardTemplateExMapper.selectByPrimaryKey(id);

        List<TraceFunTemplateconfig> traceFunTemplateconfigs= traceFunTemplateconfigMapper.selectByTraceTemplateId(standardTemplate.getTemplateId());
        List<TraceFunFieldConfig> traceFunFieldConfigs= traceFunFieldConfigMapper.selectByTraceTemplateId(standardTemplate.getTemplateId());

        for (TraceFunFieldConfig traceFunFieldConfig : traceFunFieldConfigs) {
            String objectType=traceFunFieldConfig.getObjectType();
            traceFunFieldConfig.setServiceAddress(objectConfigCache.getServiceUrl(objectType));
        }

        StandardTemplateParam standardTemplateParam=JSONObject.parseObject( JSONObject.toJSONString(standardTemplate),StandardTemplateParam.class);
        for(TraceFunTemplateconfig traceFunTemplateconfig:traceFunTemplateconfigs){
            TraceFunTemplateconfigParam traceFunTemplateconfigParam=JSONObject.parseObject( JSONObject.toJSONString(traceFunTemplateconfig),TraceFunTemplateconfigParam.class);

            for(TraceFunFieldConfig traceFunFieldConfig:traceFunFieldConfigs){
                if(traceFunFieldConfig.getFunctionId().equals(traceFunTemplateconfig.getNodeFunctionId())){
                    TraceFunFieldConfigParam traceFunFieldConfigParam=JSONObject.parseObject(JSONObject.toJSONString(traceFunFieldConfig),TraceFunFieldConfigParam.class);
                    if(traceFunTemplateconfigParam.getFieldConfigList()==null){
                        List<TraceFunFieldConfigParam> traceFunFieldConfigParams=new ArrayList<>();
                        traceFunTemplateconfigParam.setFieldConfigList(traceFunFieldConfigParams);
                    }
                    traceFunTemplateconfigParam.getFieldConfigList().add(traceFunFieldConfigParam);
                }
            }
            if(standardTemplateParam.getTraceFunTemplateconfigParams()==null){
                List<TraceFunTemplateconfigParam> traceFunTemplateconfigParams=new ArrayList<>();
                standardTemplateParam.setTraceFunTemplateconfigParams(traceFunTemplateconfigParams);
            }
            standardTemplateParam.getTraceFunTemplateconfigParams().add(traceFunTemplateconfigParam);
        }

        return standardTemplateParam;
    }

    public StandardTemplateParam getByLevelId(Integer levelId) throws Exception{
        StandardTemplate standardTemplate= standardTemplateExMapper.selectByLevelId(levelId);
        if(standardTemplate==null){
            throw new Exception("未找到该类目对应的标准模板");
        }
        return selectByPrimaryKey(standardTemplate.getId());
    }

    public void updateDisableFlag(Integer disableFlag, Long id){
        standardTemplateExMapper.updateDisableFlag(disableFlag,id);
    }
}
