package com.br.common.datasource;

import com.br.common.amazon.AmazonSecretManager;

/**
 * todo: call to amazon secret manager.
 *
 * @author an.cantuong
 */
public class AwsDataSourceProperties extends DataSourceProperties {

    private final AmazonSecretManager secretManager;

    public AwsDataSourceProperties(AmazonSecretManager secretManager) {
        this.secretManager = secretManager;
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
