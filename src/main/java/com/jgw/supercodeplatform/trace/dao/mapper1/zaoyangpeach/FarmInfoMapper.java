package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.FarmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FarmInfoMapper extends CommonSql {


    @Select({
            startScript+
                    "SELECT COUNT(1) FROM zaoyang_farm_info a"
                    +startWhere
                    + " <if test='search !=null and search != &apos;&apos; '>  a.farm_name LIKE CONCAT('%',#{search},'%')  </if> "
                    +endWhere
                    +page
                    +endScript
    })
    int getCountByCondition(Map<String, Object> var1);

    @Select({
            startScript+
                    "SELECT id,farm_name farmName FROM zaoyang_farm_info a "
                    +startWhere
                    + " <if test='search !=null and search != &apos;&apos; '>  a.farm_name LIKE CONCAT('%',#{search},'%')  </if> "
                    +endWhere
                    +" ORDER BY a.id DESC "
                    +page
                    +endScript
    })
    List<FarmInfo> selectFarmInfo(Map<String, Object> var1);


}
