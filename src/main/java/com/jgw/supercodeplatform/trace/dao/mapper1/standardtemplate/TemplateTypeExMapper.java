package com.jgw.supercodeplatform.trace.dao.mapper1.standardtemplate;

import com.jgw.supercodeplatform.common.pojo.common.DaoSearch;
import com.jgw.supercodeplatform.trace.pojo.standardtemplate.TemplateType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TemplateTypeExMapper extends TemplateTypeMapper {

    @Select(startScript+
            " SELECT Count(*) FROM trace_TemplateType a "+
            "<where>"+
            "<if test='search != null and search != &apos;&apos;'>   typeName LIKE CONCAT('%',#{search},'%') </if>"+
            "<if test='params.categoryId != null and params.categoryId != &apos;&apos;'> AND  categoryId=#{params.categoryId} </if>"+
            "<if test='params.parentId != null and params.parentId != &apos;&apos;'> AND  parentId=#{params.parentId} </if>"+
            "<if test='params.levelId != null and params.levelId != &apos;&apos;'> AND  levelId=#{params.levelId} </if>"+
            "<if test='params.enable != null and params.enable != &apos;&apos;'>  and not EXISTS ( SELECT 1 FROM trace_StandardTemplate s WHERE s.Level2TypeId=a.Id and s.DisableFlag=1 ) </if>"+
            "</where>"+
            endScript
    )
    int countTemplateType(DaoSearch searchParams);

    @Select(startScript+
            "SELECT * FROM trace_TemplateType a " +
            "<where>"+
            "<if test='search != null and search != &apos;&apos;'>   typeName LIKE CONCAT('%',#{search},'%') </if>"+
            "<if test='params.categoryId != null and params.categoryId != &apos;&apos;'> AND  categoryId=#{params.categoryId} </if>"+
            "<if test='params.parentId != null and params.parentId != &apos;&apos;'> AND  parentId=#{params.parentId} </if>"+
            "<if test='params.levelId != null and params.levelId != &apos;&apos;'> AND  levelId=#{params.levelId} </if>"+
            "<if test='params.enable != null and params.enable != &apos;&apos;'>  and not EXISTS ( SELECT 1 FROM trace_StandardTemplate s WHERE s.Level2TypeId=a.Id and s.DisableFlag=1 ) </if>"+
            "</where>"+
            " order by a.CategoryId, a.ParentId,a.Sort "+
            page+
            endScript)
    List<TemplateType> listTemplateType(DaoSearch searchParams);

    @Update("UPDATE trace_TemplateType SET DisableFlag = #{disableFlag} WHERE ID = #{id}")
    void updateDisableFlag(@Param("disableFlag")Integer disableFlag, @Param("id")Long id);

    @Update("UPDATE trace_TemplateType SET ParentName = #{parentName} WHERE LevelId=2 AND ParentId=#{parentId} ")
    void updateParentName(@Param("parentId")Long parentId,@Param("parentName")String parentName);

}
