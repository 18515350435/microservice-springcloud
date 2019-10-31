package com.alicyu.springcloud.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

//@Configuration
@MapperScan(basePackages = "com.zc.biz.dao.mappers.dbTwo", sqlSessionFactoryRef = "SqlSessionFactory02")
public class DataSourceTwoConfig {

  @Bean(name = "DataSource02")
  @ConfigurationProperties(prefix = "spring.datasource02")
  public DataSource dataSource() {
    return new org.apache.tomcat.jdbc.pool.DataSource();
  }

  // 提供SqlSeesion
  @Bean(name = "SqlSessionFactory02")
  public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    // 数据源
    sqlSessionFactoryBean.setDataSource(dataSource());
    // 实体返回映射
    sqlSessionFactoryBean.setTypeAliasesPackage("com.zc.biz.dao.models.dbTwo");
    // sql xml文件路径
    sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/dbTwo/**/*.xml"));
    // 配置文件
    sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
    return sqlSessionFactoryBean.getObject();
  }

  // 事务管理
  @Bean(name = "transactionManager02")
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

}
