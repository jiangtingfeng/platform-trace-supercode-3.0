package com.jgw.supercodeplatform.trace.constants;

/**
 * 统一的redis的key值管理类
 *
 * @author liujianqiang
 * @date 2018年9月5日
 */
public class RedisKey {

    public static final String GLOBAL_SYMBOL = "#1";//全局实体类的符号
    //登入业务
    public static final String USER_LOGIN = "user:login:";//邮箱验证码的key值前缀

    //用户角色服务关系
    public static final String USER_ROLE_SERVICE = "user_role_service:";

    //用户组织功能关系,为用户角色服务关系所服务，如果是特定功能接口拦截，需要用到此数据结果
    public static final String USER_ORG_FUN = "user_org_fun:";

    //用户组织功能关系,为用户角色服务关系所服务，如果是特定功能接口拦截，需要用到此数据结果
    public static final String TRACE = "trace:";
    
    public static String DATABASE1_TABLE_NUM_REDIS_KEY="trace:dynamic_database1_table_num";
    
    public static String DATABASE2_TABLE_NUM_REDIS_KEY="trace:dynamic_database2_table_num";

    //批次名称自增流水号
    public static final String BatchSerialNumber = "trace:batch:serialnumber:";

    public static final String StoragePlaceSerialNumber = "trace:storageplace:serialnumber:";
}
