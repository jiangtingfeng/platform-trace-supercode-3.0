package com.jgw.supercodeplatform.trace.service.certificate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificateInfoMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificateTemplateExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificateTemplateFieldMapper;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateCopyParam;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateFieldParam;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateParam;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateReturnParam;
import com.jgw.supercodeplatform.trace.dto.standardtemplate.TemplateTypeParam;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplate;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplateField;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CertificateTemplateService extends CommonUtil implements AbstractPageService {

    @Autowired
    private CertificateTemplateExMapper certificateTemplateMapper;

    @Autowired
    private CertificateTemplateFieldMapper certificateTemplateFieldMapper;


    @Autowired
    private CertificateInfoMapper certificateInfoMapper;
    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public Object searchResult(DaoSearch searchParams) {
        return certificateTemplateMapper.listCertificateTemplate(searchParams);
    }

    @Override
    public int count(DaoSearch searchParams) {
        return certificateTemplateMapper.countCertificateTemplate(searchParams);
    }

    @Transactional(rollbackFor = Exception.class)
    public CertificateTemplateReturnParam insert(CertificateTemplateParam param) throws Exception{
        CertificateTemplate record = JSONObject.parseObject( JSONObject.toJSONString(param),CertificateTemplate.class);
        AccountCache userAccount = getUserLoginCache();

        checkTemplateNameAndNumber(record);

        record.setCreateMan(userAccount.getUserName());
        record.setCreateId(userAccount.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setTemplateId(getUUID());
        record.setOrganizationId(getOrganizationId());
        certificateTemplateMapper.insert(record);
        String templateId=record.getTemplateId();
        insertTemplateField(param.getFieldParamList(),templateId);
        return returnParam(record);
    }

    /**
     * 返回ID和模板ID
     * @param record
     * @return
     */
    private CertificateTemplateReturnParam returnParam(CertificateTemplate record) {
        CertificateTemplateReturnParam returnParam = new CertificateTemplateReturnParam();
        returnParam.setId(record.getId());
        returnParam.setTemplateId(record.getTemplateId());
        returnParam.setTemplateNumber(record.getTemplateNumber());
        return returnParam;
    }

    private void checkTemplateNameAndNumber(CertificateTemplate record) throws Exception{
        // 模板名称处理
        String organizationId= getOrganizationId();
        List<CertificateTemplate> certificateTemplates=certificateTemplateMapper.selectByTemplateName(record.getTemplateName(),organizationId, record.getId());
        if(CollectionUtils.isNotEmpty(certificateTemplates)){
            throw new SuperCodeException(String.format("模板名称已存在：%s",record.getTemplateName()));
        }
    // 模板编号处理
        if (StringUtils.isEmpty(record.getTemplateNumber())){
            setNoRepeatNumber(record);
        }else {
            certificateTemplates=certificateTemplateMapper.selectByTemplateNumber(record.getTemplateNumber(),organizationId, record.getId());
            if(CollectionUtils.isNotEmpty(certificateTemplates)){
                throw new SuperCodeException(String.format("模板编号已存在：%s",record.getTemplateNumber()));
            }
        }
    }

    /**
     * 解决自增id的手动Id的冲突
     * @param record
     * @throws SuperCodeException
     */
    private void setNoRepeatNumber(CertificateTemplate record) throws SuperCodeException {
        String incrKey=String.format("%s", RedisKey.CertificateTemplateSerialNumber + getOrganizationId());
        long incr = redisUtil.generate(incrKey);
        String serial= StringUtils.leftPad(String.valueOf(incr),6,"0");
        String placeNumber=String.format("M%s",serial);
        List<CertificateTemplate> certificateTemplates = certificateTemplateMapper.selectByTemplateNumber(placeNumber,getOrganizationId(), record.getId());
        if(CollectionUtils.isNotEmpty(certificateTemplates)){
            setNoRepeatNumber(record);
        }else {
            record.setTemplateNumber(placeNumber);

        }
    }

    public void delete(Long id)
    {
        certificateTemplateMapper.deleteByPrimaryKey(id);
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(CertificateTemplateParam param) throws Exception{
        CertificateTemplate record = JSONObject.parseObject( JSONObject.toJSONString(param),CertificateTemplate.class);
        checkTemplateNameAndNumber(record);
        record.setOrganizationId(getOrganizationId());
        certificateTemplateMapper.updateByPrimaryKey(record);
        certificateTemplateMapper.deleteFieldByTemplateId(record.getTemplateId());
        String templateId=record.getTemplateId();
        insertTemplateField(param.getFieldParamList(),templateId);
//        String certificateInfoData = param.getCertificateInfoData();
        param.getFieldParamList().forEach(e -> {
            e.setTemplateId(templateId);
        });
        String jsonString = JSONObject.toJSONString(param.getFieldParamList());
        certificateInfoMapper.updateCertificateInfoWhenChangeTemplate(param.getTemplateId(),jsonString);
    }

    @Transactional(rollbackFor = Exception.class)
    public void copy(CertificateTemplateCopyParam param) throws Exception{
        if(StringUtils.isEmpty(param.getTemplateName())){
            throw new SuperCodeException("模板名称不存在");
        }
        List<CertificateTemplate> certificateTemplates = certificateTemplateMapper.selectByTemplateName(param.getTemplateName(), getOrganizationId(), null);
        if(!CollectionUtils.isEmpty(certificateTemplates)){
            throw new SuperCodeException("模板名称已存在");
        }
        CertificateTemplate certificateTemplate = certificateTemplateMapper.selectByPrimaryKey(param.getId());
        certificateTemplate.setId(null);
        certificateTemplate.setTemplateId(getUUID());
        setNoRepeatNumber(certificateTemplate);
        // "${源模板名}副本"
        certificateTemplate.setTemplateName(param.getTemplateName());
        certificateTemplate.setCreateId(getUserLoginCache().getAccountId());
        certificateTemplate.setCreateMan(getUserLoginCache().getUserName());
        certificateTemplate.setCreateTime(new Date());
        certificateTemplateMapper.insert(certificateTemplate);
        List<CertificateTemplateFieldParam> certificateTemplateFieldParams = certificateTemplateMapper.selectFieldByTemplateId(param.getTemplateId());
        certificateTemplateFieldParams.forEach(field->{
            CertificateTemplateField certificateTemplateField = JSONObject.parseObject( JSONObject.toJSONString(field),CertificateTemplateField.class);
            certificateTemplateField.setTemplateId(certificateTemplate.getTemplateId());
            certificateTemplateField.setId(null);
            try {
                certificateTemplateField.setCreateId(getUserLoginCache().getUserId());
            } catch (SuperCodeException e) {
                e.printStackTrace();
            }
            certificateTemplateField.setCreateTime(new Date());
            certificateTemplateFieldMapper.insert(certificateTemplateField);
        });
    }



    private void insertTemplateField(List<CertificateTemplateFieldParam> fieldParamList,String templateId) throws Exception {
        if(CollectionUtils.isEmpty(fieldParamList)){
            return;
        }
        for(CertificateTemplateFieldParam param1:fieldParamList){
            CertificateTemplateField certificateTemplateField = JSONObject.parseObject( JSONObject.toJSONString(param1),CertificateTemplateField.class);
            certificateTemplateField.setTemplateId(templateId);
            certificateTemplateField.setCreateId(getUserLoginCache().getUserId());
            certificateTemplateField.setCreateTime(new Date());
            certificateTemplateFieldMapper.insert(certificateTemplateField);
        }
    }

    public CertificateTemplateParam getById(Long id){
        CertificateTemplate certificateTemplate= certificateTemplateMapper.selectByPrimaryKey(id);
        CertificateTemplateParam record = JSONObject.parseObject( JSONObject.toJSONString(certificateTemplate),CertificateTemplateParam.class);
        List<CertificateTemplateFieldParam> certificateTemplateFieldParams= certificateTemplateMapper.selectFieldByTemplateId(record.getTemplateId());
        record.setFieldParamList(certificateTemplateFieldParams);
        return record;
    }

}
