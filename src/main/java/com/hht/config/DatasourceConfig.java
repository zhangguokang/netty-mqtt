/**
 * Project Name:hht-backend-loginservice
 * File Name:DatasourceConfig.java
 * Package Name:com.hht.config
 * Date:2018年09月14日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Solin
 *
 * @description 数据库连接配置类
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@MapperScan("com.hht.dao")
public class DatasourceConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Value("${spring.datasource.hikari.pool-name}")
    private String poolName;
    
    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private Integer minimumIdle;
    
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer maximumPoolSize;
    

    @Bean
    public DataSource dataSource() {
        HikariConfig configuration = new HikariConfig();
        configuration.setPoolName(poolName);
        configuration.setJdbcUrl(jdbcUrl);
        configuration.setDriverClassName(driverClass);
        configuration.addDataSourceProperty("user", username);
        configuration
                .addDataSourceProperty("password", password);
        configuration.setMinimumIdle(minimumIdle);
        configuration.setMaximumPoolSize(maximumPoolSize);
        configuration.setConnectionTestQuery(connectionTestQuery);
        return new HikariDataSource(configuration);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));

        return sqlSessionFactoryBean.getObject();
    }

}