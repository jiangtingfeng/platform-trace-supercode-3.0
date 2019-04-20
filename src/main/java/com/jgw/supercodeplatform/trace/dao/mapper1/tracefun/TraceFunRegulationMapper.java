package com.jgw.supercodeplatform.trace.dao.mapper1.tracefun;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunComponent;
import com.jgw.supercodeplatform.trace.pojo.tracefun.TraceFunRegulation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TraceFunRegulationMapper extends CommonSql {

    static String allFields="RegulationId regulationId, FunId funId, ObjectAssociatedType objectAssociatedType, RegulationType regulationType, MultipleInput multipleInput, UseSceneType useSceneType, BatchNamingLinkCharacter batchNamingLinkCharacter, "
            +"BatchNamingRule batchNamingRule, BatchTimeControl batchTimeControl, CreateDate createDate, UpdateDate updateDate, CreateBatchType createBatchType, SplittingRule splittingRule, FunctionName functionName,LayoutType layoutType";


    @Insert(startScript
            +" INSERT INTO trace_fun_regulation"
            + "<trim prefix='(' suffix=')' suffixOverrides=','>"
                + "<if test='regulationId  != null '>RegulationId,</if> "
                + "<if test='funId  != null '>FunId,</if> "
                + "<if test='objectAssociatedType  != null '>ObjectAssociatedType,</if> "
                + "<if test='regulationType  != null '>RegulationType,</if> "
                + "<if test='multipleInput  != null '>MultipleInput,</if> "
                + "<if test='useSceneType  != null '>UseSceneType,</if> "
                + "<if test='batchNamingLinkCharacter  != null '>BatchNamingLinkCharacter,</if> "
                + "<if test='batchNamingRule  != null '>BatchNamingRule,</if> "
                + "<if test='batchTimeControl  != null '>BatchTimeControl,</if> "
                + "<if test='createBatchType  != null '>CreateBatchType,</if> "
                + "<if test='splittingRule  != null '>SplittingRule,</if> "
                + "<if test='layoutType  != null '>LayoutType,</if> "
                + "<if test='functionName  != null '>FunctionName,</if> "
                + "CreateDate, "
                + "UpdateDate "
            + "</trim>"
            + "<trim prefix='values (' suffix=')' suffixOverrides=','>"
                + "<if test='regulationId != null '>#{regulationId},</if> "
                + "<if test='funId != null '>#{funId},</if> "
                + "<if test='objectAssociatedType != null '>#{objectAssociatedType},</if> "
                + "<if test='regulationType != null '>#{regulationType},</if> "
                + "<if test='multipleInput != null '>#{multipleInput},</if> "
                + "<if test='useSceneType != null '>#{useSceneType},</if> "
                + "<if test='batchNamingLinkCharacter != null '>#{batchNamingLinkCharacter},</if> "
                + "<if test='batchNamingRule != null '>#{batchNamingRule},</if> "
                + "<if test='batchTimeControl != null '>#{batchTimeControl},</if> "
                + "<if test='createBatchType != null '>#{createBatchType},</if> "
                + "<if test='splittingRule != null '>#{splittingRule},</if> "
                + "<if test='layoutType != null '>#{layoutType},</if> "
                + "<if test='functionName != null '>#{functionName},</if> "
                + "now(), "
                + "now() "
            + "</trim>"
            + endScript)
    int insertTraceFunRegulation(TraceFunRegulation traceFunRegulation);


    /**
     * 根据功能id获取定制功能使用规则
     * @param funId
     * @return
     */
    @Select("SELECT "+allFields+" FROM trace_fun_regulation  where FunId=#{funId}")
    TraceFunRegulation selectByFunId(@Param("funId")String funId);

    @Delete(" DELETE FROM trace_fun_regulation WHERE  FunId=#{funId}")
    int deleteTraceFunRegulation(@Param("funId")String funId);

}
