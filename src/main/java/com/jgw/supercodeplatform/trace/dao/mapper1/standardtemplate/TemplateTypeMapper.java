package com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface TemplateTypeMapper extends CommonSql {
    @Delete({
        "delete from trace_TemplateType",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into trace_TemplateType (Id, TemplateTypeId, ",
        "TypeName, CategoryId, ",
        "CategoryName, LevelId, ",
        "LevelName, ParentId, ",
        "ParentName, Sort, ",
        "CreateId, CreateMan, ",
        "CreateTime, UpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{templateTypeId,jdbcType=VARCHAR}, ",
        "#{typeName,jdbcType=VARCHAR}, #{categoryId,jdbcType=INTEGER}, ",
        "#{categoryName,jdbcType=VARCHAR}, #{levelId,jdbcType=INTEGER}, ",
        "#{levelName,jdbcType=VARCHAR}, #{parentId,jdbcType=BIGINT}, ",
        "#{parentName,jdbcType=VARCHAR}, #{sort,jdbcType=INTEGER}, ",
        "#{createId,jdbcType=VARCHAR}, #{createMan,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(TemplateType record);

    @Select({
        "select",
        "Id, TemplateTypeId, TypeName, CategoryId, CategoryName, LevelId, LevelName, ",
        "ParentId, ParentName, Sort, CreateId, CreateMan, CreateTime, UpdateTime",
        "from trace_TemplateType",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateTypeId", property="templateTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TypeName", property="typeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CategoryId", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="CategoryName", property="categoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="LevelId", property="levelId", jdbcType=JdbcType.INTEGER),
        @Result(column="LevelName", property="levelName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ParentId", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="ParentName", property="parentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Sort", property="sort", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TemplateType selectByPrimaryKey(Long id);

    @Select({
        "select",
        "Id, TemplateTypeId, TypeName, CategoryId, CategoryName, LevelId, LevelName, ",
        "ParentId, ParentName, Sort, CreateId, CreateMan, CreateTime, UpdateTime",
        "from trace_TemplateType"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="TemplateTypeId", property="templateTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TypeName", property="typeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CategoryId", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="CategoryName", property="categoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="LevelId", property="levelId", jdbcType=JdbcType.INTEGER),
        @Result(column="LevelName", property="levelName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ParentId", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="ParentName", property="parentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Sort", property="sort", jdbcType=JdbcType.INTEGER),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TemplateType> selectAll();

    @Update({
        "update trace_TemplateType",
        "set ",
          "TypeName = #{typeName,jdbcType=VARCHAR},",
          "CategoryId = #{categoryId,jdbcType=INTEGER},",
          "CategoryName = #{categoryName,jdbcType=VARCHAR},",
          "LevelId = #{levelId,jdbcType=INTEGER},",
          "LevelName = #{levelName,jdbcType=VARCHAR},",
          "ParentId = #{parentId,jdbcType=BIGINT},",
          "ParentName = #{parentName,jdbcType=VARCHAR},",
          "Sort = #{sort,jdbcType=INTEGER},",
          "UpdateTime = #{updateTime,jdbcType=TIMESTAMP}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TemplateType record);
}