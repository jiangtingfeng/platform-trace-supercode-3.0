package com.jgw.supercodeplatform.trace.dao.mapper1.producttesting;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.producttesting.ProductTesting;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface ProductTestingMapper extends CommonSql {
    @Delete({
        "delete from trace_ProductTesting",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into trace_ProductTesting (Id, ProductTestingId, ",
        "OrganizationId, ThirdpartyOrganizationId, ",
        "ProductID, TraceBatchInfoId, ",
        "TestingDate, TestingMan, ",
        "CreateMan, CreateTime, ",
        "CreateId, TestingType, Excel, CertifyNumber," ,
        "TraceBatchInfoName,ProductName,TestingManName, OrganizeId, OrganizationName, organizeName, thirdpartyOrganizationName, SysId)",
        "values (#{id,jdbcType=INTEGER}, #{productTestingId,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{thirdpartyOrganizationId,jdbcType=VARCHAR}, ",
        "#{productID,jdbcType=VARCHAR}, #{traceBatchInfoId,jdbcType=VARCHAR}, ",
        "#{testingDate,jdbcType=VARCHAR}, #{testingMan,jdbcType=VARCHAR}, ",
        "#{createMan,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP},  ",
        "#{createId,jdbcType=VARCHAR},#{testingType,jdbcType=INTEGER},#{excel,jdbcType=VARCHAR},#{certifyNumber,jdbcType=VARCHAR}" ,
        ",#{traceBatchInfoName,jdbcType=VARCHAR},#{productName,jdbcType=VARCHAR},#{testingManName,jdbcType=VARCHAR},#{organizeId,jdbcType=VARCHAR},#{organizationName,jdbcType=VARCHAR},#{organizeName,jdbcType=VARCHAR},#{thirdpartyOrganizationName,jdbcType=VARCHAR}, #{sysId,jdbcType=VARCHAR})"
    })
    int insert(ProductTesting record);

    @Select({
        "select",
        "Id, ProductTestingId, OrganizationId, ThirdpartyOrganizationId, ProductID, TraceBatchInfoId, ",
         "TestingDate, TestingMan, CreateMan, CreateTime, CreateId, TestingType, CertifyNumber,Excel,TraceBatchInfoName,ProductName,TestingManName,ThirdpartyOrganizationName "+
        "from trace_ProductTesting",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ProductTestingId", property="productTestingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ThirdpartyOrganizationId", property="thirdpartyOrganizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductID", property="productID", jdbcType=JdbcType.VARCHAR),
        @Result(column="TraceBatchInfoId", property="traceBatchInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingDate", property="testingDate", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingMan", property="testingMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ThirdpartyOrganizationName", property="thirdpartyOrganizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR)
    })
    ProductTesting selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "Id, ProductTestingId, OrganizationId, ThirdpartyOrganizationId, ProductID, TraceBatchInfoId, ",
        "TestingDate, TestingMan, CreateMan, CreateTime, CreateId, TestingType",
        "from trace_ProductTesting"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ProductTestingId", property="productTestingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ThirdpartyOrganizationId", property="thirdpartyOrganizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductID", property="productID", jdbcType=JdbcType.VARCHAR),
        @Result(column="TraceBatchInfoId", property="traceBatchInfoId", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingDate", property="testingDate", jdbcType=JdbcType.VARCHAR),
        @Result(column="TestingMan", property="testingMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR)
    })
    List<ProductTesting> selectAll();

    @Update({
        "update trace_ProductTesting",
        "set OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "ThirdpartyOrganizationId = #{thirdpartyOrganizationId,jdbcType=VARCHAR},",
          "ProductID = #{productID,jdbcType=VARCHAR},",
            "ProductName = #{productName,jdbcType=VARCHAR},",
          "TraceBatchInfoId = #{traceBatchInfoId,jdbcType=VARCHAR}," ,
          "TraceBatchInfoName = #{traceBatchInfoName,jdbcType=VARCHAR},",
          "TestingDate = #{testingDate,jdbcType=VARCHAR},",
          "TestingMan = #{testingMan,jdbcType=VARCHAR},",
            "ThirdpartyOrganizationName = #{thirdpartyOrganizationName,jdbcType=VARCHAR},",
            "TestingType = #{testingType,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProductTesting record);

    @Select({
            startScript+
            "SELECT COUNT(1) FROM trace_ProductTesting a"
                    +startWhere
                    +" a.TestingType = #{testingType} "
                    + " <if test='organizeId !=null and organizeId != &apos;&apos; '>  AND (a.OrganizeId = #{organizeId}  OR  a.ThirdpartyOrganizationId = #{organizeId}) </if> "
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.TestingMan LIKE CONCAT('%',#{search},'%') or a.ProductName LIKE CONCAT('%',#{search},'%')  or a.TraceBatchInfoName LIKE CONCAT('%',#{search},'%')  or a.OrganizationName LIKE CONCAT('%',#{search},'%') or a.ThirdpartyOrganizationName LIKE CONCAT('%',#{search},'%') )</if> "
                    +endWhere
                    +page
                    +orderBy
            +endScript
    })
    int getCountByCondition(Map<String, Object> var1);

    @Select({
            startScript+
                    "select "+
                    "Id, ProductTestingId, OrganizationId, ThirdpartyOrganizationId, ProductID, TraceBatchInfoId, "+
                    "TestingDate, TestingMan, CreateMan, CreateTime, CreateId, TestingType, CertifyNumber,Excel,TraceBatchInfoName,ProductName,TestingManName, OrganizationName, OrganizeName, ThirdpartyOrganizationName "+
                    "from trace_ProductTesting a"
                    +startWhere
                    +" a.TestingType = #{testingType} "
                    + " <if test='organizeId !=null and organizeId != &apos;&apos; '>  AND (a.OrganizeId = #{organizeId} OR  a.ThirdpartyOrganizationId = #{organizeId}) </if> "
                    + " <if test='search !=null and search != &apos;&apos; '> AND ( a.TestingMan LIKE CONCAT('%',#{search},'%') or a.ProductName LIKE CONCAT('%',#{search},'%')  or a.TraceBatchInfoName LIKE CONCAT('%',#{search},'%')  or a.OrganizationName LIKE CONCAT('%',#{search},'%') or a.ThirdpartyOrganizationName LIKE CONCAT('%',#{search},'%') )</if> "
                    +endWhere
                    +orderBy
                    +page
                    +endScript
    })
    List<ProductTesting> selectProductTesting(Map<String, Object> var1);


}