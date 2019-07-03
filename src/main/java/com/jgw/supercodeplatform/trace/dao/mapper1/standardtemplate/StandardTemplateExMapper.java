package com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate;

import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.trace.pojo.certificate.CertificateInfo;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.StandardTemplate;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StandardTemplateExMapper extends StandardTemplateMapper {

    @Select(startScript+
            " SELECT Count(*) FROM trace_StandardTemplate a "+
            "<where>"+
            "<if test='search != null and search != &apos;&apos;'>   level2TypeName LIKE CONCAT('%',#{search},'%') </if>"+
            "</where>"+
            endScript
    )
    int countStandardTemplate(DaoSearch searchParams);

    @Select(startScript+
            "SELECT * FROM trace_StandardTemplate a " +
            "<where>"+
            "<if test='search != null and search != &apos;&apos;'>   level2TypeName LIKE CONCAT('%',#{search},'%') </if>"+
            "</where>"+
            orderBy+
            page+
            endScript)
    List<StandardTemplate> listStandardTemplate(DaoSearch searchParams);

    @Update("UPDATE trace_StandardTemplate SET DisableFlag = #{disableFlag} WHERE ID = #{id}")
    void updateDisableFlag(@Param("disableFlag")Integer disableFlag, @Param("id")Long id);

    @Select("SELECT * FROM trace_StandardTemplate WHERE level2typeid =#{levelId} ORDER BY id desc limit 0,1")
    StandardTemplate selectByLevelId(@Param("levelId")Integer levelId);

    @Select(startScript+
            "SELECT * FROM trace_StandardTemplate a "
            +startWhere
            + "  a.level2TypeId = #{level2TypeId}   "
            + " <if test='id !=null  '>  AND a.Id != #{id} </if> "
            +endWhere
            +endScript
    )
    List<StandardTemplate> selectByLevel2TypeId(@Param("level2TypeId") Integer level2TypeId, @Param("id")Long id);


}
