package com.jgw.supercodeplatform.trace.aware;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.jgw.supercodeplatform.trace.config.redis.RedisUtil;
import com.jgw.supercodeplatform.trace.constants.RedisKey;
import com.jgw.supercodeplatform.trace.dao.DynamicBaseMapper;
import com.jgw.supercodeplatform.trace.dao.dynamicMapper1.DynamicCreateTableMapper1;
import com.jgw.supercodeplatform.trace.dao.dynamicMapper2.DynamicCreateTableMapper2;
import com.jgw.supercodeplatform.trace.dao.mapper1.TraceOrgFunRouteMapper;
import com.jgw.supercodeplatform.trace.exception.SuperCodeTraceException;
import com.jgw.supercodeplatform.trace.pojo.TraceOrgFunRoute;
@Component
public class TraceApplicationContextAware implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	
    @Autowired
    protected RedisUtil redisUtil;
    
    @Autowired
    protected TraceOrgFunRouteMapper traceOrgFunRouteDao;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
    
	/**
	 * 创建动态表时使用
	 * 先获取库1的如果库1没满就使用数据源1下的mapper否则用2的
	 * @return
	 */
	public DynamicBaseMapper getDynamicMapperByTableNum() {
		String value=redisUtil.get(RedisKey.DATABASE1_TABLE_NUM_REDIS_KEY);
		if (StringUtils.isBlank(value)) {
			return applicationContext.getBean(DynamicCreateTableMapper1.class);
		}else {
			Long key=Long.parseLong(value);
			if (key<100L) {
				return applicationContext.getBean(DynamicCreateTableMapper1.class);
			}
		}
		return applicationContext.getBean(DynamicCreateTableMapper2.class);
	}
	
	/**
	 * 动态表已存在时使用
	 * 根据功能id在企业路由关系表中找到表对应的报存库的序号
	 * @param functionId
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public DynamicBaseMapper getDynamicMapperByFunctionId(String templateId,String functionId) throws SuperCodeTraceException {
		TraceOrgFunRoute traceOrgFunRoute=traceOrgFunRouteDao.selectByTraceTemplateIdAndFunctionId(templateId, functionId);
		if (null==traceOrgFunRoute) {
			throw new SuperCodeTraceException("TraceApplicationContextAware.getDynamicMapperByFunctionId：无法根据功能id："+functionId+"在企业路由表里获取记录", 500);
		}
		String database=traceOrgFunRoute.getDatabaseAddress();
		if (StringUtils.isBlank(database) || "1".equals(database)) {
			return applicationContext.getBean(DynamicCreateTableMapper1.class);
		}else if ("2".equals(database)) {
			return applicationContext.getBean(DynamicCreateTableMapper2.class);
		}else {
			throw new SuperCodeTraceException("功能id："+functionId+"在企业路由表里获取记录数据库地址字段值="+database+"非法", 500);
		}
	}
	
	/**
	 * 动态表已存在时使用
	 * 根据功能id在企业路由关系表中找到表对应的报存库的序号
	 * @param functionId
	 * @return
	 * @throws SuperCodeTraceException
	 */
	public DynamicBaseMapper getDynamicMapperByDataBaseNum(String dataBaseNum) throws SuperCodeTraceException {

		if (StringUtils.isBlank(dataBaseNum) || "1".equals(dataBaseNum)) {
			return applicationContext.getBean(DynamicCreateTableMapper1.class);
		}else if ("2".equals(dataBaseNum)) {
			return applicationContext.getBean(DynamicCreateTableMapper2.class);
		}
		throw new SuperCodeTraceException("请传正确的dataBaseNum", 500);
	}
}
