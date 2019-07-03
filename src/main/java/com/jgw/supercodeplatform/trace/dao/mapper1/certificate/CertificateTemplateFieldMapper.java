package com.jgw.supercodeplatform.trace.dao.mapper1.certificate;

import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateTemplateField;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CertificateTemplateFieldMapper {
    @Delete({
            "delete from trace_CertificateTemplateField",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into trace_CertificateTemplateField (Id, TemplateId, ",
            "FieldType, FieldText, ",
            "FontSize, FontWeight, ",
            "TextDecoration, MarginLeft, ",
            "MarginTop, Width, ",
            "Height, FieldIndex, ",
            "CreateId, CreateTime)",
            "values (#{id,jdbcType=BIGINT}, #{templateId,jdbcType=VARCHAR}, ",
            "#{fieldType,jdbcType=INTEGER}, #{fieldText,jdbcType=VARCHAR}, ",
            "#{fontSize,jdbcType=INTEGER}, #{fontWeight,jdbcType=VARCHAR}, ",
            "#{textDecoration,jdbcType=VARCHAR}, #{marginLeft,jdbcType=INTEGER}, ",
            "#{marginTop,jdbcType=INTEGER}, #{width,jdbcType=INTEGER}, ",
            "#{height,jdbcType=INTEGER}, #{fieldIndex,jdbcType=INTEGER}, ",
            "#{createId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(CertificateTemplateField record);


    @Select({
            "select",
            "Id, TemplateId, FieldType, FieldText, FontSize, FontWeight, TextDecoration, ",
            "MarginLeft, MarginTop, Width, Height, FieldIndex, CreateId, CreateTime",
            "from trace_CertificateTemplateField",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
            @Result(column="FieldType", property="fieldType", jdbcType=JdbcType.INTEGER),
            @Result(column="FieldText", property="fieldText", jdbcType=JdbcType.VARCHAR),
            @Result(column="FontSize", property="fontSize", jdbcType=JdbcType.INTEGER),
            @Result(column="FontWeight", property="fontWeight", jdbcType=JdbcType.VARCHAR),
            @Result(column="TextDecoration", property="textDecoration", jdbcType=JdbcType.VARCHAR),
            @Result(column="MarginLeft", property="marginLeft", jdbcType=JdbcType.INTEGER),
            @Result(column="MarginTop", property="marginTop", jdbcType=JdbcType.INTEGER),
            @Result(column="Width", property="width", jdbcType=JdbcType.INTEGER),
            @Result(column="Height", property="height", jdbcType=JdbcType.INTEGER),
            @Result(column="FieldIndex", property="fieldIndex", jdbcType=JdbcType.INTEGER),
            @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    CertificateTemplateField selectByPrimaryKey(Long id);


    @Update({
            "update trace_CertificateTemplateField",
            "set TemplateId = #{templateId,jdbcType=VARCHAR},",
            "FieldType = #{fieldType,jdbcType=INTEGER},",
            "FieldText = #{fieldText,jdbcType=VARCHAR},",
            "FontSize = #{fontSize,jdbcType=INTEGER},",
            "FontWeight = #{fontWeight,jdbcType=VARCHAR},",
            "TextDecoration = #{textDecoration,jdbcType=VARCHAR},",
            "MarginLeft = #{marginLeft,jdbcType=INTEGER},",
            "MarginTop = #{marginTop,jdbcType=INTEGER},",
            "Width = #{width,jdbcType=INTEGER},",
            "Height = #{height,jdbcType=INTEGER},",
            "FieldIndex = #{fieldIndex,jdbcType=INTEGER},",
            "CreateId = #{createId,jdbcType=VARCHAR},",
            "CreateTime = #{createTime,jdbcType=TIMESTAMP}",
            "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CertificateTemplateField record);
}