package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunComponent;
import com.jgw.supercodeplatform.trace.dao.CommonSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TraceFunComponentMapper extends CommonSql {

    static String allFields=" ComponentId componentId, FunId funId, ComponentType componentType, CreateDate createDate, UpdateDate updateDate, TableName tableName, OrganizationId organizationId, ComponentName componentName";


    @Insert(startScript
            +" INSERT INTO trace_fun_component"
            + "<trim prefix='(' suffix=')' suffixOverrides=','>"
                + "<if test='componentId != null '>ComponentId,</if> "
                + "<if test='funId != null '>FunId,</if> "
                + "<if test='componentType != null '>ComponentType,</if> "
                + "<if test='tableName != null '>TableName,</if> "
                + "<if test='organizationId != null '>OrganizationId,</if> "
                + "<if test='componentName != null '>ComponentName,</if> "
                + "CreateDate, "
                + "UpdateDate "
            + "</trim>"
            + "<trim prefix='values (' suffix=')' suffixOverrides=','>"
                + "<if test='componentId != null '>#{componentId},</if> "
                + "<if test='funId != null '>#{funId},</if> "
                + "<if test='componentType != null '>#{componentType},</if> "
                + "<if test='tableName != null '>#{tableName},</if> "
                + "<if test='organizationId != null '>#{organizationId},</if> "
                + "<if test='componentName != null '>#{componentName},</if> "
                + "now(), "
                + "now() "
            + "</trim>"
            + endScript)
    int insertTraceFunComponent(TraceFunComponent traceFunComponent);


    @Select("SELECT "+allFields+" FROM trace_fun_component where FunId=#{funId}")
    List<TraceFunComponent> selectByFunId(@Param("funId")String funId);

    @Delete(" DELETE FROM trace_fun_component WHERE where FunId=#{funId}")
    int deleteTraceFunComponent(@Param("funId")String funId);

}
