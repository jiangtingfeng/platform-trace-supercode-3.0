package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface  BatchInfoMapper {


    @Select("SELECT * FROM trace_fun_config c WHERE FieldCode='PackingSpec' and  c.FunctionId in (\n" +
            "\tSELECT ComponentId FROM trace_fun_component\n" +
            "\tWHERE funid in (\n" +
            "\t\tSELECT NodeFunctionId FROM trace_fun_templateconfig WHERE TraceTemplateId in (\n" +
            "\t\t\tSELECT TraceTemplateId FROM trace_funtemplatestatistical\n" +
            "\t\t\twhere OrganizationId=#{organizationId} \n" +
            "\t\t)\n" +
            "\t)\n" +
            ") limit 0,1")
    TraceFunFieldConfig selectByFieldCode(@Param("organizationId") String organizationId);



}
