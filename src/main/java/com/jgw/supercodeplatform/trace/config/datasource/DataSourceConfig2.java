//package com.jgw.supercodeplatform.trace.config.datasource;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
///**
// * 多数据源数据库连接池配置2
// * @author liujianqiang
// * @date 2017年12月13日
// */
//@Configuration
//@MapperScan(basePackages = "com.jgw.supercodeplatform.trace.dao.dynamicMapper1",sqlSessionFactoryRef="sqlSessionFactory2")
//public class DataSourceConfig2 {
//	
//	@Autowired
//	private Environment env;
//	
//	/**
//	 * 数据源dataSource配置
//	 * @author liujianqiang
//	 * @data 2017年12月13日
//	 * @return
//	 */
//	@Bean(name = "dataSource2")
//	public DataSource dataSource(){
//		 DruidDataSource dataSource = new DruidDataSource();  
//		 dataSource.setUrl(env.getProperty("spring.datasource2.url"));
//		 dataSource.setUsername(env.getProperty("spring.datasource2.username"));
//		 dataSource.setPassword(env.getProperty("spring.datasource2.password"));
//		 dataSource.setDriverClassName(env.getProperty("spring.datasource2.driver-class-name"));
//		 dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource2.dbcp.initial-size")));
//		 dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource2.dbcp.min-idle")));
//		 dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource2.dbcp.max-active")));
//		 dataSource.setMaxWait(Long.valueOf(env.getProperty("spring.datasource2.dbcp.max-wait")));
//		 dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("spring.datasource2.dbcp.time-between-eviction-runs-millis")));
//		 dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("spring.datasource2.dbcp.min-evictable-idle-time-millis")));
//		 dataSource.setValidationQuery(env.getProperty("spring.datasource2.dbcp.validation-query"));
//		 dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("spring.datasource2.dbcp.test-while-idle")));
//		 dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("spring.datasource2.dbcp.test-on-borrow")));
//		 dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("spring.datasource2.dbcp.test-on-return")));
//		 return dataSource;
//	}
//	
//	/**
//	 * 数据源sqlSessionFactory配置
//	 * @author liujianqiang
//	 * @data 2017年12月13日
//	 * @param dataSource1
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource2) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource2);
//        return factoryBean.getObject();
//
//    }
//	
//	/**
//	 * 数据源sqlSessionTemplate配置
//	 * @author liujianqiang
//	 * @data 2017年12月13日
//	 * @param dataSource1
//	 * @return
//	 * @throws Exception
//	 */
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("dataSource2") DataSource dataSource2) throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2(dataSource2));
//        return template;
//    }
//    
//	
//
//}
