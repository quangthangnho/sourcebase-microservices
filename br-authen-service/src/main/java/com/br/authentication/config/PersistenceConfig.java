package com.br.authentication.config;

import com.br.common.datasource.DataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource dataSource(DataSourceProperties dataSourceProps) {
        return new HikariDataSource(buildHikariConfig(dataSourceProps));
    }

    @Bean
    public PlatformTransactionManager transactionManager(HikariDataSource dataSource) {
        var transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private HikariConfig buildHikariConfig(DataSourceProperties dataSourceProps) {
        var hikariConf = new HikariConfig();
        var hikari = dataSourceProps.getHikari();
        hikariConf.setDriverClassName(dataSourceProps.getDriverClassName());
        hikariConf.setJdbcUrl(dataSourceProps.getUrl());
        hikariConf.setUsername(dataSourceProps.getUsername());
        hikariConf.setPassword(dataSourceProps.getPassword());

        hikariConf.setMaximumPoolSize(hikari.getMaximumPoolSize());
        hikariConf.setConnectionTestQuery("SELECT 1");
        hikariConf.setPoolName(hikari.getPoolName());
        hikariConf.setLeakDetectionThreshold(hikari.getLeakDetectionThreshold());
        hikariConf.setAutoCommit(hikari.isAutoCommit());

        hikariConf.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConf.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConf.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConf.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        return hikariConf;
    }
}