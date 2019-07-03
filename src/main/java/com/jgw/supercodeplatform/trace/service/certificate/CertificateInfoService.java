package com.jgw.supercodeplatform.trace.service.certificate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificateInfoExMapper;
import com.jgw.supercodeplatform.trace.dto.certificate.*;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CertificateInfoService extends CommonUtil implements AbstractPageService {

    @Autowired
    private CertificateInfoExMapper certificateInfoExMapper;

    @Override
    public Object searchResult(DaoSearch searchParams) {
        return certificateInfoExMapper.listCertificateInfo(searchParams);
    }

    @Override
    public int count(DaoSearch searchParams) {
        return certificateInfoExMapper.countCertificateInfo(searchParams);
    }


    @Transactional(rollbackFor = Exception.class)
    public CertificateInfoReturnParam insert(CertificateInfoParam param) throws Exception{
        CertificateInfo record = JSONObject.parseObject( JSONObject.toJSONString(param),CertificateInfo.class);
        if(StringUtils.isBlank(record.getTemplateId())){
            throw new SuperCodeException("请选择模板");
        }
        AccountCache userAccount = getUserLoginCache();

        checkCertificateNameAndNumber(record);

        record.setCreateMan(userAccount.getUserName());
        record.setCreateId(userAccount.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setCertificateInfoId(getUUID());
        record.setOrganizationId(getOrganizationId());
        // 启用
        record.setDisableFlag(1);
        certificateInfoExMapper.insert(record);

        return returnParam(record);
    }
    /**
     * 返回ID和模板ID
     * @param record
     * @return
     */
    private CertificateInfoReturnParam returnParam(CertificateInfo record) {
        CertificateInfoReturnParam returnParam = new CertificateInfoReturnParam();
        returnParam.setId(record.getId());
        returnParam.setCertificateInfoId(record.getCertificateInfoId());
        return returnParam;
    }
    private void checkCertificateNameAndNumber(CertificateInfo record) throws Exception{
        String organizationId= getOrganizationId();

        List<CertificateInfo> certificateInfos=certificateInfoExMapper.selectByCertificateName(record.getCertificateName(),organizationId, record.getId());
        if(CollectionUtils.isNotEmpty(certificateInfos)){
            throw new SuperCodeException(String.format("合格证名称已存在：%s",record.getCertificateName()));
        }

        if (StringUtils.isEmpty(record.getCertificateNumber())){
            setNoRepeatNumber(record);
        }else {
            certificateInfos=certificateInfoExMapper.selectByCertificateNumber(record.getCertificateNumber(),organizationId, record.getId());
            if(CollectionUtils.isNotEmpty(certificateInfos)){
                throw new SuperCodeException(String.format("合格证编号已存在：%s",record.getCertificateNumber()));
            }
        }
    }

    /**
     * 解决自增时候与手动录入冲突
     * @param record
     * @throws SuperCodeException
     */
    private void setNoRepeatNumber(CertificateInfo record) throws SuperCodeException {
        String incrKey=String.format("%s", RedisKey.CertificateInfoSerialNumber+getOrganizationId());
        long incr = redisUtil.generate(incrKey);
        String serial= StringUtils.leftPad(String.valueOf(incr),6,"0");
        String placeNumber=String.format("C%s",serial);
        List<CertificateInfo>  certificateInfos=certificateInfoExMapper.selectByCertificateNumber(placeNumber,getOrganizationId(), record.getId());
        if(CollectionUtils.isNotEmpty(certificateInfos)){
             setNoRepeatNumber(record);
        }else {
            record.setCertificateNumber(placeNumber);
        }
    }

    public void delete(Long id)
    {
        certificateInfoExMapper.deleteByPrimaryKey(id);
    }

    public void update(CertificateInfoParam param) throws Exception{
        CertificateInfo record = JSONObject.parseObject( JSONObject.toJSONString(param),CertificateInfo.class);

        checkCertificateNameAndNumber(record);

        certificateInfoExMapper.updateByPrimaryKey(record);
    }

    public CertificateInfoParam getById(Long id){
        return certificateInfoExMapper.selectByPrimaryKey(id);
    }

    public void updateDisableFlag(Integer disableFlag, Long id){
        certificateInfoExMapper.updateDisableFlag(disableFlag,id);
    }

    public void copy(CertificateInfoCopyParam record) throws SuperCodeException {
        if(StringUtils.isEmpty(record.getCertificateName())){
            throw new SuperCodeException("模板名称不存在");
        }
        List<CertificateInfo> certificateInfos = certificateInfoExMapper.selectByCertificateName(record.getCertificateName(), getOrganizationId(), null);
        if(!CollectionUtils.isEmpty(certificateInfos)){
            throw new SuperCodeException("模板名称已存在");
        }
        CertificateInfoParam certificateInfoParam = certificateInfoExMapper.selectByPrimaryKey(record.getId());
        CertificateInfo dto = JSONObject.parseObject( JSONObject.toJSONString(certificateInfoParam),CertificateInfo.class);
        setNoRepeatNumber(dto);
        dto.setCertificateName(record.getCertificateName());
        dto.setId(null);
        dto.setCertificateInfoId(getUUID());
        dto.setCreateId(getUserLoginCache().getAccountId());
        dto.setCreateMan(getUserLoginCache().getUserName());
        dto.setCreateTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setOrganizationId(getOrganizationId());
        certificateInfoExMapper.insert(dto);
    }
}
