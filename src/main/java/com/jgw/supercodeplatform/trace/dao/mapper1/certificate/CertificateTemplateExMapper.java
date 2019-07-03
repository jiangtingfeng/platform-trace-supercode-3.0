package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateTemplateFieldParam;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplate;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplateField;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CertificateTemplateExMapper extends CertificateTemplateMapper{

    @Select(startScript+
            " SELECT COUNT(*) FROM trace_CertificateTemplate a  "+
            "<where>"+
            " a.OrganizationId = #{params.organizationId}  "+
            "<if test='search != null and search != &apos;&apos;'> and (  templateName LIKE CONCAT('%',#{search},'%')  " +
            " or  TemplateNumber LIKE CONCAT('%',#{search},'%')" +
            "  or  CreateMan LIKE CONCAT('%',#{search},'%') " +
            ") </if>"+
            "</where>"+
            endScript
    )
    int countCertificateTemplate(DaoSearch searchParams);

    @Select(startScript+
            " SELECT * FROM trace_CertificateTemplate a  " +
            "<where>"+
            " a.OrganizationId = #{params.organizationId} "+
            "<if test='search != null and search != &apos;&apos;'> and (  templateName LIKE CONCAT('%',#{search},'%')  " +
            " or  TemplateNumber LIKE CONCAT('%',#{search},'%')" +
            "  or  CreateMan LIKE CONCAT('%',#{search},'%') " +
            ") </if>"+
            "</where>"+
            orderBy+
            page+
            endScript)
    List<CertificateTemplate> listCertificateTemplate(DaoSearch searchParams);

    @Delete({
            "delete from trace_CertificateTemplateField",
            "where TemplateId = #{templateId}"
    })
    int deleteFieldByTemplateId(@Param("templateId") String templateId);

    @Select(" SELECT * FROM trace_CertificateTemplateField WHERE TemplateId = #{templateId} ")
    List<CertificateTemplateFieldParam> selectFieldByTemplateId(@Param("templateId") String templateId);

    @Select(startScript+
            "SELECT * FROM trace_CertificateTemplate a "
            +startWhere
            + "  AND a.OrganizationId = #{organizationId}  AND a.templateNumber = #{templateNumber} "
            + " <if test='id !=null  '>  AND a.Id != #{id} </if> "
            +endWhere
            +endScript
    )
    List<CertificateTemplate> selectByTemplateNumber(@Param("templateNumber") String templateNumber, @Param("organizationId") String organizationId, @Param("id")Long id);

    @Select(startScript+
            "SELECT * FROM trace_CertificateTemplate a "
            +startWhere
            + "  AND a.OrganizationId = #{organizationId}  AND a.templateName = #{templateName} "
            + " <if test='id !=null  '>  AND a.Id != #{id} </if> "
            +endWhere
            +endScript
    )
    List<CertificateTemplate> selectByTemplateName(@Param("templateName") String templateName, @Param("organizationId") String organizationId, @Param("id")Long id);

}
