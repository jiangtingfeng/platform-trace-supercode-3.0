package com.jgw.supercodeplatform.trace.dao.mapper1.codemanager;

import com.jgw.supercodeplatform.trace.pojo.codemanager.TraceCodeAssociated;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface TraceCodeAssociatedMapper {
    @Delete({
        "delete from trace_code_associated",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);


    @InsertProvider(type=TraceCodeAssociatedSqlProvider.class, method="insertSelective")
    int insertSelective(TraceCodeAssociated record);

    @Select({
        "select",
        "Id, ProductId, ProductName, Associated, StartNumber, EndNumber, CodeTotal, CreateTime, ",
        "UpdateTime, OrganizationId, SBatchId, TraceBatchInfoId, TraceBatchName",
        "from trace_code_associated",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductName", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="Associated", property="associated", jdbcType=JdbcType.BIT),
        @Result(column="StartNumber", property="startNumber", jdbcType=JdbcType.BIGINT),
        @Result(column="EndNumber", property="endNumber", jdbcType=JdbcType.BIGINT),
        @Result(column="CodeTotal", property="codeTotal", jdbcType=JdbcType.BIGINT),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SBatchId", property="SBatchId", jdbcType=JdbcType.BIGINT),
        @Result(column="TraceBatchInfoId", property="traceBatchInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TraceBatchName", property="traceBatchName", jdbcType=JdbcType.VARCHAR)
    })
    TraceCodeAssociated selectByPrimaryKey(Long id);

    @UpdateProvider(type=TraceCodeAssociatedSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TraceCodeAssociated record);



}