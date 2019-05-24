package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

import static com.jgw.supercodeplatform.trace.dao.CommonSql.*;

@Mapper
public interface StoragePlaceExMapper extends StoragePlaceMapper {
    @Select({
            startScript+
                    "SELECT COUNT(1) FROM zaoyang_storageplace a"
                    +startWhere
                    + " <if test='disableFlag !=null  '>  DisableFlag = #{disableFlag} </if> "
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.PlaceName LIKE CONCAT('%',#{search},'%') or a.SortingPlaceName LIKE CONCAT('%',#{search},'%') )</if> "
                    +endWhere
                    +page
                    +orderBy
                    +endScript
    })
    int getCountByCondition(Map<String, Object> var1);

    @Select({
            startScript+
                    "select * from zaoyang_storageplace a"
                    +startWhere
                    + " <if test='disableFlag !=null  '>  DisableFlag = #{disableFlag} </if> "
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.PlaceName LIKE CONCAT('%',#{search},'%') or a.SortingPlaceName LIKE CONCAT('%',#{search},'%') )</if> "
                    +endWhere
                    +orderBy
                    +page
                    +endScript
    })
    List<StoragePlace> selectSortingPlace(Map<String, Object> var1);

    @Update({
            "update zaoyang_storageplace",
            "set ",
            "DisableFlag = #{disableFlag,jdbcType=INTEGER}",
            "where Id = #{id}"
    })
    int updateDisableFlag(@Param("id") Integer id, @Param("disableFlag")Integer disableFlag);

}
