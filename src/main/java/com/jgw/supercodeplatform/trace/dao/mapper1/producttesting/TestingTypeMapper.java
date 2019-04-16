package com.jgw.supercodeplatform.trace.dao.mapper1.producttesting;

import com.jgw.supercodeplatform.trace.dao.CommonSql;
import com.jgw.supercodeplatform.trace.pojo.producttesting.TestingType;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface TestingTypeMapper extends CommonSql {
    @Delete({
        "delete from trace_TestingType",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into trace_TestingType (Id, TestingTypeId, ",
        "OrganizationId, CreateId, ",
        "CreateMan, disableFlag, ",
        "TestingTypeName, CreateTime)",
        "values (#{id,jdbcType=INTEGER}, #{testingTypeId,jdbcType=VARCHAR}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{createId,jdbcType=VARCHAR}, ",
        "#{createMan,jdbcType=VARCHAR}, #{disableFlag,jdbcType=INTEGER}, ",
        "#{testingTypeName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(TestingType record);

    @Select({
        "select",
        "Id, TestingTypeId, OrganizationId, CreateId, CreateMan, disableFlag, TestingTypeName, ",
        "CreateTime",
        "from trace_TestingType",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="TestingTypeId", property="testingTypeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
        @Result(column="disableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="TestingTypeName", property="testingTypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TestingType selectByPrimaryKey(Integer id);


    @Select({" <script>  SELECT COUNT(1) FROM trace_TestingType  <where>  <if test='organizationId !=null and organizationId != &apos;&apos; '>  AND OrganizationId = #{organizationId} </if>  <if test='search !=null and search != &apos;&apos; '> AND (  TestingTypeName LIKE CONCAT('%',#{search},'%')  OR CreateMan LIKE CONCAT('%',#{search},'%') )</if>  </where>  </script> "})
    int getCountByCondition(Map<String, Object> var1);

    @Select({" <script> select", "Id, TestingTypeId, OrganizationId, CreateId, CreateMan, disableFlag, TestingTypeName", "from trace_TestingType a <where>  <if test='organizationId !=null and organizationId != &apos;&apos; '>  AND OrganizationId = #{organizationId} </if>  <if test='search !=null and search != &apos;&apos; '> AND (  TestingTypeName LIKE CONCAT('%',#{search},'%')  OR CreateMan LIKE CONCAT('%',#{search},'%') )</if>  </where>  ORDER BY a.CreateTime DESC <if test='startNumber != null and pageSize != null '> LIMIT #{startNumber},#{pageSize}</if> </script> "})
    @Results({
            @Result(column="Id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="TestingTypeId", property="testingTypeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateId", property="createId", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateMan", property="createMan", jdbcType=JdbcType.VARCHAR),
            @Result(column="disableFlag", property="disableFlag", jdbcType=JdbcType.INTEGER),
            @Result(column="TestingTypeName", property="testingTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TestingType> selectTestingType(Map<String, Object> var1);

    @Update({
        "update trace_TestingType",
        "set TestingTypeId = #{testingTypeId,jdbcType=VARCHAR},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "CreateId = #{createId,jdbcType=VARCHAR},",
          "CreateMan = #{createMan,jdbcType=VARCHAR},",
          "disableFlag = #{disableFlag,jdbcType=INTEGER},",
          "TestingTypeName = #{testingTypeName,jdbcType=VARCHAR},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TestingType record);
}