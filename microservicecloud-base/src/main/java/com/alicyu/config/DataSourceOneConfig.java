package com.alicyu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
// 属性配置方式参考：
// spring:
//    datasource01:
//     mapperPackage: com.alicyu.springcloud.dao.dbone         #mapper包路径
//     mapperxmlDir: classpath:mybatis/mapper/dbone/**/*.xml      #mapper.xml路径
//     entityPackage: com.alicyu.springcloud.entities.dbone    #实体包路径
//     mybatiscfg: classpath:mybatis/mybatis.cfg.xml      #mapper对应mybatis通用配置文件路径
//     type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
//     driver-class-name: org.gjt.mm.mysql.Driver              # mysql驱动包
//     url: jdbc:mysql://localhost:3306/clouddb01?useSSL=false              # 数据库名称
//     username: root
//     password: zhicheng
//     tomcat:
//        min-idle: 5                                           # 数据库连接池的最小维持连接数
//        max-idle: 50                                          # 数据库连接池的最大维持连接数
//        initial-size: 5                                       # 初始化连接数
//        max-active: 50                                        # 最大活跃数

@Configuration
@MapperScan(basePackages = "${spring.datasource01.mapperPackage}", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceOneConfig {
	
    @Bean(name = "DataSource")
    @ConfigurationProperties(prefix="spring.datasource01")
    @Primary
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    @Value("${spring.datasource01.entityPackage}")
    private String entityPackage;
    @Value("${spring.datasource01.mapperxmlDir}")
    private String mapperxmlDir;
    @Value("${spring.datasource01.mybatiscfg}")
    private String mybatiscfg;

    //提供SqlSeesion
    @Bean(name = "SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 数据源
        sqlSessionFactoryBean.setDataSource(dataSource());
        //下边的值可以通过@value
        // 实体返回映射
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        // sql xml文件路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperxmlDir));
        // 配置文件
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource(mybatiscfg));
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
