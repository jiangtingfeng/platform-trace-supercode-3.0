package com.jgw.supercodeplatform.trace.dao;

/**
 * 描述：公共sql模块
 *
 * @author corbett
 *         Created by corbett on 2018/10/15.
 */
public interface CommonSql {
    String select = " SELECT ";
    String startScript = " <script> ";
    String endScript = " </script> ";
    String startWhere = " <where> ";
    String endWhere = " </where> ";
    String count = "count(*)";
    String page = "<if test='startNumber != null and pageSize != null '> LIMIT #{startNumber},#{pageSize}</if>";
    String createTime = " ,DATE_FORMAT(a.CreateTime,'%Y-%m-%d %H:%i:%S') as createTime ";
    String updateTime = " ,DATE_FORMAT(a.UpdateTime,'%Y-%m-%d %H:%i:%S') as updateTime ";
    String orderBy = " ORDER BY a.CreateTime DESC ";

}
