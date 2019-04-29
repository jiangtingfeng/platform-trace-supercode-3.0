package com.jgw.supercodeplatform.trace.dao.mapper1.producttesting;

import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItem;
import java.util.List;

import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTestingItemEx;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ProductTestingItemMapper {
    @Delete({
        "delete from trace_ProductTestingItem",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into trace_ProductTestingItem (Id, ProductTestingItemId, ",
        "ProductTestingId, TestingTypeId, ",
        "TestingStandard, TestingResult, ",
        "TestingDate, TestingAccording, ",
        "Quantity, TestingMethod, ",
        "TestingDevice, Excel, ",
        "Imgs, Pdfs, Remark,TestingDeviceName)",
        "values (#{id,jdbcType=INTEGER}, #{productTestingItemId,jdbcType=VARCHAR}, ",
        "#{productTestingId,jdbcType=VARCHAR}, #{testingTypeId,jdbcType=VARCHAR}, ",
        "#{testingStandard,jdbcType=INTEGER}, #{testingResult,jdbcType=INTEGER}, ",
        "#{testingDate,jdbcType=TIMESTAMP}, #{testingAccording,jdbcType=VARCHAR}, ",
        "#{quantity,jdbcType=INTEGER}, #{testingMethod,jdbcType=VARCHAR}, ",
        "#{testingDevice,jdbcType=VARCHAR}, #{excel,jdbcType=VARCHAR}, ",
        "#{imgs,jdbcType=VARCHAR}, #{pdfs,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, #{testingDeviceName,jdbcType=VARCHAR})"
    })
    int insert(ProductTestingItem record);

    @Select({
        "select",
        "Id, ProductTestingItemId, ProductTestingId, TestingTypeId, TestingStandard, ",
        "TestingResult, TestingDate, TestingAccording, Quantity, TestingMethod, TestingDevice, ",
        "Excel, Imgs, Pdfs, Remark",
        "from trace_ProductTestingItem",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ProductTestingItemId", property="productTestingItemId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductTestingId", property="productTestingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingTypeId", property="testingTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingStandard", property="testingStandard", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingResult", property="testingResult", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingDate", property="testingDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TestingAccording", property="testingAccording", jdbcType=JdbcType.VARCHAR),
        @Result(column="Quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingMethod", property="testingMethod", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingDevice", property="testingDevice", jdbcType=JdbcType.VARCHAR),
        @Result(column="Excel", property="excel", jdbcType=JdbcType.VARCHAR),
        @Result(column="Imgs", property="imgs", jdbcType=JdbcType.VARCHAR),
        @Result(column="Pdfs", property="pdfs", jdbcType=JdbcType.VARCHAR),
        @Result(column="Remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    ProductTestingItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "Id, ProductTestingItemId, ProductTestingId, TestingTypeId, TestingStandard, ",
        "TestingResult, TestingDate, TestingAccording, Quantity, TestingMethod, TestingDevice, ",
        "Excel, Imgs, Pdfs, Remark,TestingDeviceName",
        "from trace_ProductTestingItem where ProductTestingId in (${ids}) "
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ProductTestingItemId", property="productTestingItemId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductTestingId", property="productTestingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingTypeId", property="testingTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingStandard", property="testingStandard", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingResult", property="testingResult", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingDate", property="testingDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="TestingAccording", property="testingAccording", jdbcType=JdbcType.VARCHAR),
        @Result(column="Quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingMethod", property="testingMethod", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingDevice", property="testingDevice", jdbcType=JdbcType.VARCHAR),
        @Result(column="Excel", property="excel", jdbcType=JdbcType.VARCHAR),
        @Result(column="Imgs", property="imgs", jdbcType=JdbcType.VARCHAR),
        @Result(column="Pdfs", property="pdfs", jdbcType=JdbcType.VARCHAR),
        @Result(column="Remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    List<ProductTestingItem> selectByProductTestingId(@Param("ids")String ids);

    @Update({
        "update trace_ProductTestingItem",
        "set ",
          "TestingTypeId = #{testingTypeId,jdbcType=VARCHAR},",
          "TestingStandard = #{testingStandard,jdbcType=INTEGER},",
          "TestingResult = #{testingResult,jdbcType=INTEGER},",
          "TestingDate = #{testingDate,jdbcType=TIMESTAMP},",
          "TestingAccording = #{testingAccording,jdbcType=VARCHAR},",
          "Quantity = #{quantity,jdbcType=INTEGER},",
          "TestingMethod = #{testingMethod,jdbcType=VARCHAR},",
          "TestingDevice = #{testingDevice,jdbcType=VARCHAR},",
          "Excel = #{excel,jdbcType=VARCHAR},",
          "Imgs = #{imgs,jdbcType=VARCHAR},",
          "Pdfs = #{pdfs,jdbcType=VARCHAR},",
          "Remark = #{remark,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProductTestingItem record);

    @Select("SELECT i.*,t.TestingType\n" +
            "from trace_ProductTesting t\n" +
            "inner join trace_ProductTestingItem i on t.ProductTestingId = i.ProductTestingId\n" +
            "where tracebatchinfoid=#{traceBatchInfoId} and\n" +
            " (LENGTH(imgs)>0  or LENGTH(pdfs)>0)\n" +
            "order by testingtype desc\n" )
    List<ProductTestingItemEx> selectProductTestingItem(String tracebatchinfoid);
}