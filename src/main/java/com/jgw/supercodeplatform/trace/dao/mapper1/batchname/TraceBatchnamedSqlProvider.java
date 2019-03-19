package com.jgw.supercodeplatform.trace.dao.mapper1.batchname;

import com.jgw.supercodeplatform.trace.pojo.tracebatchname.TraceBatchnamed;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class TraceBatchnamedSqlProvider {

    public String insertSelective(TraceBatchnamed record) {
        BEGIN();
        INSERT_INTO("trace_batchnamed");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getFieldId() != null) {
            VALUES("FieldId", "#{fieldId,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldName() != null) {
            VALUES("FieldName", "#{fieldName,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldCode() != null) {
            VALUES("FieldCode", "#{fieldCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            VALUES("CreateDate", "#{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateDate() != null) {
            VALUES("UpdateDate", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFunId() != null) {
            VALUES("FunId", "#{funId,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(TraceBatchnamed record) {
        BEGIN();
        UPDATE("trace_batchnamed");
        
        if (record.getFieldId() != null) {
            SET("FieldId = #{fieldId,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldName() != null) {
            SET("FieldName = #{fieldName,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldCode() != null) {
            SET("FieldCode = #{fieldCode,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateDate() != null) {
            SET("CreateDate = #{createDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateDate() != null) {
            SET("UpdateDate = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFunId() != null) {
            SET("FunId = #{funId,jdbcType=VARCHAR}");
        }
        
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}