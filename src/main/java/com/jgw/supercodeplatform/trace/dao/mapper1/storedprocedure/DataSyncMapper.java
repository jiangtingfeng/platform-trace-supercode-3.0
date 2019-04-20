package com.jgw.supercodeplatform.trace.dao.mapper1.storedprocedure;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Map;

@Mapper
public interface DataSyncMapper {

    @Select({ "call p_datasync(#{startdate,mode=IN,jdbcType=DATE})" })
    @Options(statementType= StatementType.CALLABLE)
    void execute(Map<String,String> params);

}
