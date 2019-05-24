package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.SortingPlace;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SortingPlaceExMapper extends SortingPlaceMapper {


    @Select({
            startScript+
                    "SELECT COUNT(1) FROM zaoyang_sortingplace a"
                    +startWhere
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.SortingPlaceName LIKE CONCAT('%',#{search},'%')  )</if> "
                    +endWhere
                    +page
                    +orderBy
                    +endScript
    })
    int getCountByCondition(Map<String, Object> var1);

    @Select({
            startScript+
                    "select * from zaoyang_sortingplace a"
                    +startWhere
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.SortingPlaceName LIKE CONCAT('%',#{search},'%')  )</if> "
                    +endWhere
                    +orderBy
                    +page
                    +endScript
    })
    List<SortingPlace> selectSortingPlace(Map<String, Object> var1);


}
