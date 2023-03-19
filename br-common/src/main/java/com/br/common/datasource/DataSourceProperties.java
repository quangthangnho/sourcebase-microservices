package com.br.common.datasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceProperties {

    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private CustomHikariConfig hikari;

    @Getter
    @Setter
    public static class CustomHikariConfig {

        private long connectionTimeout;

        private long validationTimeout;

        private long idleTimeout;

        private long leakDetectionThreshold;

        private long maxLifetime;

        private int maximumPoolSize;

        private int minimumIdle;

        private boolean autoCommit;

        private String poolName;
    }
}