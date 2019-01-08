package com.jgw.supercodeplatform.trace.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by corbe on 2018/8/27.
 */
@Configuration
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
@MapperScan(basePackages = "com.jgw.supercodeplatform.trace.dao.mapper1", sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataSourceConfig1 {

    /**
     * 数据源dataSource配置
     *
     * @return
     * @author corbett
     * @data 2018年8月27日
     */
    @Bean(name = "dataSource1")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 数据源sqlSessionFactory配置
     *
     * @param dataSource1
     * @return
     * @throws Exception
     * @author corbett
     * @data 2018年8月27日
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource1") DataSource dataSource1) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource1);
        return factoryBean.getObject();

    }

    /**
     * 数据源sqlSessionTemplate配置
     *
     * @param dataSource1
     * @return
     * @throws Exception
     * @author corbett
     * @data 2018年8月27日
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("dataSource1") DataSource dataSource1) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1(dataSource1));
        return template;
    }

    @Bean
    public PlatformTransactionManager prodTransactionManager(@Qualifier("dataSource1") DataSource prodDataSource) {
        return new DataSourceTransactionManager(prodDataSource);
    }
}
