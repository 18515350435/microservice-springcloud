package com.alicyu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.alicyu.springcloud.dao.dbone", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceOneConfig {
	
    @Bean(name = "DataSource")
    @ConfigurationProperties(prefix="spring.datasource01")
    @Primary
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }
 
    //提供SqlSeesion
    @Bean(name = "SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 数据源
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 实体返回映射
        sqlSessionFactoryBean.setTypeAliasesPackage("com.alicyu.springcloud.entities.dbone");
        // sql xml文件路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/dbone/**/*.xml"));
        // 配置文件
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis.cfg.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    // 事务管理
    @Bean(name = "transactionManager")
    @Primary
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
    // sqlSessionTemplate
    @Bean(name = "sqlSessionTemplate")
    @Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
	}

}
