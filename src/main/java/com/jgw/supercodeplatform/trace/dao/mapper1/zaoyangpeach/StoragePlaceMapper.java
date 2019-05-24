package com.jgw.supercodeplatform.trace.dao.mapper1.zaoyangpeach;

import com.jgw.supercodeplatform.trace.pojo.zaoyangpeach.StoragePlace;
import java.util.List;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface StoragePlaceMapper {
    @Delete({
        "delete from zaoyang_storageplace",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into zaoyang_storageplace (Id, PlaceId, ",
        "PlaceName, CurrentBatchId, ",
        "SortingPlaceName, SortingPlaceId, ",
        " PlaceStaff, ",
        "DisableFlag, PlaceNumber)",
        "values (#{id,jdbcType=INTEGER}, #{placeId,jdbcType=VARCHAR}, ",
        "#{placeName,jdbcType=VARCHAR}, #{currentBatchId,jdbcType=VARCHAR}, ",
        "#{sortingPlaceName,jdbcType=VARCHAR}, #{sortingPlaceId,jdbcType=VARCHAR}, ",
        " #{placeStaff,jdbcType=VARCHAR}, ",
        "#{disableFlag,jdbcType=INTEGER}, #{placeNumber,jdbcType=VARCHAR})"
    })
    int insert(StoragePlace record);

    @Select({
        "select",
        "Id, PlaceId, PlaceName, CurrentBatchId, SortingPlaceName, SortingPlaceId, CreateTime, ",
        "PlaceStaff, DisableFlag, PlaceNumber",
        "from zaoyang_storageplace",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="PlaceId", property="placeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PlaceName", property="placeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CurrentBatchId", property="currentBatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SortingPlaceName", property="sortingPlaceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SortingPlaceId", property="sortingPlaceId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PlaceStaff", property="placeStaff", jdbcType=JdbcType.VARCHAR),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="PlaceNumber", property="placeNumber", jdbcType=JdbcType.VARCHAR)
    })
    StoragePlace selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "Id, PlaceId, PlaceName, CurrentBatchId, SortingPlaceName, SortingPlaceId, CreateTime, ",
        "PlaceStaff, DisableFlag, PlaceNumber",
        "from zaoyang_storageplace"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="PlaceId", property="placeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PlaceName", property="placeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CurrentBatchId", property="currentBatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SortingPlaceName", property="sortingPlaceName", jdbcType=JdbcType.VARCHAR),
        @Result(column="SortingPlaceId", property="sortingPlaceId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="PlaceStaff", property="placeStaff", jdbcType=JdbcType.VARCHAR),
        @Result(column="DisableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="PlaceNumber", property="placeNumber", jdbcType=JdbcType.VARCHAR)
    })
    List<StoragePlace> selectAll();

    @Update({
        "update zaoyang_storageplace",
        "set PlaceId = #{placeId,jdbcType=VARCHAR},",
          "PlaceName = #{placeName,jdbcType=VARCHAR},",
          "CurrentBatchId = #{currentBatchId,jdbcType=VARCHAR},",
          "SortingPlaceName = #{sortingPlaceName,jdbcType=VARCHAR},",
          "SortingPlaceId = #{sortingPlaceId,jdbcType=VARCHAR},",
          "PlaceStaff = #{placeStaff,jdbcType=VARCHAR},",
          "DisableFlag = #{disableFlag,jdbcType=INTEGER},",
          "PlaceNumber = #{placeNumber,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StoragePlace record);

    @Update({
            "update zaoyang_storageplace",
            "set ",
            "DisableFlag = #{disableFlag,jdbcType=INTEGER}",
            "where Id = #{id}"
    })
    int updateDisableFlag(@Param("id") Integer id, @Param("disableFlag")Integer disableFlag);
}