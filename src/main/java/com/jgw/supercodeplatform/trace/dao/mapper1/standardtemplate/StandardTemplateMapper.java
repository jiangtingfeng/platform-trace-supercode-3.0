package com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface StandardTemplateMapper extends CommonSql {
    @Delete({
        "delete from trace_StandardTemplate",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into trace_StandardTemplate (Id, TemplateId, ",
        "CategoryId, CategoryName, ",
        "Level1TypeId, Level1TypeName, ",
        "Level2TypeId, Level2TypeName, ",
        "TemplateNumber, DisableFlag, ",
        "CreateId, CreateMan, ",
        "CreateTime, UpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{templateId,jdbcType=VARCHAR}, ",
        "#{categoryId,jdbcType=INTEGER}, #{categoryName,jdbcType=VARCHAR}, ",
        "#{level1TypeId,jdbcType=INTEGER}, #{level1TypeName,jdbcType=VARCHAR}, ",
        "#{level2TypeId,jdbcType=INTEGER}, #{level2TypeName,jdbcType=VARCHAR}, ",
        "#{templateNumber,jdbcType=VARCHAR}, #{disableFlag,jdbcType=INTEGER}, ",
        "#{createId,jdbcType=VARCHAR}, #{createMan,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(StandardTemplate record);

    @Select({
        "select",
        "Id, TemplateId, CategoryId, CategoryName, Level1TypeId, Level1TypeName, Level2TypeId, ",
        "Level2TypeName, TemplateNumber, DisableFlag, CreateId, CreateMan, CreateTime, ",
        "UpdateTime",
        "from trace_StandardTemplate",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CategoryId", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="CategoryName", property="categoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Level1TypeId", property="level1TypeId", jdbcType=JdbcType.INTEGER),
        @Result(column="Level1TypeName", property="level1TypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Level2TypeId", property="level2TypeId", jdbcType=JdbcType.INTEGER),
        @Result(column="Level2TypeName", property="level2TypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateNumber", property="templateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    StandardTemplate selectByPrimaryKey(Long id);

    @Select({
        "select",
        "Id, TemplateId, CategoryId, CategoryName, Level1TypeId, Level1TypeName, Level2TypeId, ",
        "Level2TypeName, TemplateNumber, DisableFlag, CreateId, CreateMan, CreateTime, ",
        "UpdateTime",
        "from trace_StandardTemplate"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateId", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CategoryId", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="CategoryName", property="categoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Level1TypeId", property="level1TypeId", jdbcType=JdbcType.INTEGER),
        @Result(column="Level1TypeName", property="level1TypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Level2TypeId", property="level2TypeId", jdbcType=JdbcType.INTEGER),
        @Result(column="Level2TypeName", property="level2TypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="TemplateNumber", property="templateNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<StandardTemplate> selectAll();

    @Update({
        "update trace_StandardTemplate",
        "set ",
          "CategoryId = #{categoryId,jdbcType=INTEGER},",
          "CategoryName = #{categoryName,jdbcType=VARCHAR},",
          "Level1TypeId = #{level1TypeId,jdbcType=INTEGER},",
          "Level1TypeName = #{level1TypeName,jdbcType=VARCHAR},",
          "Level2TypeId = #{level2TypeId,jdbcType=INTEGER},",
          "Level2TypeName = #{level2TypeName,jdbcType=VARCHAR},",
          "TemplateNumber = #{templateNumber,jdbcType=VARCHAR},",
          "UpdateTime = now()",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StandardTemplate record);
}