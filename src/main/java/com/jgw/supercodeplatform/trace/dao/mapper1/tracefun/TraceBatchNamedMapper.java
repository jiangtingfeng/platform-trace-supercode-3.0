package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchNamed;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceBatchRelation;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunComponent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TraceBatchNamedMapper  extends CommonSql {

    /**
     * 根据功能id获取批次命名规则
     * @param funId
     * @return
     */
    @Select("SELECT * FROM trace_batchnamed where FunId=#{funId}")
    List<TraceBatchNamed> selectByFunId(@Param("funId")String funId);

    @Delete(" DELETE FROM trace_batchnamed WHERE where FunId=#{funId}")
    int deleteTraceBatchNamed(@Param("funId")String funId);

    @Insert("INSERT INTO trace_batchnamed" +
            "(`FieldId`,`FieldName`,`FieldCode`,`FunId`,`CreateDate`,`UpdateDate`,'fieldFormat','disableFlag')" +
            "VALUES" +
            "( #{fieldId},#{fieldName},#{fieldCode},#{funId},now(),now(),#{fieldFormat},#{disableFlag})" +
            "")
    int insertTraceBatchNamed(TraceBatchNamed traceBatchNamed);
}
