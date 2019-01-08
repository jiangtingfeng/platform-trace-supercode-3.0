package com.jgw.supercodeplatform.trace.dao.mapper1.codemanager;

import com.jgw.supercodeplatform.trace.pojo.codemanager.TraceCodeAssociated;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class TraceCodeAssociatedSqlProvider {

    public String insertSelective(TraceCodeAssociated record) {
        BEGIN();
        INSERT_INTO("trace_code_associated");
        
        if (record.getId() != null) {
            VALUES("Id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            VALUES("ProductId", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            VALUES("ProductName", "#{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getAssociated() != null) {
            VALUES("Associated", "#{associated,jdbcType=BIT}");
        }
        
        if (record.getStartNumber() != null) {
            VALUES("StartNumber", "#{startNumber,jdbcType=BIGINT}");
        }
        
        if (record.getEndNumber() != null) {
            VALUES("EndNumber", "#{endNumber,jdbcType=BIGINT}");
        }
        
        if (record.getCodeTotal() != null) {
            VALUES("CodeTotal", "#{codeTotal,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("CreateTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("UpdateTime", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOrganizationId() != null) {
            VALUES("OrganizationId", "#{organizationId,jdbcType=VARCHAR}");
        }
        
        if (record.getSBatchId() != null) {
            VALUES("SBatchId", "#{SBatchId,jdbcType=BIGINT}");
        }
        
        if (record.getTraceBatchInfoId() != null) {
            VALUES("TraceBatchInfoId", "#{traceBatchInfoId,jdbcType=VARCHAR}");
        }
        
        if (record.getTraceBatchName() != null) {
            VALUES("TraceBatchName", "#{traceBatchName,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(TraceCodeAssociated record) {
        BEGIN();
        UPDATE("trace_code_associated");
        
        if (record.getProductId() != null) {
            SET("ProductId = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getProductName() != null) {
            SET("ProductName = #{productName,jdbcType=VARCHAR}");
        }
        
        if (record.getAssociated() != null) {
            SET("Associated = #{associated,jdbcType=BIT}");
        }
        
        if (record.getStartNumber() != null) {
            SET("StartNumber = #{startNumber,jdbcType=BIGINT}");
        }
        
        if (record.getEndNumber() != null) {
            SET("EndNumber = #{endNumber,jdbcType=BIGINT}");
        }
        
        if (record.getCodeTotal() != null) {
            SET("CodeTotal = #{codeTotal,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            SET("CreateTime = #{createTime,jdbcType=TIMESTAMP}");
        }


        if (record.getOrganizationId() != null) {
            SET("OrganizationId = #{organizationId,jdbcType=VARCHAR}");
        }

        if (record.getSBatchId() != null) {
            SET("SBatchId = #{SBatchId,jdbcType=BIGINT}");
        }

        if (record.getTraceBatchInfoId() != null) {
            SET("TraceBatchInfoId = #{traceBatchInfoId,jdbcType=VARCHAR}");
        }

        if (record.getTraceBatchName() != null) {
            SET("TraceBatchName = #{traceBatchName,jdbcType=VARCHAR}");
        }

        SET("UpdateTime = now()");
        WHERE("Id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}