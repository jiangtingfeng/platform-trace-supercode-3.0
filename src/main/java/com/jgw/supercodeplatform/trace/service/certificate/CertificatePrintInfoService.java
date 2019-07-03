package com.jgw.supercodeplatform.trace.service.certificate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.trace.common.util.CommonUtil;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificateInfoExMapper;
import com.jgw.supercodeplatform.trace.dao.mapper1.certificate.CertificatePrintInfoExMapper;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateInfoParam;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificatePrintInfoDto;

import com.jgw.supercodeplatform.trace.dto.certificate.CertificatePrintInfoDtoWithTemplateInfo;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificatePrintParam;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificatePrintInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CertificatePrintInfoService extends CommonUtil implements AbstractPageService {

    @Autowired
    private CertificatePrintInfoExMapper certificatePrintInfoExMapper;
    @Autowired
    private CertificateInfoExMapper certificateInfoExMapper;

    @Override
    public Object searchResult(DaoSearch searchParams) {
        return certificatePrintInfoExMapper.listCertificatePrintInfo(searchParams);
    }

    @Override
    public int count(DaoSearch searchParams) {
        return certificatePrintInfoExMapper.countCertificatePrintInfo(searchParams);
    }


    public String insert(CertificatePrintParam param) throws Exception{
        CertificatePrintInfo record = JSONObject.parseObject( JSONObject.toJSONString(param),CertificatePrintInfo.class);
        AccountCache userAccount = getUserLoginCache();

        record.setCreateMan(userAccount.getUserName());
        record.setCreateId(userAccount.getUserId());
        record.setCreateTime(new Date());
        record.setOrganizationId(getOrganizationId());

        CertificateInfo certificateInfo = certificateInfoExMapper.selectByCertificateInfoId(record.getCertificateInfoId(),getOrganizationId());
        record.setCertificateInfoData(certificateInfo.getCertificateInfoData());
        record.setCertificateId(certificateInfo.getId());
        record.setCertificateName(certificateInfo.getCertificateName());
        record.setCertificateNumber(certificateInfo.getCertificateNumber());
        record.setTemplateId(certificateInfo.getTemplateId());
        certificatePrintInfoExMapper.insert(record);

        return record.getCertificateInfoId();
    }

    /**
     * 获取用户最后一次打印设备作为默认设备
     * @return
     * @throws SuperCodeException
     */
    public String getLastPrintDriveByUser() throws SuperCodeException {
        AccountCache userAccount = getUserLoginCache();
        return certificatePrintInfoExMapper.selectUserLastPrintDrive(userAccount.getUserId());
    }

    public CertificatePrintInfoDtoWithTemplateInfo selectById(Long id) throws SuperCodeException {
        return certificatePrintInfoExMapper.selectByPrimaryKeyWithInfo(id,getOrganizationId());
    }
}
