package com.alicyu.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
// 属性配置方式参考：
// spring:
//    datasource02:
//        mapperPackage: com.alicyu.springcloud.dao.dbtwo         #mapper包路径
//        mapperxmlDir: classpath:mybatis/mapper/dbtwo/**/*.xml      #mapper.xml路径
//        entityPackage: com.alicyu.springcloud.entities.dbtwo    #实体包路径
//        mybatiscfg: classpath:mybatis/mybatis.cfg.xml      #mapper对应mybatis通用配置文件路径
//        url: jdbc:mysql://localhost:3306/clouddb02?useSSL=false
//        username: root
//        password: zhicheng
//        minPoolSize: 3
//        maxPoolSize: 25
//        maxLifetime: 20000
//        borrowConnectionTimeout: 30
//        loginTimeout: 30
//        maintenanceInterval: 60
//        maxIdleTime: 60                                       # 最大活跃数

@Configuration
@MapperScan(basePackages = "${spring.datasource02.mapperPackage}", sqlSessionFactoryRef = "SqlSessionFactory02")
public class DataSourceTwoConfig {
	
    @Bean(name = "DBConfig2")
    @ConfigurationProperties(prefix="spring.datasource02")
    public DBConfig2 dBConfig2() {
        return new DBConfig2();
    }

    @Bean(name = "DataSource02")
    public DataSource dataSource(@Qualifier("DBConfig2")DBConfig2 testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("DataSource02");
        xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(testConfig.getTestQuery());
        return xaDataSource;
    }

    //提供SqlSeesion
    @Bean(name = "SqlSessionFactory02")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("DataSource02") DataSource dataSource,@Qualifier("DBConfig2")DBConfig2 testConfig) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        //下边的值可以通过@value
        // 实体返回映射
        sqlSessionFactoryBean.setTypeAliasesPackage(testConfig.getEntityPackage());
        // sql xml文件路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(testConfig.getMapperxmlDir()));
        // 配置文件
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource(testConfig.getMybatiscfg()));
        return sqlSessionFactoryBean.getObject();
    }
    //    因为事务会统一交给Atomikos全局事务，（因为是用了AtomikosDataSourceBean管理数据源），所以不能添加其他事务管理器
//    // 事务管理
//    @Bean(name = "transactionManager02")
//	public DataSourceTransactionManager transactionManager(@Qualifier("DataSource02") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
    // sqlSessionTemplate
    @Bean(name = "sqlSessionTemplate02")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory02") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
	}

}
