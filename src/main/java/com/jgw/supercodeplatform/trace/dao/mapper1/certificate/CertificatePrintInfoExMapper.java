package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;

import com.jgw.supercodeplatform.trace.dto.certificate.CertificatePrintInfoDto;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificatePrintInfoDtoWithTemplateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificatePrintInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CertificatePrintInfoExMapper extends CertificatePrintInfoMapper {

    @Select(startScript+
            " SELECT COUNT(*) FROM trace_CertificatePrintInfo a " +
            "<where>"+
            " a.OrganizationId = #{params.organizationId}   "+
            "<if test='search != null and search != &apos;&apos;'> and (a.certificateName LIKE CONCAT('%',#{search},'%') " +
            " or  a.certificateNumber LIKE CONCAT('%',#{search},'%') or a.createMan LIKE CONCAT('%',#{search},'%') " +
            " )</if>"+
            "</where>"+
            endScript
    )
    int countCertificatePrintInfo(DaoSearch searchParams);

    @Select(startScript+
            " SELECT  a.PrintQuantity, a.CreateMan, a.CreateTime, a.CertificateName, a.CertificateNumber, a.Id,  a.CertificateId" +
            " FROM trace_CertificatePrintInfo a " +
             "<where>"+
            " a.OrganizationId = #{params.organizationId} "+
            "<if test='search != null and search != &apos;&apos;'> and (a.certificateName LIKE CONCAT('%',#{search},'%') " +
            " or  a.certificateNumber LIKE CONCAT('%',#{search},'%') or a.createMan LIKE CONCAT('%',#{search},'%') " +
            " )</if>"+
            "</where>"+
            orderBy+
            page+
            endScript)
    List<CertificatePrintInfoDto> listCertificatePrintInfo(DaoSearch searchParams);
    @Select(startScript+
            " SELECT  a.PrintQuantity, a.CreateMan, a.CreateTime ,a.CertificateName,a.CertificateNumber,a.certificateInfoData ,b.PrintWidth ,b.PrintHeight" +
            " FROM trace_CertificatePrintInfo a  INNER JOIN  trace_CertificateTemplate b " +
            " on b.TemplateId = a.TemplateId " +
            "<where>"+
            " a.OrganizationId = #{organizationId} "+
            " and a.id = #{id} " +
            "</where>"+
            endScript)
    CertificatePrintInfoDtoWithTemplateInfo selectByPrimaryKeyWithInfo(Long id, String organizationId);
}
