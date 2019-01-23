package com.jgw.supercodeplatform.trace.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DynamicBaseMapper extends CommonSql{
	@Update({"${sql}"})
	void dynamicDDLTable(@Param("sql")String sql);
	
	@Select("${sql}")
	List<LinkedHashMap<String, Object>> select(@Param("sql")String sql);
    
	@Insert("${sql}")
	void insert(@Param("sql")String sql);
	
	@Update("${sql}")
	void update(@Param("sql")String sql);

	@Delete("${sql}")
	void delete(@Param("sql")String sql);
	
	@Select("${sql}")
	int count(@Param("sql")String sql);
}
