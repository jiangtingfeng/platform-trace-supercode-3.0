package com.jgw.supercodeplatform.trace.dao.mapper1.dao;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.TraceBatchnamed;
import com.jgw.supercodeplatform.trace.pojo.TraceBatchnamedExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface TraceBatchnamedMapper extends CommonSql {
    @SelectProvider(type=TraceBatchnamedSqlProvider.class, method="countByExample")
    int countByExample(TraceBatchnamedExample example);

    @DeleteProvider(type=TraceBatchnamedSqlProvider.class, method="deleteByExample")
    int deleteByExample(TraceBatchnamedExample example);

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
        "values (#{id,jdbcType=BIGINT}, #{fieldid,jdbcType=VARCHAR}, ",
        "#{fieldname,jdbcType=VARCHAR}, #{fieldcode,jdbcType=VARCHAR}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{updatedate,jdbcType=TIMESTAMP}, ",
        "#{funid,jdbcType=VARCHAR})"
    })
    int insert(TraceBatchnamed record);

    @InsertProvider(type=TraceBatchnamedSqlProvider.class, method="insertSelective")
    int insertSelective(TraceBatchnamed record);

    @SelectProvider(type=TraceBatchnamedSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="FieldId", property="fieldid", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldName", property="fieldname", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldCode", property="fieldcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateDate", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FunId", property="funid", jdbcType=JdbcType.VARCHAR)
    })
    List<TraceBatchnamed> selectByExample(TraceBatchnamedExample example);

    @Select({
        "select",
        "Id, FieldId, FieldName, FieldCode, CreateDate, UpdateDate, FunId",
        "from trace_batchnamed",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="FieldId", property="fieldid", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldName", property="fieldname", jdbcType=JdbcType.VARCHAR),
        @Result(column="FieldCode", property="fieldcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateDate", property="createdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateDate", property="updatedate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FunId", property="funid", jdbcType=JdbcType.VARCHAR)
    })
    TraceBatchnamed selectByPrimaryKey(Long id);

    @UpdateProvider(type=TraceBatchnamedSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TraceBatchnamed record, @Param("example") TraceBatchnamedExample example);

    @UpdateProvider(type=TraceBatchnamedSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TraceBatchnamed record, @Param("example") TraceBatchnamedExample example);

    @UpdateProvider(type=TraceBatchnamedSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TraceBatchnamed record);

    @Update({
        "update trace_batchnamed",
        "set FieldId = #{fieldid,jdbcType=VARCHAR},",
          "FieldName = #{fieldname,jdbcType=VARCHAR},",
          "FieldCode = #{fieldcode,jdbcType=VARCHAR},",
          "CreateDate = #{createdate,jdbcType=TIMESTAMP},",
          "UpdateDate = #{updatedate,jdbcType=TIMESTAMP},",
          "FunId = #{funid,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TraceBatchnamed record);
}