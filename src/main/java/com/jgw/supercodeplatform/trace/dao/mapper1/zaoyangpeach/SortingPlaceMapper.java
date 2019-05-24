package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.SortingPlace;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface SortingPlaceMapper  extends CommonSql {
    @Delete({
        "delete from zaoyang_sortingplace",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into zaoyang_sortingplace (Id, SortingPlaceName, ",
        "CreateTime)",
        "values (#{id,jdbcType=INTEGER}, #{sortingPlaceName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(SortingPlace record);

    @Select({
        "select",
        "Id, SortingPlaceName, CreateTime",
        "from zaoyang_sortingplace",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="SortingPlaceName", property="sortingPlaceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SortingPlace selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "Id, SortingPlaceName, CreateTime",
        "from zaoyang_sortingplace"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="SortingPlaceName", property="sortingPlaceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<SortingPlace> selectAll();

    @Update({
        "update zaoyang_sortingplace",
        "set SortingPlaceName = #{sortingPlaceName,jdbcType=VARCHAR},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SortingPlace record);
}