package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificatePrintInfo;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CertificatePrintInfoMapper extends CommonSql {
    @Delete({
        "delete from trace_CertificatePrintInfo",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into trace_CertificatePrintInfo (Id, CertificateInfoId, ",
        "PrintQuantity, CreateId, ",
        "CreateTime, CreateMan, ",
        "OrganizationId, PrintDrive, CertificateInfoData,CertificateNumber,CertificateName,CertificateId,TemplateId)",
        "values (#{id,jdbcType=BIGINT}, #{certificateInfoId,jdbcType=VARCHAR}, ",
        "#{printQuantity,jdbcType=INTEGER}, #{createId,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createMan,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{printDrive,jdbcType=VARCHAR}, #{certificateInfoData,jdbcType=VARCHAR}," +
        "#{certificateNumber,jdbcType=VARCHAR}, #{certificateName,jdbcType=VARCHAR}, #{certificateId},#{templateId} ) "
    })
    int insert(CertificatePrintInfo record);

    @Select({
        "select",
        "Id, CertificateInfoId, PrintQuantity, CreateId, CreateTime, CreateMan, OrganizationId,PrintDrive ",
        "from trace_CertificatePrintInfo",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CertificateInfoId", property="certificateInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PrintQuantity", property="printQuantity", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PrintDrive", property="printDrive", jdbcType=JdbcType.VARCHAR)
    })
    CertificatePrintInfo selectByPrimaryKey(Long id);

    @Select({
        "select",
        "Id, CertificateInfoId, PrintQuantity, CreateId, CreateTime, CreateMan, OrganizationId",
        "from trace_CertificatePrintInfo"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CertificateInfoId", property="certificateInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PrintQuantity", property="printQuantity", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR)
    })
    List<CertificatePrintInfo> selectAll();

    @Update({
        "update trace_CertificatePrintInfo",
        "set CertificateInfoId = #{certificateInfoId,jdbcType=VARCHAR},",
          "PrintQuantity = #{printQuantity,jdbcType=INTEGER},",
          "CreateId = #{createId,jdbcType=VARCHAR},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP},",
          "CreateMan = #{createMan,jdbcType=VARCHAR},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CertificatePrintInfo record);

    @Select(" select PrintDrive from trace_CertificatePrintInfo a " +
            " where CreateId = #{createId} order by CreateTime desc limit 0,1 ")
    String selectUserLastPrintDrive(@Param("createId") String createId);

}