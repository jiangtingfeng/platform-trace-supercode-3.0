package com.jgw.supercodeplatform.trace.dao.mapper1.batchname;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracebatchname.TraceBatchnamed;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface TraceBatchnamedMapper extends CommonSql {
    @Delete({
        "delete from trace_batchnamed",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into trace_batchnamed (Id, FieldId, ",
        "FieldName, FieldCode, ",
        "CreateDate, UpdateDate, ",
        "FunId)",
        "values (#{id,jdbcType=BIGINT}, #{fieldId,jdbcType=VARCHAR}, ",
        "#{fieldName,jdbcType=VARCHAR}, #{fieldCode,jdbcType=VARCHAR}, ",
        "#{createDate,jdbcType=TIMESTAMP}, #{updateDate,jdbcType=TIMESTAMP}, ",
        "#{funId,jdbcType=VARCHAR})"
    })
    int insert(TraceBatchnamed record);

    @InsertProvider(type= TraceBatchnamedSqlProvider.class, method="insertSelective")
    int insertSelective(TraceBatchnamed record);

    @Select({
        "select",
        "Id, FieldId, FieldName, FieldCode, CreateDate, UpdateDate, FunId",
        "from trace_batchnamed",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="FieldId", property="fieldId", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldName", property="fieldName", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldCode", property="fieldCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateDate", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FunId", property="funId", jdbcType=JdbcType.VARCHAR)
    })
    TraceBatchnamed selectByPrimaryKey(Long id);

    @Select({
            "select",
            "Id, FieldId, FieldName, FieldCode, CreateDate, UpdateDate, FunId",
            "from trace_batchnamed",
            "where FunId = #{funId}"
    })
    @Results({
            @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="FieldId", property="fieldId", jdbcType=JdbcType.VARCHAR),
            @Result(column="FieldName", property="fieldName", jdbcType=JdbcType.VARCHAR),
            @Result(column="FieldCode", property="fieldCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateDate", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="UpdateDate", property="updateDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="FunId", property="funId", jdbcType=JdbcType.VARCHAR)
    })
    TraceBatchnamed select(String funId);

    @UpdateProvider(type= TraceBatchnamedSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TraceBatchnamed record);

    @Update({
        "update trace_batchnamed",
        "set FieldId = #{fieldId,jdbcType=VARCHAR},",
          "FieldName = #{fieldName,jdbcType=VARCHAR},",
          "FieldCode = #{fieldCode,jdbcType=VARCHAR},",
          "CreateDate = #{createDate,jdbcType=TIMESTAMP},",
          "UpdateDate = #{updateDate,jdbcType=TIMESTAMP},",
          "FunId = #{funId,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TraceBatchnamed record);
}