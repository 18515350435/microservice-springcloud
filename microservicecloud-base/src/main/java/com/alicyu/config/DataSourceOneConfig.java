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
//    datasource01:
//        mapperPackage: com.alicyu.springcloud.dao.dbone         #mapper包路径
//        mapperxmlDir: classpath:mybatis/mapper/dbone/**/*.xml      #mapper.xml路径
//        entityPackage: com.alicyu.springcloud.entities.dbone    #实体包路径
//        mybatiscfg: classpath:mybatis/mybatis.cfg.xml      #mapper对应mybatis通用配置文件路径
//        url: jdbc:mysql://localhost:3306/clouddb01?useSSL=false
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
@MapperScan(basePackages = "${spring.datasource01.mapperPackage}", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceOneConfig {
	
    @Bean(name = "DBConfig1")
    @ConfigurationProperties(prefix="spring.datasource01")
    @Primary
    public DBConfig1 dBConfig1() {
        return new DBConfig1();
    }

    @Bean(name = "DataSource")
    @Primary
    public DataSource dataSource(@Qualifier("DBConfig1")DBConfig1 testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("DataSource");
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
//
//    @Value("${spring.datasource01.entityPackage}")
//    private String entityPackage;
//    @Value("${spring.datasource01.mapperxmlDir}")
//    private String mapperxmlDir;
//    @Value("${spring.datasource01.mybatiscfg}")
//    private String mybatiscfg;

    //提供SqlSeesion
    @Bean(name = "SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("DataSource") DataSource dataSource,@Qualifier("DBConfig1")DBConfig1 testConfig) throws Exception {
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
//    @Bean(name = "transactionManager")
//    @Primary
//	public DataSourceTransactionManager transactionManager(@Qualifier("DataSource") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
    // sqlSessionTemplate
    @Bean(name = "sqlSessionTemplate")
    @Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
	}

}
