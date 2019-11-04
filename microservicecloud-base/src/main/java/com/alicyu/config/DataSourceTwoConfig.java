package com.alicyu.config;

import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
@Log4j
@Configuration
@MapperScan(basePackages = "com.alicyu.springcloud.dao.dbtwo", sqlSessionFactoryRef = "SqlSessionFactory02")
public class DataSourceTwoConfig {
	
    @Bean(name = "DataSource02")
    @ConfigurationProperties(prefix="spring.datasource02")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }
 
    //提供SqlSeesion
    @Bean(name = "SqlSessionFactory02")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 数据源
        sqlSessionFactoryBean.setDataSource(dataSource());
        try {
            // 实体返回映射
            sqlSessionFactoryBean.setTypeAliasesPackage("com.alicyu.springcloud.entities.dbtwo");
            // sql xml文件路径
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/dbtwo/**/*.xml"));
            // 配置文件
            sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis.cfg.xml"));
        }catch (FileNotFoundException e){//有时候不使用额外库的时候会报找不到相应目录下的mapper文件
            log.error(e.getMessage());
        }
        return sqlSessionFactoryBean.getObject();
    }
    
    // 事务管理
    @Bean(name = "transactionManager02")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
    // sqlSessionTemplate
    @Bean(name = "sqlSessionTemplate02")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory02") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
	}

}
