package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CertificateInfoExMapper extends CertificateInfoMapper {

    @Select(startScript+
            " SELECT COUNT(*) FROM trace_CertificateInfo a  "+
            "<where>"+
            " a.OrganizationId = #{params.organizationId}   "+
            "<if test='search != null and search != &apos;&apos;'> and (" +
            " certificateName LIKE CONCAT('%',#{search},'%')" +
            " or TemplateName LIKE CONCAT('%',#{search},'%')" +
            " or CertificateNumber LIKE CONCAT('%',#{search},'%')  " +
            ") </if>"+
            "</where>"+
            endScript
    )
    int countCertificateInfo(DaoSearch searchParams);

    @Select(startScript+
            " SELECT * FROM trace_CertificateInfo a  " +
            "<where>"+
            " a.OrganizationId = #{params.organizationId} "+
            "<if test='search != null and search != &apos;&apos;'> and (" +
            " certificateName LIKE CONCAT('%',#{search},'%')" +
            " or TemplateName LIKE CONCAT('%',#{search},'%')" +
            " or CertificateNumber LIKE CONCAT('%',#{search},'%')  " +
            ") </if>"+
            "</where>"+
            orderBy+
            page+
            endScript)
    List<CertificateInfo> listCertificateInfo(DaoSearch searchParams);

    @Update("UPDATE trace_CertificateInfo SET DisableFlag = #{disableFlag} WHERE ID = #{id}")
    void updateDisableFlag(@Param("disableFlag")Integer disableFlag,@Param("id")Long id);

    @Select(startScript+
            "SELECT * FROM trace_CertificateInfo a "
            +startWhere
            + "  AND a.OrganizationId = #{organizationId}  AND a.certificateNumber = #{certificateNumber} "
            + " <if test='id !=null  '>  AND a.Id != #{id} </if> "
            +endWhere
            +endScript
    )
    List<CertificateInfo> selectByCertificateNumber(@Param("certificateNumber") String certificateNumber, @Param("organizationId") String organizationId, @Param("id")Long id);

    @Select(startScript+
            "SELECT * FROM trace_CertificateInfo a "
            +startWhere
            + "  AND a.OrganizationId = #{organizationId}  AND a.certificateName = #{certificateName} "
            + " <if test='id !=null  '>  AND a.Id != #{id} </if> "
            +endWhere
            +endScript
    )
    List<CertificateInfo> selectByCertificateName(@Param("certificateName") String certificateName, @Param("organizationId") String organizationId, @Param("id")Long id);
    @Select(startScript+
            "SELECT * FROM trace_CertificateInfo a "
            +startWhere
            + "  a.OrganizationId = #{organizationId}  AND a.CertificateInfoId = #{certificateInfoId} "
            +endWhere
            +endScript
    )
    CertificateInfo selectByCertificateInfoId(@Param("certificateInfoId") String certificateInfoId,@Param("organizationId")String organizationId);
}
