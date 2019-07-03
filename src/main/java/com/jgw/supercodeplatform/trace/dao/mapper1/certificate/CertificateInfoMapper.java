package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.dto.certificate.CertificateInfoParam;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CertificateInfoMapper extends CommonSql {
    @Delete({
        "delete from trace_CertificateInfo",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="Id")
    @Insert({
        "insert into trace_CertificateInfo (Id, CertificateInfoId, ",
        "CertificateNumber, CertificateName, ",
        "TemplateId, TemplateName, ",
        "CreateId, CreateMan, ",
        "CreateTime, UpdateTime, ",
        "DisableFlag, CertificateInfoData, ",
        "OrganizationId)",
        "values (#{id,jdbcType=BIGINT}, #{certificateInfoId,jdbcType=VARCHAR}, ",
        "#{certificateNumber,jdbcType=VARCHAR}, #{certificateName,jdbcType=VARCHAR}, ",
        "#{templateId,jdbcType=VARCHAR}, #{templateName,jdbcType=VARCHAR}, ",
        "#{createId,jdbcType=VARCHAR}, #{createMan,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{disableFlag,jdbcType=INTEGER}, #{certificateInfoData,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR})"
    })
    int insert(CertificateInfo record);

    @Select({
        "select",
        "c.Id, CertificateInfoId, CertificateNumber, CertificateName, c.TemplateId, c.TemplateName, c.CreateId, c.CreateMan, c.CreateTime, c.UpdateTime, DisableFlag, CertificateInfoData, c.OrganizationId , t.PrintWidth , t.PrintHeight  ",
        "from trace_CertificateInfo c inner join trace_CertificateTemplate t  on c.TemplateId = t.TemplateId ",
        "where c.Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CertificateInfoId", property="certificateInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CertificateNumber", property="certificateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CertificateName", property="certificateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateName", property="templateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="CertificateInfoData", property="certificateInfoData", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR)
    })
    CertificateInfoParam selectByPrimaryKey(Long id);

    @Select({
        "select",
        "Id, CertificateInfoId, CertificateNumber, CertificateName, TemplateId, TemplateName, ",
        "CreateId, CreateMan, CreateTime, UpdateTime, DisableFlag, CertificateInfoData, ",
        "OrganizationId",
        "from trace_CertificateInfo"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CertificateInfoId", property="certificateInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CertificateNumber", property="certificateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CertificateName", property="certificateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateName", property="templateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="CertificateInfoData", property="certificateInfoData", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR)
    })
    List<CertificateInfo> selectAll();

    @Update({startScript ,
        "update trace_CertificateInfo",
        "set ",
          " <if test='certificateNumber != null and certificateNumber != &apos;&apos;'>  CertificateNumber = #{certificateNumber,jdbcType=VARCHAR}, </if>",
          " <if test='certificateName != null and certificateName != &apos;&apos;'>  CertificateName = #{certificateName,jdbcType=VARCHAR},  </if>",
          " <if test='templateId != null and templateId != &apos;&apos;'>  TemplateId = #{templateId,jdbcType=VARCHAR}, </if> ",
          " <if test='templateName != null and templateName != &apos;&apos;'>  TemplateName = #{templateName,jdbcType=VARCHAR},  </if> ",
          " <if test='certificateInfoData != null and certificateInfoData != &apos;&apos;'>  CertificateInfoData = #{certificateInfoData,jdbcType=VARCHAR}  </if>",
        "where Id = #{id,jdbcType=BIGINT}",
        endScript
    })
    int updateByPrimaryKey(CertificateInfo record);
    @Update({startScript ,
            "update trace_CertificateInfo",
            "set ",
            " <if test='certificateInfoData != null and certificateInfoData != &apos;&apos;'>  CertificateInfoData = #{certificateInfoData,jdbcType=VARCHAR}  </if>",
            "where TemplateId = #{templateId,jdbcType=VARCHAR}",
            endScript
    })
    int updateCertificateInfoWhenChangeTemplate(String templateId, String certificateInfoData);
}