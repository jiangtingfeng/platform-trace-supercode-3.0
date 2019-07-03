package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface  BatchInfoMapper {


    @Select("SELECT * FROM trace_fun_config c WHERE FieldCode=#{fieldCode} and  c.FunctionId in (\n" +
            "\tSELECT ComponentId FROM trace_fun_component\n" +
            "\tWHERE funid in (\n" +
            "\t\tSELECT NodeFunctionId FROM trace_fun_templateconfig WHERE TraceTemplateId in (\n" +
            "\t\t\tSELECT TraceTemplateId FROM trace_funtemplatestatistical\n" +
            "\t\t\twhere OrganizationId=#{organizationId} \n" +
            "\t\t)\n" +
            "\t)\n" +
            ") limit 0,1")
    TraceFunFieldConfig selectByNestCompentFieldCode(@Param("organizationId") String organizationId,@Param("fieldCode")String fieldCode);


    @Select("SELECT fc.*\n" +
            "FROM trace_funtemplatestatistical t\n" +
            "inner join trace_fun_templateconfig  tc on t.TraceTemplateId = tc.TraceTemplateId\n" +
            "inner join trace_fun_config fc on fc.FunctionId = tc.NodeFunctionId and fc.TraceTemplateId = tc.TraceTemplateId\n" +
            "where t.OrganizationId=#{organizationId} and FieldCode=#{fieldCode}  limit 0,1 "
            )
    TraceFunFieldConfig selectByFieldCode(@Param("organizationId") String organizationId,@Param("fieldCode")String fieldCode);
}
