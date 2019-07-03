package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CertificateTemplateMapper extends CommonSql {
    @Delete({
        "delete from trace_CertificateTemplate",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="Id")
    @Insert({
        "insert into trace_CertificateTemplate (Id, TemplateId, ",
        "TemplateNumber, TemplateName, ",
        "PrintWidth, PrintHeight, ",
        "CreateId, CreateMan, ",
        "CreateTime, UpdateTime, ",
        "OrganizationId)",
        "values (#{id,jdbcType=BIGINT}, #{templateId,jdbcType=VARCHAR}, ",
        "#{templateNumber,jdbcType=VARCHAR}, #{templateName,jdbcType=VARCHAR}, ",
        "#{printWidth,jdbcType=INTEGER}, #{printHeight,jdbcType=INTEGER}, ",
        "#{createId,jdbcType=VARCHAR}, #{createMan,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{organizationId,jdbcType=VARCHAR})"
    })
    int insert(CertificateTemplate record);

    @Select({
        "select",
        "Id, TemplateId, TemplateNumber, TemplateName, PrintWidth, PrintHeight, CreateId, ",
        "CreateMan, CreateTime, UpdateTime, OrganizationId",
        "from trace_CertificateTemplate",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateNumber", property="templateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateName", property="templateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="PrintWidth", property="printWidth", jdbcType=JdbcType.INTEGER),
        @Result(column="PrintHeight", property="printHeight", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR)
    })
    CertificateTemplate selectByPrimaryKey(Long id);

    @Select({
        "select",
        "Id, TemplateId, TemplateNumber, TemplateName, PrintWidth, PrintHeight, CreateId, ",
        "CreateMan, CreateTime, UpdateTime, OrganizationId",
        "from trace_CertificateTemplate"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateNumber", property="templateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateName", property="templateName", jdbcType=JdbcType.VARCHAR),
        @Result(column="PrintWidth", property="printWidth", jdbcType=JdbcType.INTEGER),
        @Result(column="PrintHeight", property="printHeight", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR)
    })
    List<CertificateTemplate> selectAll();

    @Update({startScript,
        "update trace_CertificateTemplate",
        "<set> ",
            " <if test='templateId !=null and templateId != &apos;&apos; '>  TemplateId = #{templateId} ,</if> ",
            " <if test='templateNumber !=null and templateNumber != &apos;&apos; '>  TemplateNumber = #{templateNumber} ,</if> ",
            " <if test='templateName !=null and templateName != &apos;&apos; '>  TemplateName = #{templateName} ,</if> ",
            " <if test='printWidth !=null  '>  PrintWidth = #{printWidth} ,</if> ",
            " <if test='printHeight !=null '>  PrintHeight = #{printHeight} ,</if> ",
            " <if test='createId !=null and createId != &apos;&apos; '>  CreateId = #{createId} ,</if> ",
            " <if test='createMan !=null and createMan != &apos;&apos; '>  CreateMan = #{createMan} ,</if> ",
            " <if test='organizationId !=null and organizationId != &apos;&apos; '>  OrganizationId = #{organizationId} ,</if> ",
            " UpdateTime = now() ",
        "</set> ",

            "where Id = #{id,jdbcType=BIGINT}",
            endScript
    })
    int updateByPrimaryKey(CertificateTemplate record);
}